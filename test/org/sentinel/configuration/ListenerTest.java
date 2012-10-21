package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;

public class ListenerTest extends ConfigurationParserCase
{

    @Test
    public void testAttributePort()
    {
        assertConfigurationException(
            new Listener(),
            "<listener/>",
            "Attribute 'port' is required for <listener>"
        );
    }

    @Test
    public void testAttributeServer()
    {
        assertConfigurationException(
            new Listener(),
            "<listener port=\"4040\"/>",
            "Attribute 'server' is required for <listener>"
        );
    }

    @Test
    public void testToString()
    {
        assertEquals(new Listener(1234, "serverName").toString(),
            "<listener port=\"1234\" server=\"serverName\"/>");
    }

    @Test
    public void testParseTextElement() throws ConfigurationException
    {
        new Listener().parseTextElement(null);
    }

    @Test
    public void testParseElement() throws ConfigurationException
    {
        assertFalse(new Listener().parseElement(null));
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Listener().parseAttribute("doesntexist", null));
    }

}
