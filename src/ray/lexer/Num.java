package ray.lexer;

/**
 * DESCRIPTION: 整数相关类
*/
public class Num extends Token {
    
    public final int value;
    
    /**
    * DESCRIPTION: 整数类型的构造函数，此对象值为v，类型为Tag.NUM
    * PARAM[IN] : v, 将被作为整数类型的对象的value值
    */
    public Num(int v) 
    { 
        super(Tag.NUM); 
		value = v; 
    }

    public String toString() 
    { 
        return "" + value; 
    }
}