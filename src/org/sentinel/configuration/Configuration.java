package org.sentinel.configuration;

public class Configuration
{

    private Listeners listeners = new Listeners();
    private Servers servers = new Servers();
    
    public static final String DEFAULT_CONFIGURATION = "/org/sentinel/configuration/default.xml";

    public void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    public void addServer(Server server)
    {
        servers.add(server);
    }

    public Listeners getListeners()
    {
        return listeners;
    }

    public Servers getServers()
    {
        return servers;
    }

    public Server getServer(String name) throws NoSuchConfigurationServerException
    {
        for(Server server : servers) {
            if(server.getName().equals(name)) {
                return server;
            }
        }
        throw new NoSuchConfigurationServerException(name);
    }
    
}
