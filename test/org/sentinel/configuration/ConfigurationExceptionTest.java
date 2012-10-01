package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;

public class ConfigurationExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new ConfigurationException("Some message");
        }
        catch(ConfigurationException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
