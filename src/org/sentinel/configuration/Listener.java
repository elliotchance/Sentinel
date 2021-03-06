package org.sentinel.configuration;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Listener implements ConfigurationParser, ConfigurationNode
{

    private int port;
    private String server;

    public Listener()
    {
    }

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

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        ConfigurationParserHelper.parseRoot(node, this);
        return this;
    }

    @Override
    public String toString()
    {
        return "<listener port=\"" + port + "\" server=\"" + server + "\"/>";
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        return false;
    }

    @Override
    public boolean parseAttribute(String name, String value) throws ConfigurationException
    {
        if(name.equals("port")) {
            port = Integer.valueOf(value);
            return true;
        }
        
        if(name.equals("server")) {
            server = value;
            return true;
        }
        
        return false;
    }

    @Override
    public String[] getRequiredChildElements()
    {
        return new String[] { };
    }

    @Override
    public String[] getRequiredAttributes()
    {
        return new String[] { "port", "server" };
    }
    
}
