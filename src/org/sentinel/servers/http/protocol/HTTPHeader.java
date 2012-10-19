package org.sentinel.servers.http.protocol;

public class HTTPHeader
{
    
    protected String name;
    
    protected String value;
    
    public HTTPHeader(String raw)
    {
        int pos = raw.indexOf(':');
        setName(raw.substring(0, pos));
        setValue(raw.substring(pos + 1).trim());
    }

    public HTTPHeader(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public HTTPHeader(String name, int value)
    {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public final void setValue(String value)
    {
        this.value = value;
    }

    public final void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name + ": " + value;
    }
    
}
