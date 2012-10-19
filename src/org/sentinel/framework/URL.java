package org.sentinel.framework;

import java.net.MalformedURLException;
import java.util.HashMap;

public class URL
{
    
    private java.net.URL url = null;
    
    private HashMap<String, String> queryParameters = null;

    /**
     * Construct a new URL object.
     * 
     * @param rawURL String representation of a URL.
     * @throws MalformedURLException if the rawURL is malformed.
     */
    public URL(String rawURL) throws MalformedURLException
    {
        url = new java.net.URL(rawURL);
    }
    
    /**
     * Construct a framework URL from a Java URL object.
     * 
     * @param url Base URL.
     */
    public URL(java.net.URL url)
    {
        this.url = url;
    }
    
    public HashMap<String, String> getQueryParameters()
    {
        if(queryParameters != null) {
            return queryParameters;
        }
        
        // dissect query parameters
        queryParameters = new HashMap<String, String>();
        String query = url.getQuery();
        
        if(query == null || query.equals("")) {
            return queryParameters;
        }
        
        String[] params = query.split("&");
        for(String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            queryParameters.put(name, value);
        }
        
        return queryParameters;
    }
    
    public String getQueryParameter(String name)
    {
        return getQueryParameter(name, null);
    }
    
    public String getQueryParameter(String name, String defaultValue)
    {
        // make sure the parameters are loaded
        getQueryParameters();
        
        if(queryParameters.get(name) == null) {
            return defaultValue;
        }
        
        return queryParameters.get(name);
    }
    
}
