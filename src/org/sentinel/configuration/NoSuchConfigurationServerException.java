package org.sentinel.configuration;

public class NoSuchConfigurationServerException extends ConfigurationException
{

    public NoSuchConfigurationServerException(String name)
    {
        super("No such configuration server '" + name + "'");
    }
}
