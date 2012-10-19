package org.sentinel.client;

import java.net.Socket;
import static org.junit.Assert.*;
import org.junit.Test;

public class ClientTest
{
    
    @Test
    public void testInstantiation()
    {
        Client client = new org.sentinel.servers.helloworld.Client();
        assertEquals("localhost", client.getHost());
        assertEquals(0, client.getPort());
    }

    @Test
    public void testHost()
    {
        Client client = new org.sentinel.servers.helloworld.Client();
        client.setHost("somehost");
        assertEquals("somehost", client.getHost());
    }

    @Test
    public void testPort()
    {
        Client client = new org.sentinel.servers.helloworld.Client();
        client.setPort(1234);
        assertEquals(1234, client.getPort());
    }

    @Test
    public void testSocket()
    {
        Socket socket = new Socket();
        Client client = new org.sentinel.servers.helloworld.Client();
        client.setSocket(socket);
        assertEquals(socket, client.getSocket());
    }

}
