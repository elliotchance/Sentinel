package org.sentinel.servers.http.protocol;

import org.sentinel.servers.http.protocol.HTTPStatusCode;
import org.sentinel.servers.http.protocol.NoSuchHTTPStatusCodeException;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPStatusCodeTest
{

    @Test
    public void testToString()
    {
        assertEquals("200 OK", HTTPStatusCode.HTTP_200.toString());
    }

    @Test
    public void testBadValueOf()
    {
        try {
            HTTPStatusCode.valueOf(999);
            fail("Did not throw exception.");
        }
        catch(NoSuchHTTPStatusCodeException ex) {
            assertEquals("No such HTTP status code 999", ex.getMessage());
        }
    }

    @Test
    public void testValueOf()
    {
        HTTPStatusCode.valueOf("HTTP_200");
    }

}
