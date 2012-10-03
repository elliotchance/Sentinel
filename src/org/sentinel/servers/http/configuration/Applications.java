package org.sentinel.servers.http.configuration;

import java.util.ArrayList;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.configuration.ConfigurationParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Applications extends ArrayList<Application> implements ConfigurationParser,
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
            
            // <application>
            if(child.getNodeName().equals("application")) {
                add((Application) new Application().parseRoot(child));
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
        
        return this;
    }

    @Override
    public String toString()
    {
        String r = "<applications>";
        for(Application application : this) {
            r += application;
        }
        return r + "</applications>";
    }
    
}
