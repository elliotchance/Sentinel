package org.sentinel.servers.http.protocol;

public class HTTPResponse
{

    protected byte[] body = null;
    
    protected HTTPResponseHeaders httpHeaders = null;
    
    public HTTPResponse()
    {
        body = new byte[0];
        httpHeaders = new HTTPResponseHeaders();
    }

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
        body = (new String(body) + data).getBytes();
    }
    
}
