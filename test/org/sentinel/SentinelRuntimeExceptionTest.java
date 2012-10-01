package org.sentinel;

import static org.junit.Assert.*;
import org.junit.Test;

public class SentinelRuntimeExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new SentinelRuntimeException("Some message");
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof RuntimeException);
        }
    }
    
}
