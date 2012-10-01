package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServerTest
{

    @Test
    public void testName()
    {
        String string = String.valueOf(Math.random());
        Server instance = new Server();
        instance.setName(string);
        assertEquals(string, instance.getName());
    }

    @Test
    public void testProtocol()
    {
        Class klass = this.getClass();
        Server instance = new Server();
        instance.setProtocol(klass);
        assertEquals(klass, instance.getProtocol());
    }

    @Test
    public void testServer()
    {
        Class klass = this.getClass();
        Server instance = new Server();
        instance.setServer(klass);
        assertEquals(klass, instance.getServer());
    }
    
}
