package org.sentinel.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import org.sentinel.SentinelException;
import org.sentinel.configuration.ConfigurationNode;

public abstract class SentinelProtocol
{
    
    protected ConfigurationNode configuration = null;

    public void setConfiguration(ConfigurationNode configuration)
    {
        this.configuration = configuration;
    }

    public abstract void handleRead(SelectionKey key, byte[] data) throws IOException,
        SentinelException;
    
}
