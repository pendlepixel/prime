package ray.lexer;

/**
 * DESCRIPTION: 处理浮点数相关的类
*/
public class Real extends Token {
    
    public final float value;
    
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