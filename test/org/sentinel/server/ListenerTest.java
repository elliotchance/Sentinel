package org.sentinel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.sentinel.SentinelException;
import org.sentinel.SentinelJUnitStopException;
import org.sentinel.SentinelRuntimeException;
import org.sentinel.log.Logger;

public class ListenerTest
{
    
    public static final int DEFAULT_PORT = 4040;
    
    public static final int DEFAULT_HELLOWORLD_PORT = 4040;
    
    public static final int DEFAULT_HTTP_PORT = 4041;
    
    private Class protocol = org.sentinel.servers.helloworld.Protocol.class;
    
    class ListenerMock extends Listener
    {

        public ListenerMock(int port, Class protocol)
        {
            super(port, protocol);
        }

        @Override
        public void init() throws SentinelException
        {
            // do nothing except trick the later steps into thinking the socket has been initialised
            initialized = true;
        }
        
    }

    @Test
    public void testServersClashPort() throws Exception
    {
        Listener listener1 = null, listener2 = null;
        try {
            listener1 = new Listener(1234, protocol);
            listener1.init();
            listener1.start();

            try {
                listener2 = new Listener(1234, protocol);
                listener2.init();
                listener2.start();
            }
            catch(SentinelException ex) {
                assertEquals("Address already in use", ex.getMessage());
            }
        }
        finally {
            if(listener1 != null) {
                listener1.stopGracefully();
            }
            if(listener2 != null) {
                listener2.stopGracefully();
            }
        }
    }
    
    @Test
    public void testUninitializedRun()
    {
        Listener listener1 = null;
        try {
            listener1 = new Listener(1234, protocol);
            listener1.run();
        }
        catch(SentinelRuntimeException ex) {
            assertEquals("Listener is not initialised, you must invoke init() before start().",
                ex.getMessage());
        }
        finally {
            if(listener1 != null) {
                listener1.stopGracefully();
            }
        }
    }
    
    @Test
    public void testBadSelect() throws SentinelException, IOException
    {
        ListenerMock mock = null;
        try {
            try {
                // init
                mock = new ListenerMock(DEFAULT_PORT,
                    org.sentinel.servers.http.protocol.Protocol.class);
                mock.init();

                // mock selector
                mock.selector = Mockito.mock(Selector.class);
                Mockito.when(mock.selector.select())
                    .thenThrow(new IOException("Failed to select."))
                    .thenThrow(new SentinelJUnitStopException());

                // run
                mock.run();

                fail("Should have failed.");
            }
            catch(SentinelJUnitStopException ex) {
                // validate
                assertEquals("Failed to select.", Logger.getLastMessage());
            }
        }
        finally {
            if(mock != null) {
                mock.stopGracefully();
            }
        }
    }
    
    class SocketChannelMock extends SocketChannel
    {

        public SocketChannelMock(SelectorProvider sp)
        {
            super(sp);
        }

        @Override
        public Socket socket()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isConnected()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isConnectionPending()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean connect(SocketAddress sa) throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean finishConnect() throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int read(ByteBuffer bb) throws IOException
        {
            throw new IOException("Bad read.");
        }

        @Override
        public long read(ByteBuffer[] bbs, int i, int i1) throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int write(ByteBuffer bb) throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long write(ByteBuffer[] bbs, int i, int i1) throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void implCloseSelectableChannel() throws IOException
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void implConfigureBlocking(boolean bln) throws IOException
        {
            // this is mocked to do nothing
        }
        
    }
    
    @Test
    public void testBadAccept() throws IOException
    {
        Listener listener = null;
        try {
            listener = new Listener(DEFAULT_PORT, org.sentinel.servers.http.Application.class);

            // mock ServerSocketChannel
            ServerSocketChannel ssc = Mockito.mock(ServerSocketChannel.class);
            Mockito.when(ssc.accept()).thenReturn(new SocketChannelMock(null));

            // mock SelectionKey
            SelectionKey selectionKey = Mockito.mock(SelectionKey.class);
            Mockito.when(selectionKey.channel()).thenReturn(ssc);

            try {
                listener.accept(selectionKey);
                fail();
            }
            catch(SentinelException ex) {
                // verify
                assertEquals("java.lang.InstantiationException: org.sentinel.servers.http.Application",
                    ex.getMessage());
            }
        }
        finally {
            if(listener != null) {
                listener.stopGracefully();
            }
        }
    }
    
