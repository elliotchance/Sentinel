package org.sentinel.test.cases;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.sentinel.Main;
import org.sentinel.Sentinel;
import org.sentinel.client.Client;
import org.sentinel.configuration.Configuration;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.server.SentinelServer;

public class ServerCase
{
    
    protected org.sentinel.client.Client theClient;
    
    protected String configurationFile;
    
    protected Class clientClass;
    
    protected Sentinel server = null;
    
    protected int port = SentinelServer.DEFAULT_PORT;
    
    public ServerCase(Class clientClass)
    {
        this(clientClass, SentinelServer.DEFAULT_PORT, Configuration.DEFAULT_CONFIGURATION);
    }
    
    public ServerCase(Class clientClass, int port)
    {
        this(clientClass, port, Configuration.DEFAULT_CONFIGURATION);
    }
    
    public ServerCase(Class clientClass, int port, String configurationFile)
    {
        this.clientClass = clientClass;
        this.port = port;
        this.configurationFile = configurationFile;
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp() throws Exception
    {
        // launch the server
        URL resource = Main.class.getResource(configurationFile);
        if(resource == null) {
            throw new ConfigurationException("Could not find configuration file " +
                configurationFile);
        }
		server = new Sentinel(resource.getFile());
		server.run();
        
        // wait for server to be ready
        while(!server.isReady()) {
        }
        
        // setup the client
        theClient = (Client) clientClass.newInstance();
        theClient.setPort(port);
        theClient.connect();
    }
    
    @After
    public void tearDown()
    {
        // shutdown the server
        if(server != null) {
            server.stopGracefully();
        }
    }

    public Client getClient()
    {
        return theClient;
    }
    
}
