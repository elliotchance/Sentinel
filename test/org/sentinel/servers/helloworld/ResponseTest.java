package org.sentinel.servers.helloworld;

import org.junit.Test;

public class ResponseTest
{
    
    @Test
    public void testWrite()
    {
        Response response = new Response("abc");
        response.write("something");
    }

}
