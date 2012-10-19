package org.sentinel.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public abstract class Client
{

    protected String host = "localhost";
    protected int port = 0;
    protected int maxTimeout = 2000;
    protected Socket socket = null;
    
    public abstract byte[] sendRawRequest(byte[] data) throws ClientException;

    public void connect() throws UnknownHostException, SocketTimeoutException, IOException
    {
        InetAddress addr = InetAddress.getByName(host);
        SocketAddress sockaddr = new InetSocketAddress(addr, port);

        // create the socket
        socket = new Socket();
        socket.connect(sockaddr, maxTimeout);
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String sendRawRequest(String data) throws ClientException
    {
        byte[] response = sendRawRequest(data.getBytes());
        return new String(response);
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
    
}
