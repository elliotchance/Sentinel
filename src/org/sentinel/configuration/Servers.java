package org.sentinel.configuration;

import java.util.HashMap;
import org.w3c.dom.Node;

public class Servers extends HashMap<String, Server> implements ConfigurationParser,
    ConfigurationNode
{

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        ConfigurationParserHelper.parseRoot(node, this);
        return this;
    }

    @Override
    public String toString()
    {
        String r = "<servers>";
        for(Server server : values()) {
            r += server;
        }
        return r + "</servers>";
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // <server>
        if(node.getNodeName().equals("server")) {
            Server server = (Server) new Server().parseRoot(node);
            put(server.getName(), server);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean parseAttribute(String name, String value) throws ConfigurationException
    {
        return false;
    }

    @Override
    public String[] getRequiredChildElements()
    {
        return new String[] { };
    }

    @Override
    public String[] getRequiredAttributes()
    {
        return new String[] { };
    }
    
}
