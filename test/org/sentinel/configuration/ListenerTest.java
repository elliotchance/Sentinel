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
            "You must specify the port attribute for <listener>"
        );
    }

    @Test
    public void testAttributeServer()
    {
        assertConfigurationException(
            new Listener(),
            "<listener port=\"4040\"/>",
            "You must specify the server attribute for <listener>"
        );
    }

    @Test
    public void testToString()
    {
        assertEquals(new Listener(1234, "serverName").toString(),
            "<listener port=\"1234\" server=\"serverName\"/>");
    }

}
