package org.sentinel.servers.http.configuration;

import java.util.ArrayList;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.configuration.ConfigurationParser;
import org.sentinel.configuration.ConfigurationParserHelper;
import org.w3c.dom.Node;

public class Applications extends ArrayList<Application> implements ConfigurationParser,
    ConfigurationNode
{

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        ConfigurationParserHelper.parseRoot(node, this);
        return this;
    }

    @Override
    public String toString()
    {
        String r = "<applications>";
        for(Application application : this) {
            r += application;
        }
        return r + "</applications>";
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // <application>
        if(node.getNodeName().equals("application")) {
            add((Application) new Application().parseRoot(node));
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
    public String[] getRequiredChildElements()
    {
        return new String[] { "application" };
    }

    @Override
    public String[] getRequiredAttributes()
    {
        return new String[] { };
    }
    
}
