package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

public class Expr extends Node {
    
    public Token op;
    public Type type;
    
    Expr(Token tok, Type p) 
    { 
        op = tok; 
        type = p; 
    }

    public Expr gen() 
    { 
        return this; 
    }

    public Expr reduce() 
    { 
        return this; 
    }

    public void jumping(int t, int f) 
    { 
        emitjumps(toString(), t, f); 
    }

    public void emitjumps(String test, int t, int f) 
    {
        if ((0 != t) && (0 != f))
        {
            emit("if" + test + "goto L" + t);
            emit("goto L" + f);
        }
        else if (0 != t)
        {
            emit("if " + test + " goto L" + t);
        }
        else if (0 != f) 
        {
            emit("iffalse " + test + " goto L" + f);
        }
        else  //不生成指令，因为t和f都直接穿越
        {
            //do nothing
        };
    }

    public String toString() 
    { 
        return op.toString(); 
    }
}