package org.sentinel.servers.helloworld;

import org.sentinel.server.*;

public class Response implements SentinelResponse
{

    private String rawResponse = "";

    public Response(final String rawResponse)
    {
        this.rawResponse = rawResponse;
    }

    @Override
    public String getRawResponse()
    {
        return rawResponse;
    }
    
}
