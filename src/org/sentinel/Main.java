package org.sentinel;

import java.net.URL;

public class Main
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		URL resource = Main.class.getResource("/org/sentinel/configuration/default.xml");
		Sentinel s = new Sentinel(resource.getFile());
		s.run();
	}
    
}
