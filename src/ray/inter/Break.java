package ray.inter;

/**
 * @DESCRIPTION: 一个break语句把控制流转出它的外围循环或者外围switch语句，类break使用字段stmt来保存它的外围语句构造。
 * （语法分析器保证Stmt.Enclosing表示了其外围构造对应的语法树结点）。一个Break对象的代码是一个目标为标号stmt.after的
 * 跳转指令。这个标号标记了紧跟在stmt的代码之后的指令。
*/
public class Break extends Stmt {
    
    Stmt stmt;
    
    public Break() 
    {
        if (Stmt.Enclosing == Stmt.Null)
        {
            error("unenclosed break");
        }

        stmt = Stmt.Enclosing;
    }

    public void gen(int b, int a) 
    {
        emit("goto L" + stmt.after);
    }
}