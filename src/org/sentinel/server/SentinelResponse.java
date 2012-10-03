package org.sentinel.server;

public interface SentinelResponse
{

    public String getRawResponse();
    
    public void write(String data);
    
}
