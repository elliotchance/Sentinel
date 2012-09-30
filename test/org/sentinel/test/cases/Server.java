package org.sentinel.test.cases;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.sentinel.Main;
import org.sentinel.Sentinel;
import org.sentinel.test.Client;

public class Server
{
    
    private Client client = null;
    
    private int port;
    
    private Sentinel server;

    public Server(int port) throws Exception
    {
        this.port = port;
        init();
    }
    
    private void init() throws Exception
    {
        // launch the server
        URL resource = Main.class.getResource("/org/sentinel/configuration/default.xml");
		server = new Sentinel(resource.getFile());
		server.run();
        
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
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
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
