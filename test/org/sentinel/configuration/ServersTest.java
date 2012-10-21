package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServersTest extends org.sentinel.test.cases.ConfigurationParserCase
{

    @Test
    public void testToString()
    {
        Servers servers = new Servers();
        assertEquals("<servers></servers>", servers.toString());
    }

    @Test
    public void testToString2()
    {
        Servers servers = new Servers();
        Server server = new Server("abc", Integer.class, Float.class);
        servers.put(server.getName(), server);
        assertEquals("<servers>" + server.toString() + "</servers>", servers.toString());
    }

    @Test
    public void testParseRoot()
    {
        assertBadChildNode(new Servers(), "<servers><bad/></servers>");
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Servers().parseAttribute(null, null));
    }

}
