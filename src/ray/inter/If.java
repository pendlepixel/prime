package ray.inter;

import ray.symbols.Type;

public class If extends Stmt {
    
    Expr expr;  //保存了E对应的节点
    Stmt stmt;  //保存了S对应的节点

    /**
    * @DESCRIPTION: 构造函数，为语句：if(E) S构造一个结点
    * @PARAM[IN]: x, 保存了E对应的结点
    * @PARAM[IN]: s, 保存了S对应的结点
    */
    public If(Expr x, Stmt s) 
    {
        expr = x; 
        stmt = s;
        
        if (Type.Bool != expr.type) 
        {
            expr.error("boolean required in if");
        }
    }

    public void gen(int b, int a) 
    {
        int label = newlabel();  //stmt的代码的标号

        //为真时，到下一条语句;为假时，到这个IF语句的代码之后的第一条指令，一般为else或者elseif或者之后的别的逻辑
        expr.jumping(0, a);
        
        emitlabel(label); 
        {
            stmt.gen(label, a);
        }
    }
}