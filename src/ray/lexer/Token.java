package ray.lexer;

/**
 * @DESCRIPTION: 做出语法分析决定相关类，表示一个词法单元
*/
public class Token {

    public final int tag;
    
    public Token(int t) 
    { 
        tag = t; 
    }

    public String toString() 
    { 
        return "" + (char)tag; 
    }
}