package ray.parser;

import java.io.IOException;

import ray.inter.Access;
import ray.inter.And;
import ray.inter.Arith;
import ray.inter.Break;
import ray.inter.Constant;
import ray.inter.Do;
import ray.inter.Else;
import ray.inter.Expr;
import ray.inter.Id;
import ray.inter.If;
import ray.inter.Not;
import ray.inter.Or;
import ray.inter.Rel;
import ray.inter.Seq;
import ray.inter.Set;
import ray.inter.SetElem;
import ray.inter.Stmt;
import ray.inter.Unary;
import ray.inter.While;

import ray.lexer.Lexer;
import ray.lexer.Num;
import ray.lexer.Tag;
import ray.lexer.Token;
import ray.lexer.Word;

import ray.symbols.Array;
import ray.symbols.Env;
import ray.symbols.Type;

/**
 * DESCRIPTION: 语法分析器的相关类，语法分析器读入一个由词法单元组成的流，并调用适当的inter中的构造函数，构建出一棵
 * 抽象语法树
*/
public class Parser {

    private Lexer lex;  //这个语法分析器的词法分析器
    private Token look;  //向前看词法单元
    Env top = null;  //向前或顶层的符号表
    int used = 0;  //用于变量声明的存储位置
    
    public Parser(Lexer l) throws IOException 
    {
        lex = l; 
        move(); 
    }

    void move() throws IOException 
    { 
        look = lex.scan(); 
    }

    void error(String s) 
    { 
        throw new Error("near line " + Lexer.line + ": " + s); 
    }

    void match(int t) throws IOException 
    {
        if (look.tag == t) 
        {
            move();
        }
        else 
        {
            error("syntax error");
        }
    }

    /**
     * DESCRIPTION: 语法分析过程开始调用的接口，调用block()对输入流进行语法分析，并构建出抽象语法树
     * PARAM: NULL
     * RETURN: void
    */
    public void program() throws IOException 
    {
        //对输入流进行语法分析
        Stmt s = block();

        //生成中间代码
        int begin = s.newlabel(); 
        int after = s.newlabel();
        s.emitlabel(begin); 
        s.gen(begin, after); 
        s.emitlabel(after);
    }
    
    /**
     * DESCRIPTION: 对输入流进行语法分析（对符号表进行处理）
     * PARAM: NULL
     * RETURN: Stmt
    */
    Stmt block() throws IOException 
    {
        match('{'); 
        Env savedEnv = top; 
        top = new Env(top);
        decls(); 
        
        Stmt s = stmts();
        match('}'); 
        top = savedEnv;
        return s;
    }

    void decls() throws IOException 
    {
        while (look.tag == Tag.BASIC)  //D->type ID;
        {
            Type p = type(); 
            Token tok = look; 
            match(Tag.ID); 
            match(';');
            
            Id id = new Id((Word)tok, p, used);
            top.put(tok, id);  //程序中的声明被处理为符号表中有关标识符的条目
            used = used + p.width;
        }
    }

    Type type() throws IOException 
    {
        Type p = (Type)look;  //期望look.tag == Tag.BASIC
        match(Tag.BASIC);
        
        if ('[' != look.tag)
        {
            return p;  //T->basic
        }
        else 
        {
            return dims(p);  //返回数组类型
        }
    }

    Type dims(Type p) throws IOException 
    {
        match('['); 
        Token tok = look; 
        match(Tag.NUM); 
		match(']');

        if ('[' == look.tag)
        {    
            p = dims(p);
        }

        return new Array(((Num)tok).value, p);
    }

    Stmt stmts() throws IOException 
    {
        if ('}' == look.tag)
        {
            return Stmt.Null;
        }
        else 
        {
            return new Seq(stmt(), stmts());
        }
    }

    Stmt stmt() throws IOException 
    {
        Expr x; 
        Stmt s;
        Stmt s1;
        Stmt s2;
        Stmt savedStmt;  //用于break语句保存外层的循环语句
        
        //这个语句的每个case分支对应于非终结符号Stmt的各个产生式。每个case分支都使用inter中讨论的构造函数来建立某个构造对应的结点。
        //当语法分析器碰到while语句和do语句的开始关键字的时候，就会创建这些语句的结点。这些结点在相应语句进行完语法分析之前就构造出来了，
        //这可以使得任何内层的break语句回到它的外层循环语句。当出现嵌套的循环时，我们通过使用类Stmt中的变量Stmt.Enclosing和savedStmt来保存当前的外层循环。
        switch (look.tag) 
        {
            case ';':
            {
                move();
                return Stmt.Null;
            }
            case Tag.IF:
            {
                match(Tag.IF); 
                match('('); 
                x = bool(); 
                match(')');
                s1 = stmt();
                
                if (look.tag != Tag.ELSE) 
                {
                    return new If(x, s1);
                }

                match(Tag.ELSE);
                s2 = stmt();
                return new Else(x, s1, s2);
            }
            case Tag.WHILE:
            {
                While whilenode = new While();
                savedStmt = Stmt.Enclosing; 
                Stmt.Enclosing = whilenode;
                
                match(Tag.WHILE); 
                match('('); 
                x = bool(); 
                match(')');
				
                s1 = stmt();
                
                whilenode.init(x, s1);
				
                Stmt.Enclosing = savedStmt;  //重置Stmt.Enclosing
				
                return whilenode;
            }
            case Tag.DO:
            {
                Do donode = new Do();
                savedStmt = Stmt.Enclosing; 
                Stmt.Enclosing = donode;
                match(Tag.DO);
                
                s1 = stmt();
                match(Tag.WHILE); 
				match('('); 
                x = bool(); 
                match(')'); 
                match(';');
                
                donode.init(s1, x);
				
                Stmt.Enclosing = savedStmt;  //重置Stmt.Enclosing
				
                return donode;
            }
            case Tag.BREAK:
            {
                match(Tag.BREAK); 
                match(';');
                return new Break();
            }
            case '{':
            {
                return block();
            }
            default:
            {
                return assign();
            }
        }
    }

