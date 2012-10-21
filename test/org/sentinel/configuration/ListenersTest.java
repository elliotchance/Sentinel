package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;

public class ListenersTest extends ConfigurationParserCase
{

    @Test
    public void testToString()
    {
        Listeners listeners = new Listeners();
        assertEquals("<listeners></listeners>", listeners.toString());
    }

    @Test
    public void testToString2()
    {
        Listeners listeners = new Listeners();
        Listener listener = new Listener(1234, "abc");
        listeners.put(listener.getPort(), listener);
        assertEquals("<listeners>" + listener.toString() + "</listeners>", listeners.toString());
    }

    @Test
    public void testParseRoot()
    {
        assertBadChildNode(new Listeners(), "<listeners><bad/></listeners>");
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Listeners().parseAttribute(null, null));
    }

}
