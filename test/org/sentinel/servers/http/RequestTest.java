package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestTest
{

    @Test
    public void testHTTPHeaders()
    {
        Request request = new Request();
        HTTPRequestHeaders headers = new HTTPRequestHeaders();
        request.setHTTPHeaders(headers);
        assertEquals(headers, request.getHTTPHeaders());
    }

    @Test
    public void testBody()
    {
        Request request = new Request();
        byte[] body = "abc".getBytes();
        request.setBody(body);
        assertEquals(body, request.getBody());
    }
    
    @Test
    public void testParamMissing()
    {
        Request request = new Request();
        assertNull(request.getParam("doesnt_exist"));
        assertEquals("missing", request.getParam("doesnt_exist", "missing"));
    }

}
