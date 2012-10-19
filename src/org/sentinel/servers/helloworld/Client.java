package org.sentinel.servers.helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.sentinel.client.ClientException;

public class Client extends org.sentinel.client.Client
{

    @Override
    public byte[] sendRawRequest(byte[] data) throws ClientException
    {
        try {
            // this is a really simple client, we send the data to the server even though it ignores
            // it
            OutputStream out = socket.getOutputStream();
            out.write(data);
            out.flush();
            
            // now read the result, even though we know what the result is going to be
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String output = "", inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                output += inputLine + "\n";
            }
            
            return output.getBytes();
        }
        catch(IOException ex) {
            throw new ClientException(ex.getMessage());
        }
    }

}
