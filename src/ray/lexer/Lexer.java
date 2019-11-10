package ray.lexer;

import java.io.IOException;
import java.util.Hashtable;
import ray.symbols.Type;

public class Lexer {
    
    public static int line = 1;  //输入行计数
    char peek = ' ';  //存放下一个输入字符
    Hashtable words = new Hashtable();  //6-17行处理保留字
    
    void reserve(Word w) 
    { 
        words.put(w.lexeme, w); 
    }

    //words初始化
    public Lexer() 
    {
        reserve( new Word("if",     Tag.IF)    );
        reserve( new Word("else",   Tag.ELSE)  );
        reserve( new Word("while",  Tag.WHILE) );
        reserve( new Word("do",     Tag.DO)    );
        reserve( new Word("break",  Tag.BREAK) );
        reserve( Word.True  );  
        reserve( Word.False );
        reserve( Type.Int   );  
        reserve( Type.Char  );
        reserve( Type.Bool  );  
        reserve( Type.Float );
    }

    void readch() throws IOException 
    { 
        peek = (char)System.in.read(); 
    }
    
    boolean readch(char c) throws IOException 
    {
        readch();
        
        if ( peek != c) 
        {
            return false;
        }

        peek = ' ';
        return true;
    }

    /**
     * DESCRIPTION: 函数返回一个词法单元对象
     * PARAM: NULL
     * RETURN: Token对象
    */
    public Token scan() throws IOException 
    {
        //跳过空格，换行和制表符
        for ( ; ; readch()) 
        {
            if ((' ' == peek) || ('\t' == peek)) 
            {
                continue;
            }
            else if ('\n' == peek) 
            {
                line = line + 1;
            }
            else 
            {
                break;
            }
        }

        switch(peek) 
        {
            case '&':
            {
                if ( readch('&') ) return Word.and;  
                else return new Token('&');
            }
            case '|':
            {
                if ( readch('|') ) return Word.or;   
                else return new Token('|');
            }
            case '=':
            {
                if ( readch('=') ) return Word.eq;   
                else return new Token('=');
            }
            case '!':
            {
                if ( readch('!') ) return Word.ne;   
                else return new Token('!');
            }
            case '<':
            {
                if ( readch('<') ) return Word.le;   
                else return new Token('<');
            }
            case '>':
            {
                if ( readch('>') ) return Word.ge;   
                else return new Token('>');
            }
        }

        //处理数字
        if (Character.isDigit(peek)) 
        {
            int v = 0;
            
            do 
            {
                v = 10 * v + Character.digit(peek, 10); 
                readch();
            }while ( Character.isDigit(peek) );
            
            if ('.' != peek) 
            {
                return new Num(v);
            }

            float x = v; 
            float d = 10;
            
            for (;;) 
            {
                readch();
                if (!Character.isDigit(peek)) 
                {
                    break;
                }

                x = x + Character.digit(peek, 10) / d; 
                d = d * 10;
            }

            return new Real(x);
        }

        //分析保留字和标识符
        if (Character.isLetter(peek)) 
        {
            StringBuffer b = new StringBuffer();
            
            do 
            {
                b.append(peek); 
				readch();
            } while(Character.isLetterOrDigit(peek));
            
            String s = b.toString();
            Word w = (Word)words.get(s);
            
            if (null != w) 
            {
                return w;
            }
            
			w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        Token tok = new Token(peek); 
        peek = ' ';
        return tok;
    }
}
