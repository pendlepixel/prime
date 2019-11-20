package ray.lexer;

/**
 * @DESCRIPTION: 处理浮点数相关的类
*/
public class Real extends Token {
    
    public final float value;
    
    /**
    * @DESCRIPTION: 浮点数类型的构造函数，此对象值为v，类型为Tag.REAL
    * @PARAM[IN] : v, 将被作为浮点数类型的对象的value值
    */
    public Real(float v) 
    { 
        super(Tag.REAL); 
		value = v; 
    }

    public String toString() 
    { 
        return "" + value; 
    }
}