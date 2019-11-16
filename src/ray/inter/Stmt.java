package ray.inter;

/**
 * DESCRIPTION: Node的两个子类之一，对应于语句节点,每个语句构造都被实现为Stmt的一个子类。一个构造的组成部分对应的字段是对应子类的对象。
*/
public class Stmt extends Node {
    
    //处理抽象语法树的构造，相关处理工作在子类中进行
    public Stmt() 
    {
        //do nothing
    }
    public static Stmt Null = new Stmt();  //表示一个空的语句序列
    
    //处理三地址代码的生成
    public void gen(int b, int a)  //调用时的参数是语句开始处的标号和语句的下一句指令的标号
    {
        //do nothing
    }
    int after = 0;  //保存语句的下一条指令的标号
    public static Stmt Enclosing = Stmt.Null;  //用于break语句
}