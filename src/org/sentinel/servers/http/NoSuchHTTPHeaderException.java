package org.sentinel.servers.http;

public class NoSuchHTTPHeaderException extends HTTPException
{

    public NoSuchHTTPHeaderException(String string)
    {
        super("No such HTTP header " + string);
    }
    
}
