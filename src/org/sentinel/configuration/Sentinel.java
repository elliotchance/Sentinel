package org.sentinel.configuration;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sentinel implements ConfigurationParser, ConfigurationNode
{

    /**
     * Listeners.
     */
    private Listeners listeners = null;
    
    /**
     * Servers.
     */
    private Servers servers = null;
    
    /**
     * The default configuration location. This is a package path.
     */
    public static final String DEFAULT_CONFIGURATION = "/org/sentinel/configuration/default.xml";

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

    /**
     * Add a listener. If the listener has the same port as a listener already added it will replace
     * it.
     * @param listener The listener.
     */
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
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // <listeners>
        if(node.getNodeName().equals("listeners")) {
            listeners = (Listeners) new Listeners().parseRoot(node);
            return true;
        }

        // <servers>
        if(node.getNodeName().equals("servers")) {
            servers = (Servers) new Servers().parseRoot(node);
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
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        if(!node.getNodeName().equals("sentinel")) {
            throw new ConfigurationException("Root node of configuration file must be 'sentinel'.");
        }
        
        ConfigurationParserHelper.parseRoot(node, this);
        
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

    @Override
    public String[] getRequiredChildElements()
    {
        return new String[] { "listeners", "servers" };
    }

    @Override
    public String[] getRequiredAttributes()
    {
        return new String[] { };
    }
    
}
