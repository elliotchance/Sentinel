package org.sentinel.server;

public interface SentinelServer
{

    SentinelResponse handleRequest(final SentinelRequest request);
    
}
