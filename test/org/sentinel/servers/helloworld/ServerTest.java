package org.sentinel.servers.helloworld;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.server.ListenerTest;

public class ServerTest extends org.sentinel.test.cases.ServerCase
{

    public ServerTest()
    {
        super(org.sentinel.servers.helloworld.Client.class, ListenerTest.DEFAULT_HELLOWORLD_PORT);
    }

    /**
     * Test the most simple server to see if we get the correct response back.
     */
    @Test
    public void testResponse() throws Exception
    {
        String response = getClient().sendRawRequest("\n");
        assertEquals("Hello, World!\n", response);
    }
    
}
