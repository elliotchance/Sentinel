package org.sentinel.servers.http.protocol;

import static org.junit.Assert.*;
import org.junit.Test;

public class ResponseTest
{

    @Test
    public void testBody()
    {
        byte[] b = "abc".getBytes();
        HTTPResponse response = new HTTPResponse();
        response.setBody(b);
        assertTrue(response.getRawResponse().endsWith("abc"));
    }

    @Test
    public void testHTTPHeaders()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders();
        HTTPResponse response = new HTTPResponse();
        response.setHTTPHeaders(headers);
        assertEquals(headers, response.getHTTPHeaders());
    }
    
    @Test
    public void testParse()
    {
        HTTPResponse.parse("HTTP/1.1 200 OK\nContent-Length: 3\n\nabc");
    }
    
    @Test
    public void testWrite()
    {
        HTTPResponse response = new HTTPResponse();
        response.write("something");
        response.write("else");
        assertTrue(response.getRawResponse().endsWith("somethingelse"));
    }

}
