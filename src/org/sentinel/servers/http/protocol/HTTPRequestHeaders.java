package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.configuration.Application;

public class HTTPRequestHeaders extends HTTPHeaders
{
    
    protected String method;
    
    protected String path;
    
    protected String version;
    
    public HTTPRequestHeaders()
    {
        setInitialHeader("GET", "/", "HTTP/1.1");
    }

    public HTTPRequestHeaders(String initialHeader)
    {
        String[] parts = initialHeader.split(" ");
        setInitialHeader(parts[0], parts[1], parts[2]);
    }

    public final void setInitialHeader(String method, String path, String version)
    {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getPath()
    {
        return path;
    }
    
    /**
     * Get the relative path to the root application path.
     * @param app Application path to reference.
     * @return Path relative to the application.
     */
    public String getPath(Application app)
    {
        return path.substring(app.getPrefix().length());
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return method + " " + path + " " + version + "\n" + super.toString() + "\n";
    }
    
    public static HTTPRequestHeaders parse(String rawHeader)
    {
        String[] lines = rawHeader.split("\n");
        HTTPRequestHeaders headers = new HTTPRequestHeaders(lines[0]);
        
        for(int i = 1; i < lines.length; ++i) {
            int pos = lines[i].indexOf(":");
            HTTPHeader header = new HTTPHeader(lines[i].substring(0, pos),
                lines[i].substring(pos + 1).trim());
            headers.add(header);
        }
        
        return headers;
    }
    
}
