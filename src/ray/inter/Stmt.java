package ray.inter;

/**
 * @DESCRIPTION: Node的两个子类之一，对应于语句节点,每个语句构造都被实现为Stmt的一个子类。一个构造的组成部分对应的字段是对应子类的对象。
*/
public class Stmt extends Node {
    
    //处理抽象语法树的构造，相关处理工作在子类中进行
    public Stmt() 
    {
        //do nothing
    }
    public static Stmt Null = new Stmt();  //表示一个空的语句序列
    
    /**
    * @DESCRIPTION: 处理三地址代码的生成
    * @PARAM[IN]: b, 标记这个语句的代码的开始处
    * @PARAM[IN]: a, 标记这个语句的代码之后的第一条指令
    */
    public void gen(int b, int a)  //调用时的参数是语句开始处的标号和语句的下一句指令的标号
    {
        //do nothing
    }
    int after = 0;  //保存语句的下一条指令的标号
    public static Stmt Enclosing = Stmt.Null;  //空语句序列，用于break语句，在语法分析中被用于跟踪外层构造
}