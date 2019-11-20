package ray.inter;

import ray.symbols.Type;

public class While extends Stmt {
    
    Expr expr; 
    Stmt stmt;
    
    public While() 
    {
        expr = null; 
        stmt = null; 
    }

    public void init(Expr x, Stmt s) 
    {
        expr = x; 
        stmt = s;
        
        if (Type.Bool != expr.type) 
        {
            expr.error("boolean required in while");
        }
    }

    /**
    * @DESCRIPTION: 生成三地址代码，这个函数和类If中的相应函数gen()在本质上有着相通之处。不同之处在于标号a被保存在字段after中，且stmt的代码之后紧跟着一个
    * 目标为b的跳转指令。这个指令使得while循环进入下一次迭代
    */
    public void gen(int b, int a) 
    {
        after = a;  //保存标号a
        expr.jumping(0, a);
        int label = newlabel();  //用于stmt的标号
        
        emitlabel(label); 
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}