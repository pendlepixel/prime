package symbols;
import lexer.*;

/**
 * @DESCRIPTION: 数组类型的相关类
*/
public class Array extends Type {
   public Type of;  //数组的元素类型
   public int size = 1;  //元素个数

   public Array(int sz, Type p) 
   {
       super("[]", Tag.INDEX, sz*p.width);  //new Word( "[]",    Tag.INDEX   ) 
       size = sz;  
       of = p;
   }

   public String toString() 
   { 
      return "[" + size + "] " + of.toString(); 
   }
}
