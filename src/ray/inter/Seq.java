package ray.inter;

/**
 * @DESCRIPTION: 实现一个语句序列的相关类，此类对应于文法中的非终结符号stmts。
*/
public class Seq extends Stmt {
    
    Stmt stmt1; 
    Stmt stmt2;
    
    public Seq(Stmt s1, Stmt s2) 
    { 
        stmt1 = s1; 
        stmt2 = s2;
    }

    public void gen(int b, int a) 
    {
        //对空的判断是为了避免使用标号,空语句Stmt.Null不会产生任何代码，因为类Stmt中的方法gen不做任何处理
        if (Stmt.Null == stmt1)
        {
            stmt2.gen(b, a);
        }
        else if (Stmt.Null == stmt2) 
        {
            stmt1.gen(b, a);
        }
        else 
        {
            int label = newlabel();
            stmt1.gen(b, label);
            
            emitlabel(label);
            stmt2.gen(label, a);
        }
    }
}