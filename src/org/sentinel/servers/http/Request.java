package org.sentinel.servers.http;

import org.sentinel.server.SentinelRequest;

public class Request implements SentinelRequest
{
    
    protected HTTPRequestHeaders httpHeaders;
    
    protected byte[] body;

    public HTTPRequestHeaders getHTTPHeaders()
    {
        return httpHeaders;
    }

    public void setHTTPHeaders(HTTPRequestHeaders httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }
    
}
