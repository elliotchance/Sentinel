package org.sentinel.servers.http;

public class NoSuchHTTPStatusCodeException extends HTTPException
{

    public NoSuchHTTPStatusCodeException(String string)
    {
        super("No such HTTP status code " + string);
    }
    
}
