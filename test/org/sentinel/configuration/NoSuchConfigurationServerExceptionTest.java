package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;

public class NoSuchConfigurationServerExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new NoSuchConfigurationServerException("Server Name");
        }
        catch(NoSuchConfigurationServerException ex) {
            assertEquals("No such configuration server 'Server Name'", ex.getMessage());
            assertTrue(ex instanceof ConfigurationException);
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
