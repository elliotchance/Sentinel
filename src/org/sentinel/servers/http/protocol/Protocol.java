package org.sentinel.servers.http.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import org.sentinel.SentinelException;
import org.sentinel.log.Logger;
import org.sentinel.server.SentinelProtocol;
import org.sentinel.servers.http.configuration.Application;
import org.sentinel.servers.http.configuration.Static;

public class Protocol extends SentinelProtocol
{
    
    protected HTTPRequest request = null;
    
    private char[] body = null;
    
    protected static String BASE_URL = "http://localhost";
    
    public static URL getURLFromPath(String path) throws SentinelException
    {
        try {
            return new URL(BASE_URL + path);
        }
        catch(MalformedURLException ex) {
            throw new SentinelException(ex.getMessage());
        }
    }

    @Override
    public void handleRead(SelectionKey key, byte[] data) throws IOException, SentinelException
    {
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(data));
        BufferedReader in = new BufferedReader(isr);
        String inputLine, headerLines = "";
        
        // read HTTP headers
        while((inputLine = in.readLine()) != null) {
            // if the line is blank this is the end of the headers
            if(inputLine.equals("")) {
                break;
            }
            
            headerLines += inputLine + "\n";
        }
        
        // parse HTTP headers
        request = new HTTPRequest();
        request.setHTTPHeaders(HTTPRequestHeaders.parse(headerLines));
        int contentLength = 0;
        try {
            contentLength = Integer.parseInt(request.getHTTPHeaders().get("content-length").getValue());
        }
        catch(NoSuchHTTPHeaderException ex) {
            // do nothing
        }
        
        // read body
        body = new char[contentLength];
        in.read(body);
        
        // process the actual request
        Logger.log(request.getHTTPHeaders().getPath());
        
        // build the URL
        URL pathUrl = getURLFromPath(request.getHTTPHeaders().getPath());
        String path = pathUrl.getPath(), query = pathUrl.getQuery();
        
        // find the application responsible for this endpoint
        org.sentinel.servers.http.configuration.Server config = getConfiguration();
        HTTPResponse response = null;
        for(Application app : config.getApplications()) {
            if(path.startsWith(app.getPrefix())) {
                // create request
                org.sentinel.servers.http.protocol.HTTPRequest srequest =
                    new org.sentinel.servers.http.protocol.HTTPRequest();
                
                srequest.setRequestURL(new org.sentinel.framework.URL(pathUrl));

                response = runApplication(key, app, request);
            }
        }
        
        // return 404
        if(response == null) {
            URL resource = getClass().getResource("/org/sentinel/servers/http/pages/404.html");
            String page = org.sentinel.framework.Application.getResource(resource);
            response = new HTTPResponse();
            response.getHTTPHeaders().setStatus(404);
            response.setBody(page.getBytes());
        }
        
        // done
        writeResult(key, response.getRawResponse());
    }
    
    protected void writeResult(SelectionKey key, String page) throws IOException
    {
        // send output
        key.interestOps(SelectionKey.OP_WRITE);
        ByteBuffer buf = ByteBuffer.wrap(page.getBytes());

        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.write(buf);
        socketChannel.close();
    }
    
    protected org.sentinel.servers.http.configuration.Server getConfiguration()
    {
        return (org.sentinel.servers.http.configuration.Server) configuration;
    }
    
    protected HTTPResponse runApplication(SelectionKey key, Application application,
        HTTPRequest request) throws SentinelException
    {
        HTTPResponse response = new HTTPResponse();

        try {
            org.sentinel.servers.http.Application app =
                (org.sentinel.servers.http.Application) application.getApplication().newInstance();
            
            // check for static content
            String staticPath = request.getHTTPHeaders().getPath(application);
            if(staticPath.length() > 1) {
                for(Static theStatic : application.getStatics()) {
                    String staticDir = theStatic.getPath().replace('.', '/');

                    // does the resource exist?
                    InputStream stream = getClass().getClassLoader().getResourceAsStream(staticDir + staticPath);
                    if(stream != null) {
                        byte[] buf = new byte[1024];
                        int len;
                        while((len = stream.read(buf)) > 0) {
                            response.write(buf, len);
                        }
                        return response;
                    }
                }
            }
            
            app.handleRequest(request, response);
            return response;
        }
        catch(Exception ex) {
            throw new SentinelException(ex.getMessage());
        }
    }
    
}
