package org.sentinel.servers.helloworld;

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.server.SentinelServer;

public class ServerTest extends org.sentinel.test.cases.ServerCase
{

    public ServerTest()
    {
        super(org.sentinel.servers.helloworld.Client.class, SentinelServer.DEFAULT_HELLOWORLD_PORT);
    }

    /**
     * Test the most simple server to see if we get the correct response back.
     */
    @Test
    public void testResponse() throws Exception
    {
        String response = getClient().sendRawRequest("");
        assertEquals("Hello, World!\n", response);
    }

    @Test
    public void testPrematureExit() throws IOException
    {
        getClient().close();
    }
    
}
