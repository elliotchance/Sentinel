package org.sentinel.configuration;

import org.junit.Test;
import static org.junit.Assert.*;
import org.sentinel.test.Client;
import org.xml.sax.SAXParseException;

public class ConfigurationTest
{
    
    protected final String badXmlPackage = "/org/sentinel/configuration/test";
    
    protected org.sentinel.test.cases.Server server = null;
    
    protected Client launchServerWithConfiguration(String configurationFile) throws Exception
    {
        // kill server if its already running
        if(server != null) {
            server.kill();
        }
        server = new org.sentinel.test.cases.Server(4040, configurationFile);
        return server.getClient();
    }
    
    @Test
    public void testBlankConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/blank.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(SAXParseException ex) {
            assertEquals("Premature end of file.", ex.getMessage());
        }
    }
    
    @Test
    public void testIncorrectRootConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/incorrect-root.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Root node of configuration file must be 'sentinel'.", ex.getMessage());
        }
    }
    
}
