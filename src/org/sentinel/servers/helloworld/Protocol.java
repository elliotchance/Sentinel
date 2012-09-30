package org.sentinel.servers.helloworld;

import org.sentinel.server.SentinelProtocol;
import org.sentinel.server.SentinelRequest;

public class Protocol implements SentinelProtocol
{

    @Override
    public SentinelRequest handleRawRequest(final String input)
    {
        // since we don't care about what input we get the SentinelRequest can effectivly do nothing
        return new SentinelRequest();
    }
}
