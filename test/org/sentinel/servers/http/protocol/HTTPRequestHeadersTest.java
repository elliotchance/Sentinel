package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.HTTPRequestHeaders;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPRequestHeadersTest
{
    
    public static final String validInitialHeader = "GET /some/path HTTP/1.1";

    @Test
    public void testValidInitialHeader()
    {
        HTTPRequestHeaders headers = new HTTPRequestHeaders(validInitialHeader);
        assertEquals("GET", headers.getMethod());
        assertEquals("/some/path", headers.getPath());
        assertEquals("HTTP/1.1", headers.getVersion());
    }
    
    @Test
    public void testMethod()
    {
        HTTPRequestHeaders headers = new HTTPRequestHeaders(validInitialHeader);
        headers.setMethod("POST");
        assertEquals("POST", headers.getMethod());
    }
    
    @Test
    public void testPath()
    {
        HTTPRequestHeaders headers = new HTTPRequestHeaders(validInitialHeader);
        headers.setPath("/some/other/path");
        assertEquals("/some/other/path", headers.getPath());
    }
    
    @Test
    public void testVersion()
    {
        HTTPRequestHeaders headers = new HTTPRequestHeaders(validInitialHeader);
        headers.setVersion("HTTP/1.0");
        assertEquals("HTTP/1.0", headers.getVersion());
    }
    
}
