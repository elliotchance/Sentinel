package org.sentinel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.sentinel.configuration.Configuration;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.Listener;
import org.sentinel.configuration.NoSuchConfigurationServerException;
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

    public Sentinel(String configurationFile)
    {
        this.configurationFile = configurationFile;
    }

    public void run() throws Exception
    {
        try {
            // read the configuration file
            File fXmlFile = new File(configurationFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

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
            throw ex;
        }
        catch(SAXException ex) {
            throw ex;
        }
        catch(ParserConfigurationException ex) {
            throw ex;
        }
        catch(IOException ex) {
            throw ex;
        }
    }

    public void launch()
    {
        listeners = new ArrayList<org.sentinel.server.Listener>();
        for(Listener listener : configuration.getListeners()) {
            try {
                org.sentinel.configuration.Server configServer = configuration.getServer(listener.getServer());
                launchListener(listener, configServer.getProtocol(), configServer.getServer());
            }
            catch(NoSuchConfigurationServerException ex) {
                Logger.getLogger(Sentinel.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            catch(Exception ex) {
                Logger.getLogger(Sentinel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void launchListener(Listener listenerDefinition, Class protocol, Class server) throws Exception
    {
        try {
            int port = listenerDefinition.getPort();
            SentinelProtocol theProtocol = (SentinelProtocol) protocol.newInstance();
            SentinelServer theServer = (SentinelServer) server.newInstance();
            org.sentinel.server.Listener listener = new org.sentinel.server.Listener(port, theProtocol, theServer);
            listener.start();
            listeners.add(listener);
        }
        catch(InstantiationException ex) {
            Logger.getLogger(Sentinel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        catch(IllegalAccessException ex) {
            Logger.getLogger(Sentinel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public void kill()
    {
        // kill all the listeners
        for(org.sentinel.server.Listener listener : listeners) {
            listener.interrupt();
        }
    }

}
