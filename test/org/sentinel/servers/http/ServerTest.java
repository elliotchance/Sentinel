package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.server.ListenerTest;
import org.sentinel.servers.http.protocol.HTTPResponse;

public class ServerTest extends org.sentinel.test.cases.ServerCase
{

    public ServerTest()
    {
        super(org.sentinel.servers.http.Client.class, ListenerTest.DEFAULT_HTTP_PORT);
    }

    @Test
    public void test404Response() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        HTTPResponse response = client.sendRequest("/", "");
        
        assertEquals(404, response.getHTTPHeaders().getStatus());
        assertTrue(response.getRawResponse().contains("Not Found"));
    }

    @Test
    public void testSimpleAppResponse() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        HTTPResponse response = client.sendRequest("/simpleapp?a=1", "");
        
        assertEquals(200, response.getHTTPHeaders().getStatus());
        assertTrue(response.getRawResponse().contains("SimpleApp"));
    }

    @Test
    public void testNoContentLength() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        String response = client.sendRawRequest("GET / HTTP/1.1\n\n");
        
        assertEquals("HTTP/1.1 404 Not Found", response.split("\n")[0]);
    }
    
    @Test
    public void testFetchStatic() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        HTTPResponse response = client.sendRequest("/simpleapp/socket.io.js");
        
        assertEquals(100943, (int) Integer.valueOf(response.getHTTPHeaders().get("content-length").getValue()));
    }

}
