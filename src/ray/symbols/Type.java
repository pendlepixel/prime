package ray.symbols;

import ray.lexer.Tag;
import ray.lexer.Word;

public class Type extends Word {

    public int width = 0;  //width用于存储分配
    
    public Type(String s, int tag, int w ) 
    { 
        super(s, tag); 
        width = w; 
    }

    public static final Type Int   = new Type( "int",   Tag.BASIC, 4);
    public static final Type Float = new Type( "float", Tag.BASIC, 8);
    public static final Type Char  = new Type( "char",  Tag.BASIC, 1);
    public static final Type Bool  = new Type( "bool",  Tag.BASIC, 1);
    
    public static boolean numeric( Type p) 
    {
        if ((Type.Char == p) || (Type.Int == p) || (Type.Float == p)) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    public static Type max(Type p1, Type p2) 
    {
        if ((!numeric(p1)) || (!numeric(p2)))
        {
            return null;
        }
        else if ((Type.Float == p1) || (Type.Float == p2))
        {
            return Type.Float;
        }
        else if ((Type.Int == p1) || (Type.Int == p2))
        {
            return Type.Int;
        }
        else 
        {
            return Type.Char;
        }  
    }
}
