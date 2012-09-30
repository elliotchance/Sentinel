package org.sentinel.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread
{

    private int port;
    
    private SentinelProtocol protocol;
    
    private SentinelServer server;

    public Listener(int port, SentinelProtocol protocol, SentinelServer server)
    {
        this.port = port;
        this.protocol = protocol;
        this.server = server;
    }

    public void handleClient(Socket clientSocket) throws IOException
    {
        OutputStream out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();
        String inputLine, outputLine;

        // read all input
        String input = "";
        while(in.available() > 0) {
            input += (char) in.read();
        }
        
        // send the output to the protocol
        SentinelRequest request = protocol.handleRawRequest(input);
        
        // the now parsed request can be sent to the real server
        SentinelResponse response = server.handleRequest(request);
        
        // the rendered response goes back to the client
        out.write(response.getRawResponse().getBytes());
        out.flush();
    }

    @Override
    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
                catch(IOException e) {
                    System.out.println("Accept failed: " + port);
                }
            }
        }
        catch(IOException e) {
            System.out.println("Could not listen on port: " + port);
        }
    }
}
