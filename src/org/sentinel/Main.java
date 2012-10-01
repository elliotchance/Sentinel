package org.sentinel;

import java.net.URL;
import org.sentinel.configuration.Configuration;

public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Main main = new Main();
        main.start();
    }

    public void start() throws Exception
    {
        URL resource = Main.class.getResource(Configuration.DEFAULT_CONFIGURATION);
        Sentinel s = new Sentinel(resource.getFile());
        s.run();
    }
    
}
