package ray.lexer;

/**
 * DESCRIPTION: 用于管理保留字、标识符和像&&这样的复合词法单元的词素
 * 也可以用来管理在中间代码中运算符的书写形式；比如单目减号
*/
public class Word extends Token {

    public String lexeme = "";
    
    /**
    * DESCRIPTION: 符合词法单元类型的构造函数，此对象值为s，类型为tag
    * PARAM[IN] : s, 将被作为词法单元类型的对象的lexeme值
    * PARAM[IN] : tag, 将被作为词法单元类型的对象的tag值
    */
    public Word(String s, int tag) 
    { 
        super(tag); 
        lexeme = s; 
    }

    public String toString() 
    { 
        return lexeme; 
    }

    public static final Word and   = new Word( "&&",    Tag.AND   ); 
    public static final Word or    = new Word( "||",    Tag.OR    );
    public static final Word eq    = new Word( "==",    Tag.EQ    ); 
    public static final Word ne    = new Word( "!=",    Tag.NE    );
    public static final Word ge    = new Word(" >=",    Tag.GE    ); 
    public static final Word le    = new Word( "<=",    Tag.LE    );
    public static final Word minus = new Word( "minus", Tag.MINUS );
    public static final Word True  = new Word( "true",  Tag.TRUE  );
    public static final Word False = new Word( "false", Tag.FALSE );
    public static final Word temp  = new Word( "t",     Tag.TEMP  );

}
