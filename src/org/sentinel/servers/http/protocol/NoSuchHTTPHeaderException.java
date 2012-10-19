package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.HTTPException;

public class NoSuchHTTPHeaderException extends HTTPException
{

    public NoSuchHTTPHeaderException(String string)
    {
        super("No such HTTP header " + string);
    }
    
}
