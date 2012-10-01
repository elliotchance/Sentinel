package org.sentinel;

import static org.junit.Assert.*;
import org.junit.Test;

public class SentinelExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new SentinelException("Some message");
        }
        catch(SentinelException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof Exception);
        }
    }
    
}
