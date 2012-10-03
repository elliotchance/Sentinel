package org.sentinel.configuration;

import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Listeners extends HashMap<Integer, Listener> implements ConfigurationNode,
    ConfigurationParser
{

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        // read children
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            
            // ignore text
            if(child.getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            
            // <listener>
            if(child.getNodeName().equals("listener")) {
                Listener listener = (Listener) new Listener().parseRoot(child);
                put(listener.getPort(), listener);
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
        
        return this;
    }

    @Override
    public String toString()
    {
        String r = "<listeners>";
        for(Listener listener : values()) {
            r += listener;
        }
        return r + "</listeners>";
    }
    
}
