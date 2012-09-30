package org.sentinel.servers.helloworld;

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.Client;

public class ServerTest extends org.sentinel.test.cases.Server
{

    public ServerTest() throws Exception
    {
        super(4040);
    }

    /**
     * Test the most simple server to see if we get the correct response back.
     */
    @Test
    public void testResponse() throws IOException
    {
        Client client = getClient();
        String response = client.sendRawRequest("");
        assertEquals(Server.RESPONSE, response);
    }
}
