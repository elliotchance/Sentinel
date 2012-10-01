package org.sentinel.configuration;

public class Server
{

    private String name = null;
    private Class protocol = null;
    private Class server = null;
    
    public Server()
    {
    }

    public Server(String name, Class protocol, Class server)
    {
        this.name = name;
        this.protocol = protocol;
        this.server = server;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Class getProtocol()
    {
        return protocol;
    }

    public void setProtocol(Class protocol)
    {
        this.protocol = protocol;
    }

    public Class getServer()
    {
        return server;
    }

    public void setServer(Class server)
    {
        this.server = server;
    }
    
}
