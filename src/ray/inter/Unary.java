package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

/**
 * @DESCRIPTION: 表示单目运算符的类（与Arith对应，Arith处理双目运算，Unary处理单目）,仅限于算数运算
*/
public class Unary extends Op {

    public Expr expr;
    
    public Unary(Token tok, Expr x)  //处理单目减法，对！的处理见Not
    {  
        super(tok, null); 
        expr = x;

        type = Type.max(Type.Int, expr.type);
        if (null == type) 
        {
            error("type error");
        }
    }

    public Expr gen() 
    { 
        return new Unary(op, expr.reduce()); 
    }

    public String toString() 
    { 
        return op.toString() + " " + expr.toString(); 
    }
}