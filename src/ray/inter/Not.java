package ray.inter;

import ray.lexer.Token;

/**
 * @DESCRIPTION: 逻辑运算!的相关类
*/
public class Not extends Logical {
    
    public Not(Token tok, Expr x2) 
    { 
        super(tok, x2, x2);
    }

    /**
    * @DESCRIPTION: 生成一个布尔表达式B = !B2的跳转代码，其中：tok相当于!，B2相当于expr2
    */
    public void jumping(int t, int f) 
    {
        //此处跳转逻辑比较好理解，B2为true,则!B2为false,反之亦然。即B2的t和f出口恰好与B的f和t出口相对应
        expr2.jumping(f, t);
    }

    public String toString()
    { 
        return op.toString() + " " + expr2.toString(); 
    }
}