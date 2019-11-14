package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

/**
 * DESCRIPTION: Node的两个子类之一，对应于表达式节点
*/
public class Expr extends Node {
    
    public Token op;  //表示一个节点上的运算符
    public Type type;  //表示一个节点上的类型
    
    Expr(Token tok, Type p) 
    { 
        op = tok; 
        type = p; 
    }

    /**
    * DESCRIPTION: 返回一个“项”，该项可以成为一个三地址指令的右部。给定一个表达式E = E1 + E2, 方法gen()返回一个项x1 + x2,其中，x1和x2分别是存放
    * E1和E2的值的地址。如果这个对象是一个地址，就可以返回this值。Expr的子类通常会重新实现gen().
    */
    public Expr gen() 
    { 
        return this; 
    }


    /**
    * DESCRIPTION: 把一个表达式计算（或者说“规约”）成为一个单一的地址。也就是说，他返回一个常量、一个标识符、或者一个临时名字。给定一个表达式E，方法reduce返回一个
    * 存放E的值的临时变量t。如果这个对象是一个地址，那么this仍然是正确的返回值。
    */
    public Expr reduce() 
    { 
        return this; 
    }

    /**
    * DESCRIPTION: 布尔表达式B的跳转方法。这个方法的两个参数t和f，分别称为表达式B的true出口和false出口。如果B的值为真，代码中就包含一个目标为t的跳转指令；
    * 如果B的值为假，就有一个目标为f的指令。按照惯例，特殊符号0表示控制流从B穿越，到达B的代码之后的下一个指令。
    */
    public void jumping(int t, int f) 
    { 
        emitjumps(toString(), t, f); 
    }

    public void emitjumps(String test, int t, int f) 
    {
        if ((0 != t) && (0 != f))
        {
            emit("if" + test + "goto L" + t);
            emit("goto L" + f);
        }
        else if (0 != t)
        {
            emit("if " + test + " goto L" + t);
        }
        else if (0 != f) 
        {
            emit("iffalse " + test + " goto L" + f);
        }
        else  //不生成指令，因为t和f都直接穿越
        {
            //do nothing
        };
    }

    public String toString() 
    { 
        return op.toString(); 
    }
}