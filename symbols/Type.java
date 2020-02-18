package symbols;
import lexer.*;

/**
 * @DESCRIPTION: 我们将Type定义为类Word的子类，因为像int这样的基本类型名字就是保留字，将被词法分析器从词素映射为适当的对象。
 * 对应于基本类型的对象是Type.Int, Type.Float, Type.Char, Type.Bool.这些对象从超类中继承了字段tag,相应的值被设置为Tag.BASIC，
 * 因此语法分析器以同样的方式处理了他们。Type就是专门处理基础类型的类
*/
public class Type extends Word {

   public int width = 0;  //width用于存储分配，单位是字节

   public Type(String s, int tag, int w) 
   { 
      super(s, tag); 
      width = w; 
   }

   public static final Type Int   = new Type( "int",   Tag.BASIC, 4 );
   public static final Type Float = new Type( "float", Tag.BASIC, 8 );
   public static final Type Char  = new Type( "char",  Tag.BASIC, 1 );
   public static final Type Bool  = new Type( "bool",  Tag.BASIC, 1 );

   /**
     * @DESCRIPTION: 判断一个类型是否是基础类型char,int,float,是，返回true,不是，返回false
     * @PARAM[IN]: p, 进行判断的Type
     * @RETURN: true/false，是基础类型返回true,否则返回false
    */
   public static boolean numeric(Type p) 
   {
      if (p == Type.Char || p == Type.Int || p == Type.Float) 
      {
         return true;
      }
      else 
      {
         return false;
      }
   }

   /**
     * @DESCRIPTION: 在两个“数字”类型之间进行类型转换，“数字”类型包括Type.Char,Type.Int,Type.Float.
     * 当一个算数运算符应用于两个数字类型时，结果类型是这两个类型的max值。
     * @PARAM: NULL
     * @RETURN: Type类型的对象
    */
   public static Type max(Type p1, Type p2 ) 
   {
      if ( ! numeric(p1) || ! numeric(p2) ) 
      {
         return null;
      }
      else if ( p1 == Type.Float || p2 == Type.Float ) 
      {
         return Type.Float;
      }
      else if ( p1 == Type.Int   || p2 == Type.Int   ) 
      {
         return Type.Int;
      }
      else 
      {
         return Type.Char;
      }
   }
}
