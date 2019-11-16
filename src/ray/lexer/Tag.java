package ray.lexer;

/**
 * DESCRIPTION: 关键字类，定义了各个词法单元对应的常量,其中三个常量INDEX, MINUS, TEMP不是词法单元，它们将在抽象语法树中使用。
*/
public class Tag {
    
    public final static int AND   = 256;
    public final static int BASIC = 257;
    public final static int BREAK = 258;
    public final static int DO    = 259;
    public final static int ELSE  = 260;
    public final static int EQ    = 261;
    public final static int FALSE = 262;
    public final static int GE    = 263;
    public final static int ID    = 264;
    public final static int IF    = 265;
    public final static int INDEX = 266;  //数组
    public final static int LE    = 267;
    public final static int MINUS = 268;
    public final static int NE    = 269;
    public final static int NUM   = 270;
    public final static int OR    = 271;
    public final static int REAL  = 272;
    public final static int TEMP  = 273;
    public final static int TRUE  = 274;
    public final static int WHILE = 275;
}
