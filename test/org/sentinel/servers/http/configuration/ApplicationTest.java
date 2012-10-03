package org.sentinel.servers.http.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.configuration.Listener;

public class ApplicationTest extends org.sentinel.test.cases.ConfigurationParserCase
{

    @Test
    public void testName()
    {
        String string = "abc";
        Application instance = new Application();
        instance.setName(string);
        assertEquals(string, instance.getName());
    }

    @Test
    public void testApplication()
    {
        Class klass = this.getClass();
        Application instance = new Application();
        instance.setApplication(klass);
        assertEquals(klass, instance.getApplication());
    }

    @Test
    public void testPrefix()
    {
        String string = "abc";
        Application instance = new Application();
        instance.setPrefix(string);
        assertEquals(string, instance.getPrefix());
    }

    @Test
    public void testBadApplicationClass()
    {
        assertConfigurationException(
            new Application(),
            "<application name=\"123\" application=\"abc\"/>",
            "No such class 'abc'"
        );
    }

}
