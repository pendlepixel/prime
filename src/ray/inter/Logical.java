package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

/**
 * DESCRIPTION: And,Or,Not,Rel的父类，为他们提供一些常见功能。
*/
public class Logical extends Expr {
    
    //两个字段分别对应于一个逻辑运算符的运算分量，
    public Expr expr1;
    public Expr expr2;

    /**
    * DESCRIPTION: 构造函数，构造出一个语法树的节点，其运算符是tok,运算分量是a和b,通过check保证a和b都是布尔类型
    */
    Logical(Token tok, Expr x1, Expr x2) 
    {
        super(tok, null);  //开始时设置类型为空
        expr1 = x1; 
        expr2 = x2;
        
        type = check(expr1.type, expr2.type);
        if (null == type) 
        {
            error("type error");
        }
    }

    public Type check(Type p1, Type p2) 
    {
        if ((Type.Bool == p1) && (Type.Bool == p2)) 
        {
            return Type.Bool;
        }
        else 
        {
            return null;
        }
    }

    public Expr gen() 
    {
        int f = newlabel(); 
        int a = newlabel();
        
        Temp temp = new Temp(type);
        this.jumping(0, f);
        
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitlabel(f); 
		emit(temp.toString() + " = false");
        emitlabel(a);
        
        return temp;
    }

    public String toString() 
    {
        return expr1.toString() + " " + op.toString() + " " + expr2.toString();
    }
}