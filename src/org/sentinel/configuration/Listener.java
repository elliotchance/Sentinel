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

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    @Override
    public String toString()
    {
        return "Listener{" + "port=" + port + ", server=" + server + '}';
    }
}
