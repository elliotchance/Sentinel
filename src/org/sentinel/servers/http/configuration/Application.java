package org.sentinel.servers.http.configuration;

import java.util.ArrayList;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.configuration.ConfigurationParser;
import org.sentinel.configuration.ConfigurationParserHelper;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Application implements ConfigurationParser, ConfigurationNode
{

    protected String name;
    
    protected Class application;
    
    protected String prefix;
    
    protected ArrayList<Static> statics = new ArrayList<Static>();
    
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

    public ArrayList<Static> getStatics()
    {
        return statics;
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
        return "<application application=\"" + application.getCanonicalName() + "\" name=\"" +
            name + "\" prefix=\"" + prefix + "\"/>";
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // <static>
        if(node.getNodeName().equals("static")) {
            statics.add((Static) new Static().parseRoot(node));
            return true;
        }
        
        return false;
    }

    @Override
    public boolean parseAttribute(String name, String value) throws ConfigurationException
    {
        if(name.equals("name")) {
            this.name = value;
            return true;
        }
        
        if(name.equals("application")) {
            try {
                this.application = Class.forName(value);
                return true;
            }
            catch(ClassNotFoundException ex) {
                throw new ConfigurationException("No such class '" + ex.getMessage() + "'");
            }
        }
        
        if(name.equals("prefix")) {
            this.prefix = value;
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
        return new String[] { "name", "application", "prefix" };
    }
    
}
