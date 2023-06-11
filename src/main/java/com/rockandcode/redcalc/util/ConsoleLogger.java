
package com.rockandcode.redcalc.util;

public class ConsoleLogger {
    private static final ConsoleLogger instance = new ConsoleLogger();

    public static ConsoleLogger getInstance() {
        return instance;
    }
       
    public void printMessage(String message) {
        System.out.println(message);
    }
    
    public void printErrorMessage(String message, Exception e) {
        System.err.println(message + e.getMessage());
    }

}
