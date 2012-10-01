package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;

public class ResponseTest
{

    @Test
    public void testBody()
    {
        byte[] b = "abc".getBytes();
        Response response = new Response();
        response.setBody(b);
        assertEquals("abc", new String(response.getBody()));
    }

    @Test
    public void testHTTPHeaders()
    {
        HTTPResponseHeaders headers = new HTTPResponseHeaders();
        Response response = new Response();
        response.setHTTPHeaders(headers);
        assertEquals(headers, response.getHTTPHeaders());
    }
    
    @Test
    public void testParse()
    {
        Response.parse("HTTP/1.1 200 OK\nContent-Length: 3\n\nabc");
    }

}
