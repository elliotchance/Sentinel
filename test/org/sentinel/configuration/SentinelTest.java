package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;

public class SentinelTest extends ConfigurationParserCase
{

    @Test
    public void testParseRoot()
    {
        assertBadChildNode(new Sentinel(), "<sentinel><bad/></sentinel>");
    }

    @Test
    public void testToString()
    {
        assertEquals(new Sentinel().toString(), "<sentinel></sentinel>");
    }

    @Test
    public void testToString2()
    {
        assertEquals(Sentinel.debugInstance().toString(),
            "<sentinel><listeners></listeners><servers></servers></sentinel>");
    }

}
