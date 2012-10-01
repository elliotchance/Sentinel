package org.sentinel.servers.http;

public enum HTTPStatusCode
{
    
    HTTP_200(200, "OK"),
    HTTP_302(302, "Found");
    
    protected int statusCode;
    
    protected String statusMessage;

    HTTPStatusCode(int statusCode, String statusMessage)
    {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }

    @Override
    public String toString()
    {
        return statusCode + " " + statusMessage;
    }
    
    public static HTTPStatusCode valueOf(int statusCode) throws NoSuchHTTPStatusCodeException
    {
        HTTPStatusCode[] codes = HTTPStatusCode.values();
        for(HTTPStatusCode sc : codes) {
            if(sc.getStatusCode() == statusCode) {
                return sc;
            }
        }
        throw new NoSuchHTTPStatusCodeException(String.valueOf(statusCode));
    }
    
}
