package org.sentinel.servers.http.configuration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.sentinel.configuration.ConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public class ApplicationTest extends org.sentinel.test.cases.ConfigurationParserCase
{

    @Test
    public void testName()
    {
        String string = "abc";
        Application instance = new Application();
        instance.setName(string);
        assertEquals(string, instance.getName());
    }

    @Test
    public void testApplication()
    {
        Class klass = this.getClass();
        Application instance = new Application();
        instance.setApplication(klass);
        assertEquals(klass, instance.getApplication());
    }

    @Test
    public void testPrefix()
    {
        String string = "abc";
        Application instance = new Application();
        instance.setPrefix(string);
        assertEquals(string, instance.getPrefix());
    }

    @Test
    public void testBadApplicationClass()
    {
        assertConfigurationException(
            new Application(),
            "<application name=\"123\" application=\"abc\"/>",
            "No such class 'abc'"
        );
    }

    @Test
    public void testParseAttribute() throws ConfigurationException
    {
        assertFalse(new Application().parseAttribute("doesntexist", null));
    }

    @Test
    public void testParseElement() throws ConfigurationException
    {
        assertFalse(new Application().parseElement(new NodeImpl("doesntexist")));
    }
    
    class NodeImpl implements Node
    {
        
        private String nodeName;

        public NodeImpl(String nodeName)
        {
            this.nodeName = nodeName;
        }

        @Override
        public String getNodeName()
        {
            return nodeName;
        }

        @Override
        public String getNodeValue() throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setNodeValue(String string) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public short getNodeType()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node getParentNode()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NodeList getChildNodes()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node getFirstChild()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node getLastChild()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node getPreviousSibling()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node getNextSibling()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NamedNodeMap getAttributes()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Document getOwnerDocument()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node insertBefore(Node node, Node node1) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node replaceChild(Node node, Node node1) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node removeChild(Node node) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node appendChild(Node node) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean hasChildNodes()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Node cloneNode(boolean bln)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void normalize()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isSupported(String string, String string1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getNamespaceURI()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getPrefix()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setPrefix(String string) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getLocalName()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean hasAttributes()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getBaseURI()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public short compareDocumentPosition(Node node) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getTextContent() throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setTextContent(String string) throws DOMException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isSameNode(Node node)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String lookupPrefix(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDefaultNamespace(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String lookupNamespaceURI(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isEqualNode(Node node)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getFeature(String string, String string1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object setUserData(String string, Object o, UserDataHandler udh)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getUserData(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

}
