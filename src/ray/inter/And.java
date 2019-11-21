package ray.inter;

import ray.lexer.Token;

/**
 * @DESCRIPTION: 逻辑运算AND的相关类
*/
public class And extends Logical {
    
    public And(Token tok, Expr x1, Expr x2) 
    { 
        super(tok, x1, x2); 
    }

    /**
    * @DESCRIPTION: 生成一个布尔表达式B = B1 && B2的跳转代码，其中：tok相当于&&，B1相当于expr1，B2相当于expr2
    */
    public void jumping(int t, int f)
    {
        int label = (0 != f) ? f : newlabel();
        
        //与Or中的jumping逻辑类似，expr1如果是true,则要继续读下一条expr2,即expr1的true接口为0；expr1如果是false,则整个语句为false,出口为label
        expr1.jumping(0, label);

        //检测expr2，说明expr1的结果是true,那么expr2为true,表示整条表达式为true；expr2为false,整条表达式也为false，所以expr2的t和f出口和整条表达式的t和f出口一致
        expr2.jumping(t, f);
		
        if (0 == f) 
        {
            emitlabel(label);
        }
		
    }
}