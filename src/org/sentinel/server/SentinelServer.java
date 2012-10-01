package org.sentinel.server;

public interface SentinelServer
{
    
    public static final int DEFAULT_PORT = 4040;
    
    public static final int DEFAULT_HELLOWORLD_PORT = 4040;
    
    public static final int DEFAULT_HTTP_PORT = 4041;

    SentinelResponse handleRequest(final SentinelRequest request);
    
}
