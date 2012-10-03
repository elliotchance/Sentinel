package org.sentinel.servers.http.configuration;

import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Server extends org.sentinel.configuration.Server implements ConfigurationNode
{
    
    protected Applications applications;

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
            
            // <applications>
            if(child.getNodeName().equals("applications")) {
                applications = (Applications) new Applications().parseRoot(child);
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
        
        return this;
    }

    @Override
    public String toString()
    {
        String base = super.toString();
        if(applications != null) {
            base = base.substring(0, base.length() - 2) + ">" + applications.toString() + "</server>";
        }
        return base;
    }
    
}
