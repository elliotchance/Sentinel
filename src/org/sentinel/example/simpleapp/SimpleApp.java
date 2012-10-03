package org.sentinel.example.simpleapp;

import org.sentinel.server.Application;
import org.sentinel.server.SentinelRequest;
import org.sentinel.server.SentinelResponse;
import org.sentinel.servers.http.Request;

public class SimpleApp implements Application
{

    @Override
    public void handleRequest(SentinelRequest request, SentinelResponse response)
    {
        // first cast the request into something we can use
        Request req = (Request) request;
        
        // in this simple app we get the name of the person and send back a greeting
        String name = req.getParam("name", "nobody");
        String template = "<html><head><title>SimpleApp</title><head><body>%name%</body></html>";
        
        response.write(template);
    }
    
}
