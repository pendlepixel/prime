package ray.inter;

import ray.lexer.Lexer;

/**
 * DESCRIPTION: 抽象语法树的实现类
*/
public class Node {
    
    int lexline = 0;
    
    Node() 
    {
        lexline = Lexer.line; 
    }

    void error(String s) 
    { 
        throw new Error("near line " + lexline + ": " + s); 
    }

    //labels以及newlabel()和emitlabel()用来生成三地址代码
    static int labels = 0;
    
    public int newlabel() 
    { 
        ++labels;
        return labels;
    }

    public void emitlabel(int i) 
    { 
        System.out.print("L" + i + ":"); 
    }

    public void emit(String s) 
    {
        System.out.println("\t" + s); 
    }
}