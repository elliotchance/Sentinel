package org.sentinel.servers.helloworld;

import org.sentinel.server.SentinelRequest;
import org.sentinel.server.SentinelResponse;
import org.sentinel.server.SentinelServer;

/**
 * The Hello World server always returns the classic "Hello, World!" message.
 *
 * @author Elliot Chance
 */
public class Server implements SentinelServer
{
    
    public static final String RESPONSE = "Hello, World!\n";

    @Override
    public SentinelResponse handleRequest(SentinelRequest originalRequest)
    {
        Request request = (Request) originalRequest;
        return new SentinelResponse(RESPONSE);
    }
    
}
