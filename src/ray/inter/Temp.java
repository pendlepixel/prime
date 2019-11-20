package ray.inter;

import ray.lexer.Word;
import ray.symbols.Type;

/**
 * @DESCRIPTION: 构造临时变量的类，生成一个临时变量，作为一些表达式的左部
*/
public class Temp extends Expr {
    
    static int count = 0;
    int number = 0;
    
     /**
    * @DESCRIPTION: 构造临时变量的构造函数，临时变量也要标明类型
    * @PARAM[IN]: p, 临时变量的类型
    */
    public Temp(Type p) 
    { 
        super(Word.temp, p);
        ++count;
        number = count;
    }

    public String toString() 
    { 
        return "t" + number; 
    }
}