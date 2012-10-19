package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.HTTPResponseHeaders;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPResponseHeadersTest
{

    @Test
    public void testToString()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders("HTTP/1.1 200");
        assertEquals("HTTP/1.1 200 OK\n", headers.toString());
    }

    @Test
    public void testToStringWithBadStatusCode()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders("HTTP/1.1 999");
        assertEquals("HTTP/1.1 999\n", headers.toString());
    }
    
    @Test
    public void testVersion()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders();
        headers.setVersion("HTTP/1.1");
        assertEquals("HTTP/1.1", headers.getVersion());
    }
    
    @Test
    public void testStatus()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders();
        headers.setStatus(304);
        assertEquals(304, headers.getStatus());
    }

}
