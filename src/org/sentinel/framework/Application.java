package org.sentinel.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.sentinel.servers.http.protocol.HTTPRequest;
import org.sentinel.servers.http.protocol.HTTPResponse;

public abstract class Application implements org.sentinel.servers.http.Application
{
    
    protected HTTPRequest request = null;
    
    @Override
    public void handleRequest(HTTPRequest request, HTTPResponse response)
        throws IOException
    {
        this.request = (HTTPRequest) request;
    }
    
    public String getParam(String name)
    {
        return getParam(name, null);
    }
    
    public String getParam(String name, String defaultValue)
    {
        if(request.getRequestURL() == null) {
            return defaultValue;
        }
        return request.getRequestURL().getQueryParameter(name, defaultValue);
    }
    
    public String getTemplate(String name) throws IOException
    {
        String packageName = getClass().getPackage().getName();
        String resourcePath = "/" + packageName.replace('.', '/') + "/" + name;
        java.net.URL resourceURL = getClass().getResource(resourcePath);
        return getResource(resourceURL);
    }
    
    public static String getResource(java.net.URL filePath) throws IOException
    {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath.getFile()));
        char[] buf = new char[1024];
        int numRead;
        while((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

}
