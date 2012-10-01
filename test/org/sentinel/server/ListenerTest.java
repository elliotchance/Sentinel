package org.sentinel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.sentinel.SentinelException;
import org.sentinel.SentinelRuntimeException;

public class ListenerTest
{
    
    private SentinelProtocol protocol = new org.sentinel.servers.helloworld.Protocol();
    private SentinelServer server = new org.sentinel.servers.helloworld.Server();
    
    class ListenerMock extends Listener
    {

        ListenerMock(int port, SentinelProtocol protocol, SentinelServer server)
        {
            super(port, protocol, server);
        }
        
        public void mockAccept()
        {
            try {
                serverSocket = Mockito.mock(ServerSocket.class);
                Mockito.when(serverSocket.accept()).thenReturn(null);
            }
            catch(IOException ex) {
                fail(ex.getMessage());
            }
        }
        
        public void mockSoTimeout()
        {
            initialized = true;
            try {
                serverSocket = Mockito.mock(ServerSocket.class);
                Mockito.doThrow(new SocketException("setSoTimeout() failed"))
                    .when(serverSocket)
                    .setSoTimeout(100);
            }
            catch(IOException ex) {
                fail(ex.getMessage());
            }
        }
        
        public void mockSocketClose()
        {
            initialized = true;
            try {
                serverSocket = Mockito.mock(ServerSocket.class);
                Mockito.doThrow(new SocketException("close() failed"))
                    .when(serverSocket)
                    .close();
            }
            catch(IOException ex) {
                fail(ex.getMessage());
            }
        }
        
    }
    
    class ListenerMockHandleClient extends ListenerMock
    {

        public ListenerMockHandleClient(int port, SentinelProtocol protocol, SentinelServer server)
        {
            super(port, protocol, server);
        }

        @Override
        public void handleClient(Socket clientSocket) throws IOException
        {
            throw new IOException("Cient error.");
        }
        
    }
    
    class ListenerMockAcceptConnection extends ListenerMock
    {

        public ListenerMockAcceptConnection(int port, SentinelProtocol protocol, SentinelServer server)
        {
            super(port, protocol, server);
        }

        @Override
        protected boolean acceptConnection()
        {
            return false;
        }
        
    }
    
    class ListenerMockJoin extends ListenerMock
    {

        public ListenerMockJoin(int port, SentinelProtocol protocol, SentinelServer server)
        {
            super(port, protocol, server);
        }

        @Override
        public void waitForThreadToJoin() throws InterruptedException
        {
            throw new InterruptedException("waitForThreadToJoin() failed");
        }
        
    }

    @Test
    public void testServersClashPort() throws Exception
    {
        Listener listener1 = new Listener(1234, protocol, server);
        listener1.init();
        listener1.start();
        
        try {
            Listener listener2 = new Listener(1234, protocol, server);
            listener2.init();
            listener2.start();
        }
        catch(SentinelException ex) {
            assertEquals("Address already in use", ex.getMessage());
        }
        
        listener1.stopGracefully();
    }
    
    @Test
    public void testUninitializedRun()
    {
        Listener listener1 = null;
        try {
            listener1 = new Listener(1234, protocol, server);
            listener1.run();
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("Listener is not initialised, you must invoke init() before start().",
                ex.getMessage());
        }
        listener1.stopGracefully();
    }
    
    @Test
    public void testSocketAcceptFail()
    {
        try {
            ListenerMockHandleClient listener1 =
                new ListenerMockHandleClient(1234, protocol, server);
            listener1.mockAccept();
            listener1.acceptConnection();
            fail("Didn't throw client error.");
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("Cient error.", ex.getMessage());
        }
    }
    
    @Test
    public void testSetSoTimeoutFail()
    {
        try {
            ListenerMock listener1 = new ListenerMock(1234, protocol, server);
            listener1.mockSoTimeout();
            listener1.run();
            fail("Didn't throw SocketException.");
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("setSoTimeout() failed", ex.getMessage());
        }
    }
    
    @Test
    public void testAcceptConnectionFinished() throws SentinelException
    {
        ListenerMockAcceptConnection listener1 =
            new ListenerMockAcceptConnection(1234, protocol, server);
        listener1.init();
        listener1.run();
        
        // now make sure the thread has stopped running
        assertFalse(listener1.isAlive());
    }
    
    @Test
    public void testSocketCloseFail()
    {
        try {
            ListenerMockAcceptConnection listener1 =
                new ListenerMockAcceptConnection(1234, protocol, server);
            listener1.mockSocketClose();
            listener1.run();
            fail("Didn't throw IOException.");
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("close() failed", ex.getMessage());
        }
    }
    
    @Test
    public void testThreadJoinFail()
    {
        try {
            ListenerMockJoin listener1 = new ListenerMockJoin(1234, protocol, server);
            listener1.stopGracefully();
            fail("Didn't throw InterruptedException.");
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("waitForThreadToJoin() failed", ex.getMessage());
        }
    }
    
}
