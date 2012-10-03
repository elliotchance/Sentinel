package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConfigurationTest
{

    @Test
    public void testListeners()
    {
        Sentinel configuration = Sentinel.debugInstance();
        assertEquals(0, configuration.getListeners().size());
        
        configuration.addListener(new Listener(1234, "bla"));
        assertEquals(1, configuration.getListeners().size());
        assertEquals(1234, configuration.getListeners().get(1234).getPort());
        assertEquals("bla", configuration.getListeners().get(1234).getServer());
    }

    @Test
    public void testServers()
    {
        Sentinel configuration = Sentinel.debugInstance();
        assertEquals(0, configuration.getServers().size());
        
        configuration.addServer(new Server());
        assertEquals(1, configuration.getServers().size());
    }
    
}
