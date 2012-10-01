package org.sentinel.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.println("handleRawRequest: " + request);
        
        // the now parsed request can be sent to the real server
        SentinelResponse response = server.handleRequest(request);
        System.out.println("handleRequest: " + response);
        
        // the rendered response goes back to the client
        OutputStream out = clientSocket.getOutputStream();
        out.write(response.getRawResponse().getBytes());
        out.close();
        System.out.println("write: " + response.getRawResponse());
        
        // close
        clientSocket.close();
        System.out.println("close: " + clientSocket);
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
        System.out.println("waiting for accept: " + serverSocket);
        try {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            }
            catch(SocketTimeoutException ex) {
                return true;
            }

            System.out.println("accept: " + clientSocket);
            handleClient(clientSocket);
            System.out.println("finish: " + clientSocket);
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
            System.out.println("Thread finished.");
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
