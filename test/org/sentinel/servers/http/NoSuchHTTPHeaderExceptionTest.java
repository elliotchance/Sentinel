package org.sentinel.servers.http;

import org.junit.Test;
import static org.junit.Assert.*;
import org.sentinel.SentinelException;
import org.sentinel.server.ServerException;

public class NoSuchHTTPHeaderExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new NoSuchHTTPHeaderException("Some message");
        }
        catch(NoSuchHTTPHeaderException ex) {
            assertEquals("No such HTTP header Some message", ex.getMessage());
            assertTrue(ex instanceof HTTPException);
            assertTrue(ex instanceof ServerException);
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
