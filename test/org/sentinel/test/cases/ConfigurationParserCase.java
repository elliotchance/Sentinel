package org.sentinel.test.cases;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static org.junit.Assert.*;
import org.sentinel.configuration.ConfigurationParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ConfigurationParserCase
{

    public Node convertXmlToNode(String xml) throws Exception
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        String realXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
        InputStream inputStream = new ByteArrayInputStream(realXml.getBytes());
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc.getFirstChild();
    }
    
    public void assertConfigurationException(ConfigurationParser parser, String xml, String message)
    {
        try {
            Node root = convertXmlToNode(xml);
            parser.parseRoot(root);
            fail("Should have failed.");
        }
        catch(Exception ex) {
            assertEquals(message, ex.getMessage());
        }
    }
    
    public void assertBadChildNode(ConfigurationParser parser, String xml)
    {
        assertConfigurationException(parser, xml, "Bad child node 'bad'");
    }
    
}
