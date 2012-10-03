package org.sentinel;

import java.net.URL;

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
        String configFile = org.sentinel.configuration.Sentinel.DEFAULT_CONFIGURATION;
        URL resource = Main.class.getResource(configFile);
        Sentinel s = new Sentinel(resource.getFile());
        s.run();
    }
    
}