    /**
     * DESCRIPTION: 为方便起见，赋值语句的代码出现在一个辅助过程assign中
     * PARAM: NULL
     * RETURN: Stmt
    */
    Stmt assign() throws IOException 
    {
        Stmt stmt; 
        Token t = look;
        
        match(Tag.ID);
        Id id = top.get(t);
        
        if (null == id) 
        {
            error(t.toString() + " undeclared");
        }

        if ('=' == look.tag) 
        {
            move(); 
			stmt = new Set(id, bool());
        }
        else 
        {
            Access x = offset(id);
            match('='); 
            stmt = new SetElem(x, bool());
        }

        match(';');
        return stmt;
    }

    //对算数运算和布尔运算的语法分析很相似。在每种情况下都会创建一个正确的抽象语法树结点，这两者代码的生成方式有所不同。
    Expr bool() throws IOException 
    {
        Expr x = join();
        while(Tag.OR == look.tag)
        {
            Token tok = look; 
            move(); 
            x = new Or(tok, x, join());
        }

        return x;
    }

    Expr join() throws IOException 
    {
        Expr x = equality();
        while(Tag.AND == look.tag) 
        {
            Token tok = look; 
            move(); 
            x = new And(tok, x, equality());
        }

        return x;
    }

    Expr equality() throws IOException 
    {
        Expr x = rel();
        while((Tag.EQ == look.tag) || (Tag.NE == look.tag))
        {
            Token tok = look; 
            move(); 
            x = new Rel(tok, x, rel());
        }

        return x;
    }

    Expr rel() throws IOException 
    {
        Expr x = expr();
        switch(look.tag) 
        {
            case '<': 
            case Tag.LE: 
            case Tag.GE: 
            case '>':
            {
                Token tok = look; 
                move(); 
                return new Rel(tok, x, expr());
            }
            default:
            {
                return x;
            }
        }
    }

    Expr expr() throws IOException 
    {
        Expr x = term();
        while(('+' == look.tag) || ('-' == look.tag))
        {
            Token tok = look;
            move(); 
            x = new Arith(tok, x, term());
        }

        return x;
    }

    Expr term() throws IOException 
    {
        Expr x = unary();
        while(('*' == look.tag) || ('/' == look.tag)) 
        {
            Token tok = look; 
            move(); 
            x = new Arith(tok, x, unary());
        }

        return x;
    }

    Expr unary() throws IOException 
    {
        if ('-' == look.tag) 
        {
            move(); 
            return new Unary(Word.minus, unary());
        }
        else if ('!' == look.tag)
        {
            Token tok = look; 
            move(); 
            return new Not(tok, unary());
        }
        else 
        {
            return factor();
        }
    }

    /**
     * DESCRIPTION: 处理表达式“因子”，辅助函数offset按照6.4.3节中讨论的方法为数组地址计算生成代码。
     * PARAM: NULL
     * RETURN: Expr
    */
    Expr factor() throws IOException 
    {
        Expr x = null;
        switch(look.tag) 
        {
            case '(':
            {
                move();
                x = bool();
                match(')');
                return x;
            }
            case Tag.NUM:
            {
                x = new Constant(look, Type.Int); 
                move(); 
                return x;
            }
            case Tag.REAL:
            {
                x = new Constant(look, Type.Float); 
                move(); 
                return x;
            }
            case Tag.TRUE:
            {
                x = Constant.True; 
                move(); 
                return x;
            }
            case Tag.FALSE:
            {
                x = Constant.False; 
                move(); 
                return x;
            }
            case Tag.ID:
            {
                String s = look.toString();
                Id id = top.get(look);

                if (id == null) 
                {
                    error(look.toString() + " undeclared");
                }

                move();
            
                if (look.tag != '[') 
                {
                    return id;
                }
                else 
                {
                    return offset(id);
                }
            }
            default:
            {
                error("syntax error");
                return x;
            }
        }
    }

    /**
     * DESCRIPTION: 按照6.4.3节中讨论的方法为数组地址计算生成代码。 I -> [E]|[E]I
     * PARAM: NULL
     * RETURN: Expr
    */
    Access offset(Id a) throws IOException 
    {
        Expr i; 
        Expr w; 
        Expr t1;
        Expr t2; 
        Expr loc;  //继承id
        Type type = a.type;
        
        //第一个下标，I->[E]
        match('['); 
        i = bool(); 
        match(']');
        
        type = ((Array)type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'), i, w);
        loc = t1;
        
        //多维下标，I->[E]I
        while('[' == look.tag) 
        {
            match('['); 
            i = bool(); 
            match(']');
            
            type = ((Array)type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }
        
        return new Access(a, loc, type);
    }
}
