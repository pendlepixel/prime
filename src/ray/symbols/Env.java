package ray.symbols;

import java.util.Hashtable;

import ray.inter.Id;
import ray.lexer.Token;

/**
 * DESCRIPTION: 符号表相关的类
*/
public class Env {

    private Hashtable table;
    protected Env prev;

    /**
     * DESCRIPTION: 构造函数，创建一个对象，该对象包含一个名为table的散列表。这个对象的字段prev被设置为参数n
     * PARAM[IN]: n，这个参数n的值是一个环境，这个对象被链接到环境
     * RETURN: 
    */
    public Env(Env n) 
	{ 
        table = new Hashtable(); 
        prev = n; 
    }

    /**
     * DESCRIPTION: 向当前表中加入一个新的条目。
     * PARAM[IN]: w, 加入条目的key值
     * PARAM[IN]: i, 加入条目的value值
     * RETURN: void
    */
    public void put(Token w, Id i) 
	{ 
        table.put(w, i); 
    }
    
    /**
     * DESCRIPTION: 从当前表中得到一个标识符的条目。从当前块的符号表中开始搜索链接符号表
     * PARAM[IN]: w，key值，根据此值进行搜索
     * RETURN: 搜索成功，返回一个符号表条目；搜索失败，返回null
    */
    public Id get(Token w) 
	{
        for (Env e = this; e != null; e = e.prev) 
		{
            Id found = (Id)(e.table.get(w));
            if (null != found)
			{
			    return found;
			}
        }
        return null;
    }
}