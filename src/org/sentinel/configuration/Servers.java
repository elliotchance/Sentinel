package org.sentinel.configuration;

import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Servers extends HashMap<String, Server> implements ConfigurationParser,
    ConfigurationNode
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
            
            // <server>
            if(child.getNodeName().equals("server")) {
                Server server = (Server) new Server().parseRoot(child);
                put(server.getName(), server);
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
        
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
    
}
