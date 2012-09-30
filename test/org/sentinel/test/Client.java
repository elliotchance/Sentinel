package org.sentinel.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private InputStream inputStream;
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
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }
    
    protected void write(String string) throws IOException
    {
        outputStream.write(string.getBytes());
        outputStream.flush();
    }
    
    protected String read() throws IOException
    {
        String r = "";
        while(inputStream.available() > 0) {
            r += (char) inputStream.read();
        }
        return r;
    }

    public String sendRawRequest(String content) throws IOException
    {
        write(content);
        return read();
    }
}
