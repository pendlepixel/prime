package ray.inter;

import ray.lexer.Token;
import ray.symbols.Array;
import ray.symbols.Type;

/**
 * @DESCRIPTION: 实现运算符<,<=,==,!=,>=,>的相关类
*/
public class Rel extends Logical {
    
    public Rel(Token tok, Expr x1, Expr x2) 
    { 
        super(tok, x1, x2); 
    }

    /**
    * @DESCRIPTION: 检查两个运算分量是否具有相同的类型，但他们不是数组，为简单起见，这里不允许类型转换
    */
    public Type check(Type p1, Type p2) 
    {
        if (p1 instanceof Array || p2 instanceof Array ) 
        {
            return null;
        }
        else if (p1 == p2) 
        {
            return Type.Bool;
        }
        else 
        {
            return null;
        }
    }

    public void jumping(int t, int f) 
    {
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();

        String test = a.toString() + " " + op.toString() + " " + b.toString();
        emitjumps(test, t, f);
    }
}