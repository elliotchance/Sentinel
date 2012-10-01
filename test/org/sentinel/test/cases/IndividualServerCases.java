package org.sentinel.test.cases;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.sentinel.Main;
import org.sentinel.Sentinel;
import org.sentinel.configuration.Configuration;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.test.Client;

public class IndividualServerCases
{
    
    private Client client = null;
    
    private int port;
    
    private Sentinel server;
    
    private String configurationFile;

    public IndividualServerCases(int port) throws Exception
    {
        this(port, Configuration.DEFAULT_CONFIGURATION);
    }

    public IndividualServerCases(int port, String configurationFile) throws Exception
    {
        this.port = port;
        this.configurationFile = configurationFile;
    }
    
    private void init() throws Exception
    {
        // launch the server
        URL resource = Main.class.getResource(configurationFile);
        if(resource == null) {
            throw new ConfigurationException("Could not load configuration file " +
                configurationFile);
        }
		server = new Sentinel(resource.getFile());
		server.run();
        
        // wait for server to be ready
        while(!server.isReady()) {
            System.out.println("Not ready.");
        }
        
        // setup the client
        client = new Client("localhost", port);
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
        init();
    }
    
    @After
    public void tearDown()
    {
        server.stopGracefully();
    }

    public Client getClient()
    {
        return client;
    }

    public int getPort()
    {
        return port;
    }
    
}
