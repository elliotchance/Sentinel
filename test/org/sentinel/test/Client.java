package org.sentinel.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client
{

    private String host;
    private int port;
    private int maxTimeout = 2000;
    private Socket socket = null;
    private BufferedReader inputStream;
    private OutputStream outputStream;

    public Client(String host, int port) throws UnknownHostException, SocketTimeoutException,
        IOException
    {
        this.host = host;
        this.port = port;
        connect();
    }

    private void connect() throws UnknownHostException, SocketTimeoutException, IOException
    {
        InetAddress addr = InetAddress.getByName(host);
        SocketAddress sockaddr = new InetSocketAddress(addr, port);

        // create the socket
        socket = new Socket();
        socket.connect(sockaddr, maxTimeout);
        outputStream = socket.getOutputStream();
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    protected void write(String string) throws IOException
    {
        outputStream.write(string.getBytes());
        outputStream.close();
    }
    
    protected String read() throws IOException
    {
        String r = "";
        while(true) {
            int ch = inputStream.read();
            if(ch < 0) {
                break;
            }
            r += (char) ch;
        }
        return r;
    }

    public String sendRawRequest(String content) throws IOException
    {
        if(content != null) {
            write(content);
        }
        return read();
    }
    
    public void close() throws IOException
    {
        outputStream.close();
        inputStream.close();
        socket.close();
    }
    
}
