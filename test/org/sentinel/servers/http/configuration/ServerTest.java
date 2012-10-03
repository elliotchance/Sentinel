package org.sentinel.servers.http.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;

public class ServerTest extends ConfigurationParserCase
{

    @Test
    public void testParseRoot()
    {
        assertBadChildNode(new Server(), "<server><bad/></server>");
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
            "<server configuration=\"java.lang.Integer\" name=\"abc\" protocol=\"java.lang.Float\" "
            + "server=\"java.lang.Double\"/>");
    }

    @Test
    public void testToStringApplications()
    {
        Server server = new Server();
        server.setConfiguration(java.lang.Integer.class);
        server.setProtocol(java.lang.Float.class);
        server.setServer(java.lang.Double.class);
        server.setName("abc");
        
        Application application = new Application("app", java.lang.Integer.class, "/");
        server.applications = new Applications();
        server.applications.add(application);
        
        assertEquals(server.toString(),
            "<server configuration=\"java.lang.Integer\" name=\"abc\" protocol=\"java.lang.Float\" "
            + "server=\"java.lang.Double\"><applications>" +
            application.toString() + "</applications></server>");
    }

}
