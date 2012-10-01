package org.sentinel.servers.http;

import org.sentinel.server.SentinelRequest;
import org.sentinel.server.SentinelResponse;
import org.sentinel.server.SentinelServer;

public class Server implements SentinelServer
{

    @Override
    public SentinelResponse handleRequest(SentinelRequest originalRequest)
    {
        Request request = (Request) originalRequest;
        Response response = new Response("It works!");
        return response;
    }
    
}
