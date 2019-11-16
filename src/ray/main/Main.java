package ray.main;

import java.io.IOException;

import ray.lexer.Lexer;
import ray.parser.Parser;

/**
 * DESCRIPTION: 实现词法分析和语法分析的相关类
*/
public class Main {

    /**
    * DESCRIPTION: 程序的执行就是从此main()开始的
    */
    public static void main(String[] args) throws IOException 
    {
        Lexer lex = new Lexer();  //构建一个词法分析器
        Parser parse = new Parser(lex);  //构建一个语法分析器
        parse.program();  //调用语法分析器中的方法进行分析
        System.out.write('\n');
    }
}