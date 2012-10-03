package org.sentinel.configuration;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurationParserHelper
{

    public static void nodeShouldHaveNoChildren(Node node) throws ConfigurationException
    {
        // no children
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            
            // ignore text
            if(child.getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
    }
    
}
