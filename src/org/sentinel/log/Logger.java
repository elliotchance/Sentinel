package org.sentinel.log;

import java.util.Calendar;

public class Logger
{
    
    protected static String lastMessage = null;

    public static void log(String message)
    {
        lastMessage = message;
        System.out.println(Calendar.getInstance().getTime() + ": " + message);
    }
    
    public static void log(Exception ex)
    {
        log(ex.getMessage());
    }

    public static String getLastMessage()
    {
        return lastMessage;
    }
    
}
