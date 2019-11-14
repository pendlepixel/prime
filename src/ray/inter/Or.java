package ray.inter;

import ray.lexer.Token;

/**
 * DESCRIPTION: 逻辑运算OR的相关类
*/
public class Or extends Logical {
    
    public Or(Token tok, Expr x1, Expr x2)
    { 
        super(tok, x1, x2); 
    }

    /**
    * DESCRIPTION: 生成一个布尔表达式B = B1 || B2的跳转代码
    */
    public void jumping(int t, int f) 
    {
        int label = t != 0 ? t : newlabel();
        
        expr1.jumping(label, 0);
        expr2.jumping(t, f);
        
        if (0 == t)
        {
            emitlabel(label);
        }
    }
}