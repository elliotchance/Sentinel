package org.sentinel.servers.http.protocol;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestTest
{

    @Test
    public void testHTTPHeaders()
    {
        HTTPRequest request = new HTTPRequest();
        HTTPRequestHeaders headers = new HTTPRequestHeaders();
        request.setHTTPHeaders(headers);
        assertEquals(headers, request.getHTTPHeaders());
    }

    @Test
    public void testBody()
    {
        HTTPRequest request = new HTTPRequest();
        byte[] body = "abc".getBytes();
        request.setBody(body);
        assertEquals(body, request.getBody());
    }

}
