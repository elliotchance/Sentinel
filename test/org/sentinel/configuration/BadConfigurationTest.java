package org.sentinel.configuration;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.sentinel.test.Client;

public class BadConfigurationTest
{
    
    protected final String badXmlPackage = "/org/sentinel/configuration/test";
    
    protected org.sentinel.test.cases.ServerCase server = null;
    
    protected Client launchServerWithConfiguration(String configurationFile) throws Exception
    {
        // kill server if its already running
        /*if(server != null) {
            server.kill();
        }*/
        server = new org.sentinel.test.cases.ServerCase(4040, configurationFile);
        return server.getClient();
    }
    
    @Test
    public void testBlankConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/blank.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Configuration XML file is invalid: Premature end of file.", ex.getMessage());
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
    
    @Test
    public void testMissingServerConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/missing-server.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("No such configuration server helloWorldServer", ex.getMessage());
        }
    }
    
    @Test
    public void testMissingClassConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/missing-class.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("No such class no.such.protocol", ex.getMessage());
        }
    }
    
    @Test
    public void testMissingConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/no-such-file.xml");
            throw new Exception("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Could not load configuration file /org/sentinel/configuration/test/no-such-file.xml", ex.getMessage());
        }
    }
    
}
