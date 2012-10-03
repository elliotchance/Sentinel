package org.sentinel.servers.helloworld.configuration;

import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.w3c.dom.Node;

public class Server extends org.sentinel.configuration.Server implements ConfigurationNode
{

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        return this;
    }
    
}
