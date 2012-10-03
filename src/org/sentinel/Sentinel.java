package org.sentinel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.Listener;
import org.sentinel.configuration.NoSuchConfigurationServerException;
import org.sentinel.server.SentinelProtocol;
import org.sentinel.server.SentinelServer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Sentinel
{

    private String configurationFile = null;
    private org.sentinel.configuration.Sentinel configuration = null;
    private ArrayList<org.sentinel.server.Listener> listeners = null;
    private boolean ready = false;

    /**
     * Empty constructor. Mostly used for debugging.
     */
    public Sentinel()
    {
    }

    /**
     * Create a new Sentinel instance with the XML configuration file. This will not start the
     * listeners themselves.
     * @param configurationFile Path to the configuration file.
     */
    public Sentinel(String configurationFile)
    {
        this.configurationFile = configurationFile;
    }
    
    /**
     * This method is here for JUnit. It is overridden for some tests.
     * @return A new instance of DocumentBuilderFactory
     * @throws ParserConfigurationException
     */
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
        // parse configuration file
        Document doc = parseConfigurationFile(new File(configurationFile));
        configuration = new org.sentinel.configuration.Sentinel();
        configuration.parseRoot(doc.getFirstChild());
            
        launch();
    }

    public void launch() throws SentinelException
    {
        listeners = new ArrayList<org.sentinel.server.Listener>();
        for(Listener listener : configuration.getListeners().values()) {
            org.sentinel.configuration.Server configServer = configuration.getServers().get(listener.getServer());
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
