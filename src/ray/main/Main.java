package ray.main;

import java.io.IOException;

import ray.lexer.Lexer;
import ray.parser.Parser;

public class Main {

    public static void main(String[] args) throws IOException 
    {
        Lexer lex = new Lexer();
        Parser parse = new Parser(lex);
        parse.program();
        System.out.write('\n');
    }
}