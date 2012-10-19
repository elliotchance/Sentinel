package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.NoSuchHTTPHeaderException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sentinel.SentinelException;
import org.sentinel.server.ServerException;
import org.sentinel.servers.http.HTTPException;

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
