package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.HTTPHeaders;
import org.sentinel.servers.http.protocol.HTTPHeader;

public class HTTPResponseHeaders extends HTTPHeaders
{
    
    protected String version;
    
    protected int status;

    public HTTPResponseHeaders()
    {
        this("HTTP/1.1", 200);
    }

    public HTTPResponseHeaders(String firstLine)
    {
        int pos = firstLine.indexOf(" ");
        this.version = firstLine.substring(0, pos);
        
        String statusCode = firstLine.substring(pos + 1);
        if(statusCode.indexOf(' ') >= 0) {
            statusCode = statusCode.substring(0, statusCode.indexOf(' '));
        }
        this.status = Integer.parseInt(statusCode);
    }

    public HTTPResponseHeaders(String version, int status)
    {
        this.version = version;
        this.status = status;
    }

    @Override
    public String toString()
    {
        StringBuilder r = new StringBuilder();
        
        r.append(version);
        r.append(" ");
        r.append(status);
        
        try {
            HTTPStatusCode code = HTTPStatusCode.valueOf(status);
            r.append(" ");
            r.append(code.getStatusMessage());
        }
        catch(NoSuchHTTPStatusCodeException ex) {
            // never mind, we dont "need" it
        }
        
        r.append("\n");
        
        for(HTTPHeader header : this) {
            r.append(header.toString());
            r.append("\n");
        }
        
        return r.toString();
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public static HTTPResponseHeaders parse(String rawHeader)
    {
        String[] lines = rawHeader.split("\n");
        HTTPResponseHeaders headers = new HTTPResponseHeaders(lines[0]);
        
        for(int i = 1; i < lines.length; ++i) {
            int pos = lines[i].indexOf(":");
            HTTPHeader header = new HTTPHeader(lines[i].substring(0, pos),
                lines[i].substring(pos + 1).trim());
            headers.add(header);
        }
        
        return headers;
    }
    
}
