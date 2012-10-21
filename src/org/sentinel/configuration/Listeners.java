package org.sentinel.configuration;

import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Listeners extends HashMap<Integer, Listener> implements ConfigurationNode,
    ConfigurationParser
{

    @Override
    public String toString()
    {
        String r = "<listeners>";
        for(Listener listener : values()) {
            r += listener;
        }
        return r + "</listeners>";
    }

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        ConfigurationParserHelper.parseRoot(node, this);
        return this;
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // <listener>
        if(node.getNodeName().equals("listener")) {
            Listener listener = (Listener) new Listener().parseRoot(node);
            put(listener.getPort(), listener);
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
