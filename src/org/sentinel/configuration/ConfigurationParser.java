package org.sentinel.configuration;

import org.w3c.dom.Node;

/**
 * Classes that parse configuration XML must implement this interface.
 * @see org.sentinel.configuration.ConfigurationParserHelper
 */
public interface ConfigurationParser
{
    
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException;
    
    public void parseTextElement(String content) throws ConfigurationException;
    
    public boolean parseElement(Node node) throws ConfigurationException;
    
    public boolean parseAttribute(String name, String value) throws ConfigurationException;
    
    public String[] getRequiredChildElements();
    
    public String[] getRequiredAttributes();
    
}
