package ray.inter;

import ray.lexer.Token;

/**
 * @DESCRIPTION: 逻辑运算OR的相关类
*/
public class Or extends Logical {
    
    public Or(Token tok, Expr x1, Expr x2)
    { 
        super(tok, x1, x2); 
    }

    /**
    * @DESCRIPTION: 生成一个布尔表达式B = B1 || B2的跳转代码，其中：tok相当于||，B1相当于expr1，B2相当于expr2
    */
    public void jumping(int t, int f) 
    {
        //变量label保证了B1的true出口被正确地设置为B的代码的结尾处。
        int label = (0 != t) ? t : newlabel();
        
        //B1表示expr1;B2表示expr2
        //对于or语句，会先判断B1，如果为true,不会再判断B2.
        //因此对于B1来讲，如果B1为true，则B为true，而B为true时，出口为t,所以B1为true时，出口也为t;
        //如果B1为false,此时势必要去读B2的内容，因此此时B2的false为0，表示读下一条语句
        expr1.jumping(label, 0);

        //由于是先判断的B1，后判断的B2，且只有B1为false时才会判断B2,因此，B2为true代表B为true;B2为false代表B为false;
        //所以B2的true出口和false出口和B是一样的，即B2的t出口为t，f出口为f
        expr2.jumping(t, f);
        
        if (0 == t)
        {
            emitlabel(label);
        }
    }
}