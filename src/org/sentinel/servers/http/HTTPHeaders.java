package org.sentinel.servers.http;

import java.util.ArrayList;

public class HTTPHeaders extends ArrayList<HTTPHeader>
{
    
    public HTTPHeader get(String name) throws NoSuchHTTPHeaderException
    {
        HTTPHeader found = null;
        
        for(HTTPHeader h : this) {
            if(h.getName().equalsIgnoreCase(name)) {
                found = h;
            }
        }
        
        if(found != null) {
            return found;
        }
        throw new NoSuchHTTPHeaderException(name);
    }
    
    public void addOrReplace(HTTPHeader header)
    {
        boolean found = false;
        for(HTTPHeader h : this) {
            if(h.getName().equalsIgnoreCase(header.getName())) {
                h.setValue(header.getValue());
                found = true;
            }
        }
        
        if(!found) {
            add(header);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for(HTTPHeader header : this) {
            sb.append(header.toString());
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
}
