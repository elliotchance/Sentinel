package org.sentinel.servers.http;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPHeadersTest
{
    
    public HTTPHeadersTest()
    {
    }

    @Test
    public void testGet()
    {
        try {
            HTTPHeaders headers = new HTTPHeaders();
            headers.add(new HTTPHeader("key", "value"));
            assertEquals("value", headers.get("key").getValue());
        }
        catch(NoSuchHTTPHeaderException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetNonExistantHeader()
    {
        try {
            HTTPHeaders headers = new HTTPHeaders();
            assertEquals("value", headers.get("key").getValue());
            fail("Should of failed before this point.");
        }
        catch(NoSuchHTTPHeaderException ex) {
            // good
        }
    }

    @Test
    public void testAddOrReplace()
    {
        try {
            HTTPHeaders headers = new HTTPHeaders();
            headers.addOrReplace(new HTTPHeader("key", "value"));
            assertEquals("value", headers.get("key").getValue());
            
            headers.addOrReplace(new HTTPHeader("key", "value2"));
            assertEquals("value2", headers.get("key").getValue());
        }
        catch(NoSuchHTTPHeaderException ex) {
            fail(ex.getMessage());
        }
    }
    
}
