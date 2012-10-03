package org.sentinel.configuration;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sentinel implements ConfigurationParser, ConfigurationNode
{

    private Listeners listeners = null;
    private Servers servers = null;

    /**
     * When unit testing this class we want listeners and servers to be initialized.
     * @return A new instance of Sentinel.
     */
    public static Sentinel debugInstance()
    {
        Sentinel sentinel = new Sentinel();
        sentinel.listeners = new Listeners();
        sentinel.servers = new Servers();
        return sentinel;
    }
    
    public static final String DEFAULT_CONFIGURATION = "/org/sentinel/configuration/default.xml";

    public void addListener(Listener listener)
    {
        listeners.put(listener.getPort(), listener);
    }

    public void addServer(Server server)
    {
        servers.put(server.getName(), server);
    }

    public Listeners getListeners()
    {
        return listeners;
    }

    public Servers getServers()
    {
        return servers;
    }

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        if(!node.getNodeName().equals("sentinel")) {
            throw new ConfigurationException("Root node of configuration file must be 'sentinel'.");
        }
        
        // read children
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            
            // ignore text
            if(child.getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            
            // <listeners>
            if(child.getNodeName().equals("listeners")) {
                listeners = (Listeners) new Listeners().parseRoot(child);
                continue;
            }
            
            // <servers>
            if(child.getNodeName().equals("servers")) {
                servers = (Servers) new Servers().parseRoot(child);
                continue;
            }
            
            throw new ConfigurationException("Bad child node '" + child.getNodeName() + "'");
        }
        
        // we need at least one listener
        if(listeners.isEmpty()) {
            throw new ConfigurationException("There are no listeners configured.");
        }
        
        // validate servers
        for(Listener listener : listeners.values()) {
            String serverName = listener.getServer();
            
            boolean found = false;
            for(Server server : servers.values()) {
                if(server.getName().equals(serverName)) {
                    found = true;
                    break;
                }
            }
            
            if(!found) {
                throw new ConfigurationException("No such configuration server '" + serverName +
                    "'");
            }
        }
        
        return this;
    }

    @Override
    public String toString()
    {
        String r = "<sentinel>";
        if(listeners != null) {
            r += listeners;
        }
        if(servers != null) {
            r += servers;
        }
        return r + "</sentinel>";
    }
    
}
