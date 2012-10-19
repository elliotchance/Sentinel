package org.sentinel.servers.http.protocol;

import java.util.HashMap;
import java.util.Map;
import org.sentinel.framework.URL;

public class HTTPRequest
{
    
    protected org.sentinel.framework.URL requestURL = null;
    
    protected HTTPRequestHeaders httpHeaders;
    
    protected byte[] body;
    
    protected Map<String, String> queryParameters = new HashMap<String, String>();

    public HTTPRequestHeaders getHTTPHeaders()
    {
        return httpHeaders;
    }

    public void setHTTPHeaders(HTTPRequestHeaders httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }

    public URL getRequestURL()
    {
        return requestURL;
    }

    public void setRequestURL(URL requestURL)
    {
        this.requestURL = requestURL;
    }
    
}
