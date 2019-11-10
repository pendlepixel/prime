package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

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