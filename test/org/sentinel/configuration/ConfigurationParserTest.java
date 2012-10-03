package org.sentinel.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.test.cases.ConfigurationParserCase;
import org.w3c.dom.Node;

public class ConfigurationParserTest extends ConfigurationParserCase
{
    
    class ConfigurationParserMock implements ConfigurationParser, ConfigurationNode
    {

        @Override
        public ConfigurationNode parseRoot(Node node) throws ConfigurationException
        {
            ConfigurationParserHelper.nodeShouldHaveNoChildren(node);
            return this;
        }
        
    }

    @Test
    public void testNodeShouldHaveNoChildren()
    {
        try {
            Node node = this.convertXmlToNode("<parent><child/></parent>");
            ConfigurationParserHelper.nodeShouldHaveNoChildren(node);
        }
        catch(Exception ex) {
            assertEquals("Bad child node 'child'", ex.getMessage());
        }
    }
    
}
