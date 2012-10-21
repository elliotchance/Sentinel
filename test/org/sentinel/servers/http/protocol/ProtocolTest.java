package org.sentinel.servers.http.protocol;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;
import org.sentinel.servers.http.configuration.Application;

public class ProtocolTest
{

    @Test
    public void testGetURLFromPathInvalid() throws Exception
    {
        String original = Protocol.BASE_URL;
        
        try {
            Protocol.BASE_URL = "!";
            System.out.println(Protocol.getURLFromPath("a"));
            fail("Should have failed.");
        }
        catch(SentinelException ex) {
            assertTrue(ex.getMessage().contains("no protocol"));
        }
        finally {
            Protocol.BASE_URL = original;
        }
    }
    
    @Test
    public void testInvalidRunApplication()
    {
        try {
            Application app = new Application();
            app.setApplication(Integer.class);

            Protocol protocol = new Protocol();
            protocol.runApplication(null, app, null);
        }
        catch(SentinelException ex) {
            assertTrue(ex.getMessage().contains("Integer"));
        }
    }

}
