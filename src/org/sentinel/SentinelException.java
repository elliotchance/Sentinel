package org.sentinel;

public class SentinelException extends Exception
{

    public SentinelException(String string)
    {
        super(string);
    }
    
    public SentinelException(Exception cause)
    {
        super(cause);
    }
    
}
