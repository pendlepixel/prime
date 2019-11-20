package ray.inter;

import ray.lexer.Token;
import ray.symbols.Type;

/**
 * @DESCRIPTION: 表示算数运算符的类
*/
public class Arith extends Op {
    
    public Expr expr1;
    public Expr expr2;
    
    public Arith(Token tok, Expr x1, Expr x2) 
    {
        super(tok, null); 
        expr1 = x1; 
        expr2 = x2;
        
        //简单检查类型，判断两个运算分量是否可以被类型强制为一个常见的数字类型，如果可以type被设置为结果类型，否则，报告一个类型错误。
        //即判断是否可以被类型强制为char,int,float这三种，不能的话，报告一个错误
        type = Type.max(expr1.type, expr2.type);
        if (null == type) 
        {
            error("type error");
        }
    }

    /**
    * @DESCRIPTION: 将表达式的子表达式规约为地址，并将表达式的运算符作用于这些地址，从而构造出一个三地址指令的右部
    */
    public Expr gen() 
    {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    public String toString() 
    {
        return expr1.toString() + " " + op.toString() + " " + expr2.toString();
    }
}