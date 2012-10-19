package org.sentinel.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import org.sentinel.SentinelException;
import org.sentinel.SentinelJUnitStopException;
import org.sentinel.SentinelRuntimeException;
import org.sentinel.configuration.ConfigurationNode;
import org.sentinel.log.Logger;

public class Listener extends Thread
{

    /**
     * The port number this listener is listening on.
     */
    private int port;
    
    /**
     * The protocol class initialized when a new accepted connection.
     */
    private Class protocol;
    
    protected boolean initialized = false;
    
    protected volatile boolean threadRunning = true;
    
    protected ConfigurationNode configuration;
    
    protected ServerSocketChannel serverChannel;
    
    protected Selector selector;
    
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    
    protected MockFinalMethod mocker = new MockFinalMethod();

    public Listener(int port, Class protocol)
    {
        this.port = port;
        this.protocol = protocol;
    }
    
    public void init() throws SentinelException
    {
        try {
            // create a new selector
            selector = SelectorProvider.provider().openSelector();

            // create a new non-blocking server socket channel
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            // bind the server socket to the specified address and port
            InetSocketAddress isa = new InetSocketAddress((InetAddress) null, port);
            serverChannel.socket().bind(isa);

            // register the server socket channel, indicating an interest in accepting new
            // connections
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch(IOException ex) {
            throw new SentinelException(ex.getMessage());
        }
        initialized = true;
    }

    @Override
    public void run()
    {
        if(!initialized) {
            throw new SentinelRuntimeException("Listener is not initialised, you must invoke " +
                "init() before start().");
        }

        while(threadRunning) {
            try {
                // wait for an event one of the registered channels
                selector.select();

                // iterate over the set of keys for which events are available
                Iterator selectedKeys = selector.selectedKeys().iterator();
                while(selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    // check what event is available and deal with it
                    if(key.isAcceptable()) {
                        accept(key);
                    }
                    else if(key.isReadable()) {
                        read(key);
                    }
                }
            }
            catch(SentinelJUnitStopException ex) {
                throw ex;
            }
            catch(Exception e) {
                Logger.log(e);
            }
        }
    }
    
    protected boolean read(SelectionKey key) throws IOException, SentinelException
    {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // clear out our read buffer so it's ready for new data
        readBuffer.clear();

        // attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(readBuffer);
        }
        catch(IOException e) {
            // the remote forcibly closed the connection, cancel the selection key and close the
            // channel
            key.cancel();
            mocker.closeSocketChannel(socketChannel);
            return false;
        }

        if(numRead == -1) {
            // remote entity shut the socket down cleanly. Do the same from our end and cancel the 
            // channel
            key.cancel();
            mocker.closeSocketChannel(socketChannel);
            return false;
        }

        // hand the data off to our worker thread
        SentinelProtocol connection = (SentinelProtocol) key.attachment();
        connection.handleRead(key, readBuffer.array());
        return true;
    }
    
    protected void accept(SelectionKey key) throws IOException, SentinelException
    {
        // for an accept to be pending the channel must be a server socket channel.
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        // accept the connection on a new thread
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        try {
            // initialise protocol
            SentinelProtocol theProtocol = (SentinelProtocol) protocol.newInstance();
            theProtocol.setConfiguration(configuration);

            // register the new SocketChannel with our Selector, indicating we'd like to be notified
            // when there's data waiting to be read
            socketChannel.register(selector, SelectionKey.OP_READ, theProtocol);
        }
        catch(Exception ex) {
            throw new SentinelException(ex);
        }
    }
    
    public void stopGracefully()
    {
        try {
            threadRunning = false;
            if(serverChannel != null) {
                ServerSocket socket = serverChannel.socket();
                if(socket != null) {
                    mocker.closeServerSocket(socket);
                }
            }
            Thread.currentThread().interrupt();
        }
        catch(IOException ex) {
            Logger.log(ex);
        }
    }

    public void setConfiguration(ConfigurationNode configuration)
    {
        this.configuration = configuration;
    }
    
}
