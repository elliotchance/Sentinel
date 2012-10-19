package org.sentinel.test.bench;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import org.junit.Test;

public class SimpleAppTest
{
    
    class Client extends Thread
    {
        
        public long time = 0;
        
        public int count = 0;

        public Client(int count)
        {
            this.count = count;
        }

        @Override
        public void run()
        {
            try {
                long start = Calendar.getInstance().getTimeInMillis();
                for(int i = 0; i < count; ++i) {
                    byte[] result = SimpleAppTest.downloadPage();
                    //System.out.println(new String(result));
                }
                time = (Calendar.getInstance().getTimeInMillis() - start);
                //System.out.println("Time: " + time + "ms");
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }

    @Test
    public void testHandleRequest() throws Exception
    {
        int totalClients = 20;
        int totalRequests = 250;
        
        Client[] clients = new Client[totalClients];
        for(int i = 0; i < clients.length; ++i) {
            clients[i] = new Client(totalRequests);
            clients[i].start();
        }
        
        // get totals
        int total = 0;
        for(int i = 0; i < clients.length; ++i) {
            clients[i].join();
            total += clients[i].time;
        }
        System.out.println("Total Time: " + (total / totalClients) + " ms -> " +
            (totalRequests * totalClients) / (total / totalClients * 1.0) * 1000.0 + " ms/request");
    }
    
    protected static byte[] downloadPage() throws Exception
    {
        URL url = new URL("http://localhost:4041/simpleapp?name=Bob");
        url.openConnection();
        InputStream reader = url.openStream();
        byte[] buffer = new byte[1000];
        reader.read(buffer);
        return buffer;
    }

}
