package org.sentinel.servers.http.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.test.cases.ConfigurationParserCase;
import org.w3c.dom.Node;

public class ApplicationsTest extends ConfigurationParserCase
{

    @Test
    public void testParseRoot()
    {
        assertBadChildNode(new Applications(), "<sentinel><bad/></sentinel>");
    }

    @Test
    public void testToString()
    {
        assertEquals(new Applications().toString(), "<applications></applications>");
    }

    @Test
    public void testToString2() throws Exception
    {
        Node root = convertXmlToNode("<applications><application application=\"java.lang.Integer\" name=\"a\" prefix=\"b\"/></applications>");
        assertEquals(new Applications().parseRoot(root).toString(),
            "<applications><application application=\"java.lang.Integer\" name=\"a\" prefix=\"b\"/></applications>");
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Applications().parseAttribute(null, null));
    }

}
