package org.sentinel.servers.http.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.configuration.ConfigurationException;

public class StaticTest
{

    @Test
    public void testPath()
    {
        String string = "abc";
        Static instance = new Static();
        instance.setPath(string);
        assertEquals(string, instance.getPath());
    }
    
    @Test
    public void testToString()
    {
        Static s = new Static("/some/path");
        assertEquals("<static path=\"/some/path\"/>", s.toString());
    }

    @Test
    public void testTextElement() throws ConfigurationException
    {
        new Static().parseTextElement(null);
    }

    @Test
    public void testParseElement() throws ConfigurationException
    {
        assertFalse(new Static().parseElement(null));
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Static().parseAttribute("doesntexist", null));
    }

}
