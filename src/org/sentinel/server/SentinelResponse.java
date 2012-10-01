package org.sentinel.server;

public class SentinelResponse
{

    private String rawResponse = "";

    public SentinelResponse(final String rawResponse)
    {
        this.rawResponse = rawResponse;
    }

    public String getRawResponse()
    {
        return rawResponse;
    }
    
}
