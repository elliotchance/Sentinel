package org.sentinel.example.simpleapp;

import java.io.IOException;
import org.sentinel.framework.Application;
import org.sentinel.servers.http.protocol.HTTPRequest;
import org.sentinel.servers.http.protocol.HTTPResponse;

public class SimpleApp extends Application
{

    @Override
    public void handleRequest(HTTPRequest request, HTTPResponse response)
        throws IOException
    {
        // initialise the application and framework
        super.handleRequest(request, response);
        
        // get the persons name
        String name = getParam("name");
        
        // if there is no name then we need to ask for it
        if(name == null) {
            response.write(getTemplate("index.html"));
            return;
        }
        
        // if we have a name then greet the person
        String output = getTemplate("welcome.html").replaceFirst("%name%", name);
        response.write(output);
    }
    
}
