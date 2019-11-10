package ray.lexer;

/**
 * DESCRIPTION: 整数相关类
*/
public class Num extends Token {
    
    public final int value;
    
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