package org.sentinel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

public class MockFinalMethod
{

    public void closeSocketChannel(SocketChannel sc) throws IOException
    {
        sc.close();
    }

    public void closeServerSocket(ServerSocket ss) throws IOException
    {
        ss.close();
    }
    
}
