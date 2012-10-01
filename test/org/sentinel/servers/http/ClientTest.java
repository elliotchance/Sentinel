package org.sentinel.servers.http;

import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.sentinel.client.ClientException;

public class ClientTest
{

    @Test
    public void testSendRawRequest()
    {
        try {
            Socket mockSocket = Mockito.mock(Socket.class);
            Mockito.when(mockSocket.getOutputStream()).thenThrow(new IOException("Mocked"));

            Client client = new Client();
            client.setSocket(mockSocket);
            client.sendRawRequest(new byte[] {});
        }
        catch(ClientException ex) {
            assertEquals("Mocked", ex.getMessage());
        }
        catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

}
