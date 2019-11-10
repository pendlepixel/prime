package ray.inter;

import ray.lexer.Num;
import ray.lexer.Token;
import ray.lexer.Word;
import ray.symbols.Type;

public class Constant extends Expr {
    
    public Constant(Token tok, Type p) 
    { 
        super(tok, p); 
    }

    public Constant(int i) 
    { 
        super(new Num(i), Type.Int); 
    }

    public static final Constant True  = new Constant(Word.True,  Type.Bool);
    public static final Constant False = new Constant(Word.False, Type.Bool);

    public void jumping(int t, int f) 
    {
        if ((True == this) && (0 != t)) 
        {
            emit("goto L" + t);
        }
        else if ((False == this) && (0 != f)) 
        {
            emit("goto L" + f);
        }
        else
        {
            //do nothing
        }
    }
}
