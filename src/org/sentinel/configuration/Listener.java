package org.sentinel.configuration;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Listener implements ConfigurationParser, ConfigurationNode
{

    private int port;
    private String server;

    public Listener()
    {
    }

    public Listener(int port, String server)
    {
        this.port = port;
        this.server = server;
    }

    public int getPort()
    {
        return port;
    }

    public String getServer()
    {
        return server;
    }

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        // no children
        ConfigurationParserHelper.nodeShouldHaveNoChildren(node);
        
        // attributes
        NamedNodeMap listenerAttributes = node.getAttributes();

        // port
        Node portValue = listenerAttributes.getNamedItem("port");
        if(portValue == null) {
            throw new ConfigurationException("You must specify the port attribute for <listener>");
        }
        port = Integer.valueOf(portValue.getNodeValue());
        
        // server
        Node serverValue = listenerAttributes.getNamedItem("server");
        if(serverValue == null) {
            throw new ConfigurationException("You must specify the server attribute for <listener>");
        }
        server = listenerAttributes.getNamedItem("server").getNodeValue();
        
        return this;
    }

    @Override
    public String toString()
    {
        return "<listener port=\"" + port + "\" server=\"" + server + "\"/>";
    }
    
}
