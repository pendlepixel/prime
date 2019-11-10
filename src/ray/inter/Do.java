package ray.inter;

import ray.symbols.Type;

public class Do extends Stmt {

    Expr expr; 
    Stmt stmt;
    
    public Do() 
    { 
        expr = null; 
        stmt = null; 
    }

    public void init(Stmt s, Expr x) 
    {
        expr = x; 
        stmt = s;

        if (Type.Bool != expr.type) 
        {
            expr.error("boolean required in do");
        }
    }

    public void gen(int b, int a) 
    {
        after = a;
        int label = newlabel();  //用于expr的标号
        stmt.gen(b, label);
		
        emitlabel(label);
		
        expr.jumping(b, 0);
    }
}