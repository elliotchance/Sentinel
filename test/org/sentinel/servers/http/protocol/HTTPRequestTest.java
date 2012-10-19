package org.sentinel.servers.http.protocol;

import java.net.MalformedURLException;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPRequestTest
{
    
    @Test
    public void testRequestURL() throws MalformedURLException
    {
        org.sentinel.framework.URL url = new org.sentinel.framework.URL("http://google.com");
        HTTPRequest instance = new HTTPRequest();
        instance.setRequestURL(url);
        assertEquals(url, instance.getRequestURL());
    }

}
