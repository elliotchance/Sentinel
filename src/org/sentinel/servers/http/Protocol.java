package org.sentinel.servers.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.sentinel.server.SentinelProtocol;
import org.sentinel.server.SentinelRequest;

public class Protocol implements SentinelProtocol
{

    @Override
    public SentinelRequest handleRawRequest(final Socket socket) throws IOException
    {
        Request request = new Request();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine, headerLines = "";
        
        // read HTTP headers
        while((inputLine = in.readLine()) != null) {
            // if the line is blank this is the end of the headers
            if(inputLine.equals("")) {
                break;
            }
            
            headerLines += inputLine + "\n";
        }
        
        // parse HTTP headers
        HTTPRequestHeaders headers = HTTPRequestHeaders.parse(headerLines);
        int contentLength = 0;
        try {
            contentLength = Integer.parseInt(headers.get("content-length").getValue());
        }
        catch(NoSuchHTTPHeaderException ex) {
            // do nothing
        }
        request.setHTTPHeaders(headers);
        
        // read body
        char[] body = new char[contentLength];
        in.read(body);
        request.setBody(new String(body).getBytes());
        
        System.out.println(request);
        return request;
    }
    
}
