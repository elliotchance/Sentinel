package org.sentinel.servers.http.protocol;

import java.util.LinkedList;

public class HTTPResponse
{

    protected LinkedList<byte[]> body = new LinkedList<byte[]>();
    
    protected HTTPResponseHeaders httpHeaders = null;
    
    protected int contentLength = 0;
    
    public HTTPResponse()
    {
        httpHeaders = new HTTPResponseHeaders();
    }

    public String getRawResponse()
    {
        // set the content-length
        httpHeaders.addOrReplace(new HTTPHeader("Content-Length", contentLength));
        
        // build full data
        byte[] full = new byte[contentLength];
        int offset = 0;
        for(byte[] b : body) {
            for(int i = 0; i < b.length; ++i, ++offset) {
                full[offset] = b[i];
            }
        }
        
        return httpHeaders + "\n" + new String(full);
    }

    public void setBody(byte[] body)
    {
        this.body = new LinkedList<byte[]>();
        this.body.add(body);
        contentLength = body.length;
    }

    public HTTPResponseHeaders getHTTPHeaders()
    {
        return httpHeaders;
    }

    public void setHTTPHeaders(HTTPResponseHeaders httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }
    
    public static HTTPResponse parse(String raw)
    {
        HTTPResponse response = new HTTPResponse();
        
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

    public void write(String data)
    {
        write(data.getBytes());
    }
    
    public void write(byte[] data)
    {
        write(data, data.length);
    }
    
    public void write(byte[] data, int len)
    {
        contentLength += len;
        
        if(data.length != len) {
            body.add(new String(data, 0, len).getBytes());
        }
        else {
            body.add(data);
        }
    }
    
}
