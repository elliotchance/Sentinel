package org.sentinel.servers.http.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.configuration.ConfigurationParser;
import org.sentinel.configuration.ConfigurationParserHelper;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Application implements ConfigurationParser, ConfigurationNode
{

    protected String name;
    
    protected Class application;
    
    protected String prefix;
    
    public Application()
    {
    }

    public Application(String name, Class application, String prefix)
    {
        this.name = name;
        this.application = application;
        this.prefix = prefix;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Class getApplication()
    {
        return application;
    }

    public void setApplication(Class application)
    {
        this.application = application;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        // no children
        ConfigurationParserHelper.nodeShouldHaveNoChildren(node);
        
        try {
            // attributes
            NamedNodeMap listenerAttributes = node.getAttributes();

            name = listenerAttributes.getNamedItem("name").getNodeValue();
            application = Class.forName(listenerAttributes.getNamedItem("application").getNodeValue());
            prefix = listenerAttributes.getNamedItem("prefix").getNodeValue();
            
            return this;
        }
        catch(ClassNotFoundException ex) {
            throw new ConfigurationException("No such class '" + ex.getMessage() + "'");
        }
    }

    @Override
    public String toString()
    {
        return "<application application=\"" + application.getCanonicalName() + "\" name=\"" +
            name + "\" prefix=\"" + prefix + "\"/>";
    }
    
}
