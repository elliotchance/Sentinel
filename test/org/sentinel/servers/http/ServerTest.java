package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.server.SentinelServer;

public class ServerTest extends org.sentinel.test.cases.ServerCase
{

    public ServerTest()
    {
        super(org.sentinel.servers.http.Client.class, SentinelServer.DEFAULT_HTTP_PORT);
    }

    @Test
    public void testResponse() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        Response response = client.sendRequest("something");
        
        assertEquals(200, response.getHTTPHeaders().getStatus());
        assertEquals("It works!", new String(response.getBody()));
    }

    @Test
    public void testNoContentLength() throws Exception
    {
        org.sentinel.servers.http.Client client = (org.sentinel.servers.http.Client) getClient();
        String response = client.sendRawRequest("GET / HTTP/1.1\n\n");
        
        assertEquals("HTTP/1.1 200 OK", response.split("\n")[0]);
    }

}
