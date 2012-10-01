package org.sentinel.servers.http;

import org.sentinel.server.ServerException;

public class HTTPException extends ServerException
{

    public HTTPException(String string)
    {
        super(string);
    }
    
}
