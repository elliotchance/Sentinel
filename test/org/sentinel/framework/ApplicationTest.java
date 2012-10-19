package org.sentinel.framework;

import java.net.MalformedURLException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.servers.http.protocol.HTTPRequest;

public class ApplicationTest
{

    public class ApplicationImpl extends Application
    {
    }

    @Test
    public void testGetParam() throws MalformedURLException
    {
        ApplicationImpl impl = new ApplicationImpl();
        impl.request = new HTTPRequest();
        assertNull(impl.getParam("test", null));
        
        URL url = new URL("http://localhost/something?test=1&test2=abc");
        impl.request.setRequestURL(url);
        assertEquals("1", impl.getParam("test"));
        assertEquals("abc", impl.getParam("test2"));
    }

}
