package org.sentinel.client;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.SentinelException;

public class ClientExceptionTest
{

    @Test
    public void testInstantiation()
    {
        try {
            throw new ClientException("Some message");
        }
        catch(ClientException ex) {
            assertEquals("Some message", ex.getMessage());
            assertTrue(ex instanceof SentinelException);
            assertTrue(ex instanceof Exception);
        }
    }
    
}
