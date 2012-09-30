package org.sentinel.server;

public interface SentinelProtocol
{

    SentinelRequest handleRawRequest(final String input);
    
}
