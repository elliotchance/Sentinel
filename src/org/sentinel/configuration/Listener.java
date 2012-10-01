package org.sentinel.configuration;

public class Listener
{

    private int port;
    private String server;

    public Listener(int port, String server)
    {
        this.port = port;
        this.server = server;
    }

    public int getPort()
    {
        return port;
    }

    public String getServer()
    {
        return server;
    }
    
}
