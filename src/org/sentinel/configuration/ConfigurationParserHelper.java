package org.sentinel.configuration;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurationParserHelper
{
    
    public static void parseRoot(Node node, ConfigurationParser target)
        throws ConfigurationException
    {
        // read children
        NodeList children = node.getChildNodes();
        String[] requiredChildElements = target.getRequiredChildElements();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            
            // ignore text?
            if(child.getNodeType() == Node.TEXT_NODE) {
                target.parseTextElement(child.getNodeValue());
                continue;
            }
            
            if(!target.parseElement(child)) {
                throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
            }
            
            // mark the child node off as complete
            for(int j = 0; j < requiredChildElements.length; ++j) {
                if(requiredChildElements[j] != null &&
                    requiredChildElements[j].equals(child.getNodeName())) {
                    requiredChildElements[j] = null;
                }
            }
        }
        
        // validate required children
        for(String s : requiredChildElements) {
            if(s != null) {
                throw new ConfigurationException("Child element <" + s + "> is required for <" +
                    node.getNodeName() + ">");
            }
        }
        
        // attributes
        NamedNodeMap attributes = node.getAttributes();
        String[] requiredAttributes = target.getRequiredAttributes();
        for(int i = 0; i < attributes.getLength(); ++i) {
            Node attribute = attributes.item(i);
            
            if(!target.parseAttribute(attribute.getNodeName(), attribute.getNodeValue())) {
                throw new ConfigurationException("Bad attribute '" + attribute.getNodeName() + "'");
            }
            
            // mark the attribute off as complete
            for(int j = 0; j < requiredAttributes.length; ++j) {
                if(requiredAttributes[j] != null &&
                    requiredAttributes[j].equals(attribute.getNodeName())) {
                    requiredAttributes[j] = null;
                }
            }
        }
        
        // validate required attributes
        for(String s : requiredAttributes) {
            if(s != null) {
                throw new ConfigurationException("Attribute '" + s + "' is required for <" +
                    node.getNodeName() + ">");
            }
        }
    }
    
}
