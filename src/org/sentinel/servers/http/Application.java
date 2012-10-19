package org.sentinel.servers.http;

import java.io.IOException;
import org.sentinel.servers.http.protocol.HTTPRequest;
import org.sentinel.servers.http.protocol.HTTPResponse;

public interface Application
{
    
    void handleRequest(HTTPRequest request, HTTPResponse response)
        throws IOException;
    
}
