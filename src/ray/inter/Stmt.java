package ray.inter;

/**
 * DESCRIPTION: Node的两个子类之一，对应于语句节点
*/
public class Stmt extends Node {
    
    public Stmt() 
    {
        //do nothing
    }
    
    public static Stmt Null = new Stmt();
    
    public void gen(int b, int a)  //调用时的参数是语句开始处的标号和语句的下一句指令的标号
    {
        //do nothing
    }

    int after = 0;  //保存语句的下一条指令的标号
    public static Stmt Enclosing = Stmt.Null;  //用于break语句
}