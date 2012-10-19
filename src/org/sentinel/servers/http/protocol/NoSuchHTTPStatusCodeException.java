package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.HTTPException;

public class NoSuchHTTPStatusCodeException extends HTTPException
{

    public NoSuchHTTPStatusCodeException(String string)
    {
        super("No such HTTP status code " + string);
    }
    
}
