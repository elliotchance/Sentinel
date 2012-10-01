package org.sentinel.server;

import java.io.IOException;
import java.net.Socket;

public interface SentinelProtocol
{

    SentinelRequest handleRawRequest(final Socket socket) throws IOException;
    
}
