package org.sentinel.servers.helloworld;

import java.io.IOException;
import java.net.Socket;
import org.sentinel.server.SentinelProtocol;
import org.sentinel.server.SentinelRequest;

public class Protocol implements SentinelProtocol
{

    @Override
    public SentinelRequest handleRawRequest(final Socket socket) throws IOException
    {
        // totally ignore any input sent
        return new Request();
    }
    
}
