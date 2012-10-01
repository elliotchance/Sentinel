package org.sentinel;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.configuration.Configuration;
import org.sentinel.configuration.ConfigurationException;
import org.sentinel.configuration.Listener;

public class SentinelTest
{
    
    class SentinelMock1 extends Sentinel
    {
        
        @Override
        protected DocumentBuilderFactory getDocumentBuilderFactoryInstance()
            throws ParserConfigurationException
        {
            throw new ParserConfigurationException("Bad factory instance.");
        }
        
    }
    
    abstract class AbstractClass1
    {
    }

    @Test
    public void testParseConfigurationFile1()
    {
        try {
            SentinelMock1 mockSentinel = new SentinelMock1();
            mockSentinel.parseConfigurationFile(new File(Configuration.DEFAULT_CONFIGURATION));
            fail("ConfigurationException should have been thrown.");
        }
        catch(ConfigurationException ex) {
            assertEquals("Bad factory instance.", ex.getMessage());
        }
    }

    @Test
    public void testParseConfigurationFile2()
    {
        try {
            Sentinel sentinel = new Sentinel();
            sentinel.parseConfigurationFile(new File(Configuration.DEFAULT_CONFIGURATION + "-bad"));
            fail("ConfigurationException should have been thrown.");
        }
        catch(ConfigurationException ex) {
            assertEquals("/org/sentinel/configuration/default.xml-bad (No such file or directory)",
                ex.getMessage());
        }
    }
    
    @Test
    public void testLaunchListener1()
    {
        try {
            Sentinel sentinel = new Sentinel();
            Listener listener = new Listener(1234, "bla");
            sentinel.launchListener(listener, AbstractClass1.class, AbstractClass1.class);
        }
        catch(SentinelException ex) {
            assertEquals("Can not instantiate: org.sentinel.SentinelTest$AbstractClass1",
                ex.getMessage());
        }
    }
    
}