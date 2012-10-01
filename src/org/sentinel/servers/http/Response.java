package org.sentinel.servers.http;

import org.sentinel.server.SentinelResponse;

public class Response implements SentinelResponse
{

    protected byte[] body = new byte[0];
    
    protected HTTPResponseHeaders httpHeaders = new HTTPResponseHeaders();
    
    public Response()
    {
    }

    public Response(final String body)
    {
        this.body = body.getBytes();
    }

    @Override
    public String getRawResponse()
    {
        // set the content-length
        String length = String.valueOf(body.length);
        httpHeaders.addOrReplace(new HTTPHeader("Content-Length", length));
        
        return httpHeaders + "\n" + new String(body);
    }

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }

    public HTTPResponseHeaders getHTTPHeaders()
    {
        return httpHeaders;
    }

    public void setHTTPHeaders(HTTPResponseHeaders httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }
    
    public static Response parse(String raw)
    {
        Response response = new Response();
        
        // read HTTP headers
        String[] lines = raw.split("\n");
        int offset = 0;
        for(String line : lines) {
            // if the line is blank this is the end of the headers
            if(line.equals("")) {
                response.setHTTPHeaders(HTTPResponseHeaders.parse(raw.substring(0, offset)));
                break;
            }
            offset += line.length() + 1;
        }
        
        // body
        response.setBody(raw.substring(offset + 1).getBytes());
        
        return response;
    }
    
}
