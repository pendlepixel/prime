package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

public class Logical extends Expr {
    
    public Expr expr1;
    public Expr expr2;

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