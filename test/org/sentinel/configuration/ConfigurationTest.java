package org.sentinel.configuration;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigurationTest
{

    @Test
    public void testGetListeners()
    {
        Configuration configuration = new Configuration();
        assertEquals(0, configuration.getListeners().size());
        
        configuration.addListener(new Listener(1234, "bla"));
        assertEquals(1, configuration.getListeners().size());
        assertEquals(1234, configuration.getListeners().get(0).getPort());
        assertEquals("bla", configuration.getListeners().get(0).getServer());
    }

    @Test
    public void testGetServers()
    {
        Configuration configuration = new Configuration();
        assertEquals(0, configuration.getServers().size());
        
        configuration.addServer(new Server());
        assertEquals(1, configuration.getServers().size());
    }
    
}
