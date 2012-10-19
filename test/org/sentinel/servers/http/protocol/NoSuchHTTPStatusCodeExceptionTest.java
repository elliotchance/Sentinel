package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.NoSuchHTTPStatusCodeException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;
import org.sentinel.server.ServerException;
import org.sentinel.servers.http.HTTPException;

public class NoSuchHTTPStatusCodeExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new NoSuchHTTPStatusCodeException("999");
        }
        catch(NoSuchHTTPStatusCodeException ex) {
            assertEquals("No such HTTP status code 999", ex.getMessage());
            assertTrue(ex instanceof HTTPException);
            assertTrue(ex instanceof ServerException);
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}