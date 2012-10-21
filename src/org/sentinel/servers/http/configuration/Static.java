package org.sentinel.servers.http.configuration;

import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.configuration.ConfigurationParser;
import org.sentinel.configuration.ConfigurationParserHelper;
import org.w3c.dom.Node;

public class Static implements ConfigurationParser, ConfigurationNode
{

    protected String path;
    
    public Static()
    {
    }

    public Static(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
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
        return "<static path=\"" + path + "\"/>";
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
        if(name.equals("path")) {
            this.path = value;
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
        return new String[] { "path" };
    }
    
}
