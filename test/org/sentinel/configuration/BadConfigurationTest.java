package org.sentinel.configuration;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.client.Client;

public class BadConfigurationTest
{
    
    protected final String badXmlPackage = "/org/sentinel/configuration/test";
    
    protected org.sentinel.test.cases.ServerCase server = null;
    
    protected Client launchServerWithConfiguration(String configurationFile) throws Exception
    {
        server = new org.sentinel.test.cases.ServerCase(
            org.sentinel.servers.helloworld.Client.class,
            4040,
            configurationFile
        );
        server.setUp();
        return server.getClient();
    }
    
    @Test
    public void testBlankConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/blank.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Configuration XML file is invalid: Premature end of file.",
                ex.getMessage());
        }
    }
    
    @Test
    public void testIncorrectRootConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/incorrect-root.xml");
            fail("Server should not have launched.");
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
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("No such configuration server 'helloWorldServer'", ex.getMessage());
        }
    }
    
    @Test
    public void testMissingClassConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/missing-class.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("No such class 'no.such.protocol'", ex.getMessage());
        }
    }
    
    @Test
    public void testMissingConfiguration() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/no-such-file.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Could not find configuration file /org/sentinel/configuration/test/no-such-file.xml", ex.getMessage());
        }
    }
    
    @Test
    public void testNoListeners() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/no-listeners.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("There are no listeners configured.", ex.getMessage());
        }
    }
    
    @Test
    public void testMissingRequiredNode() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/missing-required-node.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Child element <listeners> is required for <sentinel>", ex.getMessage());
        }
    }
    
    @Test
    public void testInvalidAttribute() throws Exception
    {
        try {
            Client client = launchServerWithConfiguration(badXmlPackage + "/invalid-attribute.xml");
            fail("Server should not have launched.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Bad attribute 'foo'", ex.getMessage());
        }
    }
    
    @After
    public void tearDown()
    {
        server.tearDown();
    }
    
}
