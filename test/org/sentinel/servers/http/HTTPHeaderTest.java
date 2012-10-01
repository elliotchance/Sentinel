package org.sentinel.servers.http;

import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPHeaderTest
{

    @Test
    public void testInstantiation1()
    {
        HTTPHeader header = new HTTPHeader("abc", "def");
        assertEquals("abc", header.getName());
        assertEquals("def", header.getValue());
    }

    @Test
    public void testInstantiation2()
    {
        HTTPHeader header = new HTTPHeader("abc: def");
        assertEquals("abc", header.getName());
        assertEquals("def", header.getValue());
    }

    @Test
    public void testNameValue()
    {
        HTTPHeader header = new HTTPHeader("", "");
        header.setName("123");
        assertEquals("123", header.getName());
    }

    @Test
    public void testValue()
    {
        HTTPHeader header = new HTTPHeader("", "");
        header.setValue("456");
        assertEquals("456", header.getValue());
    }

    @Test
    public void testToString()
    {
        HTTPHeader header = new HTTPHeader("abc", "def");
        assertEquals("abc: def", header.toString());
    }
    
}
