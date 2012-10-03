package org.sentinel.server;

public interface Application
{
    
    void handleRequest(SentinelRequest request, SentinelResponse response);
    
}
