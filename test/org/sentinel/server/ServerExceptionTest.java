package org.sentinel.server;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;

public class ServerExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new ServerException("Some message");
        }
        catch(ServerException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
