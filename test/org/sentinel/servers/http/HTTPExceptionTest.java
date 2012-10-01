package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;
import org.sentinel.server.ServerException;

public class HTTPExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new HTTPException("Some message");
        }
        catch(HTTPException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof ServerException);
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
