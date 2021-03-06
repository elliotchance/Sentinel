package org.sentinel.configuration;

import org.sentinel.log.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Server implements ConfigurationParser, ConfigurationNode
{

    protected String name = null;
    protected Class protocol = null;
    protected Class configuration = null;
    
    public Server()
    {
    }

    public Server(String name, Class protocol, Class configuration)
    {
        this.name = name;
        this.protocol = protocol;
        this.configuration = configuration;
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

    public Class getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Class configuration)
    {
        this.configuration = configuration;
    }

    /**
     * This is a special case of parseRoot() since it will not return an instance of itself like the
     * other parsers do. Instead it will find the 'configuration' attribute on the &lt;server&gt;
     * tag and create an instance of that class to parse itself.
     * 
     * @param node Root node.
     * @return A dynamic instance of whatever class is specified in the 'configuration' attribute.
     * @throws ConfigurationException If something goes wrong.
     */
    @Override
    public ConfigurationNode parseRoot(Node node) throws ConfigurationException
    {
        try {
            // attributes
            NamedNodeMap serverAttributes = node.getAttributes();

            // server
            Node nameValue = serverAttributes.getNamedItem("name");
            if(nameValue == null) {
                throw new ConfigurationException("You must specify the name for <server>");
            }
            name = nameValue.getNodeValue();
            
            // protocol
            Node protocolValue = serverAttributes.getNamedItem("protocol");
            if(protocolValue == null) {
                throw new ConfigurationException("You must specify the protocol for <server>");
            }
            protocol = Class.forName(protocolValue.getNodeValue());
            
            // configuration
            Node configurationValue = serverAttributes.getNamedItem("configuration");
            if(configurationValue == null) {
                throw new ConfigurationException("You must specify the configuration for <server>");
            }
            configuration = Class.forName(configurationValue.getNodeValue());

            // special handler
            Server parser = (Server) configuration.newInstance();
            
            // copy all the properties of this class into the new instance
            parser.setConfiguration(configuration);
            parser.setName(name);
            parser.setProtocol(protocol);
            
            return parser.parseRoot(node);
        }
        catch(ClassNotFoundException ex) {
            throw new ConfigurationException("No such class '" + ex.getMessage() + "'");
        }
        catch(Exception ex) {
            throw new ConfigurationException(ex.getMessage());
        }
    }

    @Override
    public void parseTextElement(String content) throws ConfigurationException
    {
        // ignore
    }

    @Override
    public boolean parseElement(Node node) throws ConfigurationException
    {
        // ignore
        return false;
    }

    @Override
    public boolean parseAttribute(String name, String value) throws ConfigurationException
    {
        // ignore
        return false;
    }

    @Override
    public String[] getRequiredChildElements()
    {
        // ignore
        return new String[] { };
    }

    @Override
    public String[] getRequiredAttributes()
    {
        // ignore
        return new String[] { };
    }

    @Override
    public String toString()
    {
        return "<server configuration=\"" + configuration.getCanonicalName() + "\" name=\"" +
            name + "\" protocol=\"" + protocol.getCanonicalName() + "\"/>";
    }
    
}
