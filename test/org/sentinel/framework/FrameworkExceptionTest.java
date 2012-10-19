package org.sentinel.framework;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;

public class FrameworkExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new FrameworkException("Some message");
        }
        catch(FrameworkException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
