package org.sentinel.framework;

import java.net.MalformedURLException;
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;

public class URLTest
{

    @Test
    public void testQueryParameters() throws MalformedURLException
    {
        URL url = new URL("http://localhost/abc?a=1&b=2&c=3");
        HashMap<String, String> params = url.getQueryParameters();
        assertEquals(params.get("a"), "1");
        assertEquals(params.get("b"), "2");
        assertEquals(params.get("c"), "3");
    }

    @Test
    public void testMissingQueryParameters1() throws MalformedURLException
    {
        URL url = new URL("http://localhost/abc");
        HashMap<String, String> params = url.getQueryParameters();
        assertEquals(params.size(), 0);
    }

    @Test
    public void testMissingQueryParameters2() throws MalformedURLException
    {
        URL url = new URL("http://localhost/abc?");
        HashMap<String, String> params = url.getQueryParameters();
        assertEquals(params.size(), 0);
    }

    @Test
    public void testQueryParameter() throws MalformedURLException
    {
        URL url = new URL("http://localhost/abc?a=1&b=2&c=3");
        assertEquals(url.getQueryParameter("a"), "1");
        assertEquals(url.getQueryParameter("abc"), null);
        assertEquals(url.getQueryParameter("a", "123"), "1");
        assertEquals(url.getQueryParameter("abc", "123"), "123");
    }

}