    @Test
    public void testClientClosesConnection() throws SentinelException, IOException
    {
        Listener listener = null;
        try {
            listener = new Listener(DEFAULT_PORT,
                org.sentinel.servers.http.protocol.Protocol.class);
            listener.init();
            listener.start();
            
            org.sentinel.servers.http.Client client = new org.sentinel.servers.http.Client();
            client.setPort(DEFAULT_PORT);
            client.connect();
            client.getSocket().close();
        }
        finally {
            if(listener != null) {
                listener.stopGracefully();
            }
        }
    }
    
    @Test
    public void testBadRead1() throws SentinelException, IOException
    {
        Listener listener = null;
        try {
            listener = new Listener(DEFAULT_PORT, org.sentinel.servers.http.Application.class);
            listener.initialized = true;

            // mock ServerSocketChannel
            SocketChannel ssc = Mockito.mock(SocketChannel.class);
            Mockito.when(ssc.read((ByteBuffer) Mockito.anyObject())).thenReturn(-1);

            // mock SelectionKey
            SelectionKey selectionKey = Mockito.mock(SelectionKey.class);
            Mockito.when(selectionKey.channel()).thenReturn(ssc);
            Mockito.doNothing().when(selectionKey).cancel();

            // MockFinalMethod
            listener.mocker = Mockito.mock(MockFinalMethod.class);
            Mockito.doNothing().when(listener.mocker).closeSocketChannel(ssc);

            // run
            assertFalse(listener.read(selectionKey));
        }
        finally {
            if(listener != null) {
                listener.stopGracefully();
            }
        }
    }
    
    @Test
    public void testBadRead2() throws SentinelException, IOException
    {
        Listener listener = null;
        try {
            listener = new Listener(DEFAULT_PORT, org.sentinel.servers.http.Application.class);
            listener.initialized = true;

            // mock ServerSocketChannel
            SocketChannel ssc = Mockito.mock(SocketChannel.class);
            Mockito.when(ssc.read((ByteBuffer) Mockito.anyObject())).thenThrow(new IOException("Bad read."));

            // mock SelectionKey
            SelectionKey selectionKey = Mockito.mock(SelectionKey.class);
            Mockito.when(selectionKey.channel()).thenReturn(ssc);
            Mockito.doNothing().when(selectionKey).cancel();

            // MockFinalMethod
            listener.mocker = Mockito.mock(MockFinalMethod.class);
            Mockito.doNothing().when(listener.mocker).closeSocketChannel(ssc);

            // run
            assertFalse(listener.read(selectionKey));
        }
        finally {
            if(listener != null) {
                listener.stopGracefully();
            }
        }
    }
    
    @Test
    public void testStopGracefullyFail() throws SentinelException, IOException
    {
        Listener listener = null;
        try {
            listener = new Listener(DEFAULT_PORT, org.sentinel.servers.http.Application.class);
            listener.initialized = true;

            // mock ServerSocketChannel
            ServerSocketChannel ssc = Mockito.mock(ServerSocketChannel.class);
            Mockito.when(ssc.socket()).thenReturn(new ServerSocket());
            listener.serverChannel = ssc;
            
            // mocker
            listener.mocker = Mockito.mock(MockFinalMethod.class);
            Mockito.doThrow(new IOException("Cannot close socket.")).when(listener.mocker).closeServerSocket((ServerSocket) Mockito.anyObject());
            
            listener.stopGracefully();
            assertEquals("Cannot close socket.", Logger.getLastMessage());
        }
        finally {
            if(listener != null) {
                listener.stopGracefully();
            }
        }
    }
    
}
