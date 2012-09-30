package org.sentinel.configuration;

public class Server
{

    private String name;
    private Class protocol;
    private Class server;

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

    @Override
    public String toString()
    {
        return "Server{" + "name=" + name + ", protocol=" + protocol + ", server=" + server + '}';
    }
}
