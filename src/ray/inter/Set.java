package ray.inter;

import ray.symbols.Type;

public class Set extends Stmt {

    public Id id; 
    public Expr expr;

    public Set(Id i, Expr x) 
    {
        id = i; 
        expr = x;
        
        if (null == check(id.type, expr.type)) 
        {
            error("type error");
        }
    }

    public Type check(Type p1, Type p2) 
    {
        if (Type.numeric(p1) && Type.numeric(p2)) 
        {
            return p2;
        }
        else if ((Type.Bool == p1) && (Type.Bool == p2)) 
        {
            return p2;
        }
        else 
        {
            return null;
        }
    }

    public void gen(int b, int a) 
    {
        emit(id.toString() + " = " + expr.gen().toString());
    }
}