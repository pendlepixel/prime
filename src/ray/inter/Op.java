package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

/**
 * DESCRIPTION: 表示操作符的类，这个类的子类包括：表示算数运算符的类Arith;表示单目运算符的类Unary;表示数组访问的类Access.
*/
public class Op extends Expr {
    
    public Op(Token tok, Type p) 
    {
        super(tok, p); 
    }

    /**
    * DESCRIPTION: 通过gen()生成一个项，再生成一个指令把这个项赋值给一个新的临时名字，并把名字返回
    */
    public Expr reduce() 
    {
        Expr x = gen();
        Temp t = new Temp(type);
        
        emit(t.toString() + " = " + x.toString());
        return t;
    }
}