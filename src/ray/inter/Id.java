package ray.inter;

import ray.lexer.Word;
import ray.symbols.Type;

/**
 * DESCRIPTION: 表示标识符的类。对应于一个标识符的类Id的结点是一个叶子结点
*/
public class Id extends Expr {
    
    public int offset;  //这个标识符的相对地址

    public Id(Word id, Type p, int b) 
    { 
        super(id, p); 
        offset = b; 
    }
}