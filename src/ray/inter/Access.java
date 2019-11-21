package ray.inter;

import ray.lexer.Tag;
import ray.lexer.Word;
import ray.symbols.Type;
 
/**
 * @DESCRIPTION: 表示数组访问的类
*/
public class Access extends Op {

    public Id array;  //数组名称
    public Expr index;  //数组元素格式
    
    public Access(Id a, Expr i, Type p)  //p是将数组平组化后的元素类型
	{  
        super(new Word("[]", Tag.INDEX), p);
        array = a; 
		index = i;
    }
	
    public Expr gen() 
	{ 
	    return new Access(array, index.reduce(), type); 
	}
	
    public void jumping(int t, int f) 
	{ 
	    emitjumps(reduce().toString(), t, f); 
	}
	
    public String toString() 
	{
		return array.toString() + " [ " + index.toString() + " ]";
	}
}