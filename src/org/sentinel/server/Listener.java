package org.sentinel.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.sentinel.SentinelException;
import org.sentinel.SentinelRuntimeException;

public class Listener extends Thread
{

    private int port;
    
    private SentinelProtocol protocol;
    
    private SentinelServer server;
    
    protected ServerSocket serverSocket;
    
    protected boolean initialized = false;
    
    private boolean threadRunning = true;

    public Listener(int port, SentinelProtocol protocol, SentinelServer server)
    {
        this.port = port;
        this.protocol = protocol;
        this.server = server;
    }

    public void handleClient(Socket clientSocket) throws IOException
    {
        // send the output to the protocol
        SentinelRequest request = protocol.handleRawRequest(clientSocket);
        
        // the now parsed request can be sent to the real server
        SentinelResponse response = server.handleRequest(request);
        
        // the rendered response goes back to the client
        OutputStream out = clientSocket.getOutputStream();
        out.write(response.getRawResponse().getBytes());
        out.close();
        System.out.println("write: " + response.getRawResponse());
        
        // close
        clientSocket.close();
    }
    
    public void init() throws SentinelException
    {
        try {
            serverSocket = new ServerSocket(port);
        }
        catch(IOException ex) {
            throw new SentinelException(ex.getMessage());
        }
        initialized = true;
    }
    
    protected boolean acceptConnection()
    {
        try {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            }
            catch(SocketTimeoutException ex) {
                return true;
            }

            handleClient(clientSocket);
            return true;
        }
        catch(IOException ex) {
            throw new SentinelRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void run()
    {
        if(!initialized) {
            throw new SentinelRuntimeException("Listener is not initialised, you must invoke " +
                "init() before start().");
        }

        try {
            serverSocket.setSoTimeout(100);
        }
        catch(SocketException ex) {
            throw new SentinelRuntimeException(ex.getMessage());
        }
        
        while(threadRunning) {
            if(!acceptConnection()) {
                break;
            }
        }
            
        try {
            serverSocket.close();
        }
        catch(IOException ex) {
            throw new SentinelRuntimeException(ex.getMessage());
        }
    }
    
    public void waitForThreadToJoin() throws InterruptedException
    {
        join();
    }
    
    public void stopGracefully()
    {
        try {
            threadRunning = false;
            interrupt();
            waitForThreadToJoin();
        }
        catch(InterruptedException ex) {
            throw new SentinelRuntimeException(ex.getMessage());
        }
    }
    
}
