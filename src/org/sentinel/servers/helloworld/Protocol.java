package org.sentinel.servers.helloworld;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import org.sentinel.server.SentinelProtocol;

public class Protocol extends SentinelProtocol
{

    @Override
    public void handleRead(SelectionKey key, byte[] data) throws IOException
    {
        // totally ignore any input sent
        
        key.interestOps(SelectionKey.OP_WRITE);

        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.write(ByteBuffer.wrap("Hello, World!\n".getBytes()));
        socketChannel.close();
    }
    
}
