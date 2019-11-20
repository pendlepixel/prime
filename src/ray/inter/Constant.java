package ray.inter;

import ray.lexer.Num;
import ray.lexer.Token;
import ray.lexer.Word;
import ray.symbols.Type;

/**
 * @DESCRIPTION: 表示抽象语法构造树中的一个叶子结点
*/
public class Constant extends Expr {
    
    /**
    * @DESCRIPTION: 在抽象语法树中构造出一个标号为tok,类型为p的叶子结点
    */
    public Constant(Token tok, Type p) 
    { 
        super(tok, p); 
    }

    /**
    * @DESCRIPTION: 重载之后的Constant，可以根据一个整数创建一个常量，代表这个叶子结点是一个整数
    */
    public Constant(int i) 
    { 
        super(new Num(i), Type.Int); 
    }

    public static final Constant True  = new Constant(Word.True,  Type.Bool);
    public static final Constant False = new Constant(Word.False, Type.Bool);

    /**
    * @DESCRIPTION: 结点的具体跳转逻辑，如果这个常量是True，并且t不是特殊符号0，则生成一个目标为t的跳转指令；
    * 如果这个常量是False，并且f不是特殊符号0，则生成一个目标为f的跳转指令。
    */
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
