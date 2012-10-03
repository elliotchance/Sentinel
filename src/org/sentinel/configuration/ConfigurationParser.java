package org.sentinel.configuration;

import org.w3c.dom.Node;

public interface ConfigurationParser
{
    
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException;
    
}
