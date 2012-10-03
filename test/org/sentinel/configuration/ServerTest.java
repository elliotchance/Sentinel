package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;

public class ServerTest extends ConfigurationParserCase
{

    @Test
    public void testInstantiation()
    {
        Server server = new Server("abc", java.lang.Integer.class, java.lang.Float.class, java.lang.Double.class);
        assertEquals("abc", server.getName());
        assertEquals(java.lang.Integer.class, server.getProtocol());
        assertEquals(java.lang.Float.class, server.getServer());
        assertEquals(java.lang.Double.class, server.getConfiguration());
    }

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
    public void testConfiguration()
    {
        Class klass = this.getClass();
        Server instance = new Server();
        instance.setConfiguration(klass);
        assertEquals(klass, instance.getConfiguration());
    }

    @Test
    public void testServer()
    {
        Class klass = this.getClass();
        Server instance = new Server();
        instance.setServer(klass);
        assertEquals(klass, instance.getServer());
    }

    @Test
    public void testAttributeName()
    {
        assertConfigurationException(
            new Server(),
            "<server/>",
            "You must specify the name for <server>"
        );
    }

    @Test
    public void testAttributeProtocol()
    {
        assertConfigurationException(
            new Server(),
            "<server name=\"a\"/>",
            "You must specify the protocol for <server>"
        );
    }

    @Test
    public void testAttributeServer()
    {
        assertConfigurationException(
            new Server(),
            "<server name=\"a\" protocol=\"java.lang.Integer\"/>",
            "You must specify the server for <server>"
        );
    }

    @Test
    public void testAttributeConfiguration()
    {
        assertConfigurationException(
            new Server(),
            "<server name=\"a\" protocol=\"java.lang.Integer\" server=\"java.lang.Integer\"/>",
            "You must specify the configuration for <server>"
        );
    }

    @Test
    public void testToString()
    {
        Server server = new Server();
        server.setConfiguration(java.lang.Integer.class);
        server.setProtocol(java.lang.Float.class);
        server.setServer(java.lang.Double.class);
        server.setName("abc");
        
        assertEquals(server.toString(),
            "<server configuration=\"java.lang.Integer\" name=\"abc\" protocol=\"java.lang.Float\" server=\"java.lang.Double\"/>");
    }
    
}
