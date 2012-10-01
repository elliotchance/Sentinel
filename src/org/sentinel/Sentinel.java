package org.sentinel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.sentinel.configuration.Configuration;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.Listener;
import org.sentinel.configuration.Server;
import org.sentinel.server.SentinelProtocol;
import org.sentinel.server.SentinelServer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Sentinel
{

    private String configurationFile = null;
    private Configuration configuration = null;
    private ArrayList<org.sentinel.server.Listener> listeners = null;
    private boolean ready = false;

    public Sentinel()
    {
    }

    public Sentinel(String configurationFile)
    {
        this.configurationFile = configurationFile;
    }
    
    protected DocumentBuilderFactory getDocumentBuilderFactoryInstance()
        throws ParserConfigurationException
    {
        return DocumentBuilderFactory.newInstance();
    }
    
    /**
     * Read a configuration file.
     */
    public Document parseConfigurationFile(File configurationFile) throws ConfigurationException
    {
        try {
            DocumentBuilderFactory dbFactory = getDocumentBuilderFactoryInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configurationFile);
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch(SAXException ex) {
            throw new ConfigurationException("Configuration XML file is invalid: " + ex.getMessage());
        }
        catch(ParserConfigurationException ex) {
            throw new ConfigurationException(ex.getMessage());
        }
        catch(IOException ex) {
            throw new ConfigurationException(ex.getMessage());
        }
    }

    public void run() throws SentinelException
    {
        try {
            Document doc = parseConfigurationFile(new File(configurationFile));
            
            // build internal configuration
            if(!doc.getDocumentElement().getNodeName().equals("sentinel")) {
                throw new ConfigurationException("Root node of configuration file must be 'sentinel'.");
            }
            configuration = new Configuration();

            // listeners
            NodeList listeners = doc.getDocumentElement().getElementsByTagName("listener");
            for(int i = 0; i < listeners.getLength(); ++i) {
                NamedNodeMap listenerAttributes = listeners.item(i).getAttributes();

                int port = Integer.valueOf(listenerAttributes.getNamedItem("port").getNodeValue());
                String server = listenerAttributes.getNamedItem("server").getNodeValue();

                Listener listener = new Listener(port, server);
                configuration.addListener(listener);
            }

            // servers
            NodeList servers = doc.getDocumentElement().getElementsByTagName("server");
            for(int i = 0; i < servers.getLength(); ++i) {
                NamedNodeMap serverAttributes = servers.item(i).getAttributes();

                String name = serverAttributes.getNamedItem("name").getNodeValue();
                Class protocol = Class.forName(serverAttributes.getNamedItem("protocol").getNodeValue());
                Class serverClass = Class.forName(serverAttributes.getNamedItem("server").getNodeValue());

                Server server = new Server(name, protocol, serverClass);
                configuration.addServer(server);
            }

            launch();
        }
        catch(ClassNotFoundException ex) {
            throw new ConfigurationException("No such class " + ex.getMessage());
        }
    }

    public void launch() throws SentinelException
    {
        listeners = new ArrayList<org.sentinel.server.Listener>();
        for(Listener listener : configuration.getListeners()) {
            org.sentinel.configuration.Server configServer = configuration.getServer(listener.getServer());
            launchListener(listener, configServer.getProtocol(), configServer.getServer());
        }
        ready = true;
    }
    
    public void launchListener(Listener listenerDefinition, Class protocol, Class server) throws
        SentinelException
    {
        try {
            int port = listenerDefinition.getPort();
            SentinelProtocol theProtocol = (SentinelProtocol) protocol.newInstance();
            SentinelServer theServer = (SentinelServer) server.newInstance();
            org.sentinel.server.Listener listener = new org.sentinel.server.Listener(port, theProtocol, theServer);
            listener.init();
            listener.start();
            listeners.add(listener);
        }
        catch(Exception ex) {
            throw new SentinelException("Can not instantiate: " + ex.getMessage());
        }
    }
    
    public void stopGracefully()
    {
        // tell each listener to shutdown
        if(listeners != null) {
            for(org.sentinel.server.Listener listener : listeners) {
                listener.stopGracefully();
            }
        }
    }
    
    public boolean isReady()
    {
        return ready;
    }

}
