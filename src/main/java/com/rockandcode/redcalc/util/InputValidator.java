package com.rockandcode.redcalc.util;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Util class to support a command-line version of the app
 * Used only to test the Datasource class functionalities
 */
public class InputValidator {

    private final static InputValidator instance = new InputValidator();
    private Scanner scanner = new Scanner(System.in);
    public static InputValidator getInstance() {
        return instance;
    }
    
    public int getSelection() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter a number using only digits 0 to 9: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }
  

    public int getInteger() {
        ConsoleLogger.getInstance().printMessage("Enter an integer: ");
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter a number using only digits 0 to 9: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }
    
    public double getDouble() {
        ConsoleLogger.getInstance().printMessage("Enter an integer: ");
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter a number using only digits 0 to 9: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }

    public int getInteger(String message) {  //Easier to ask for forgiveness than permission
        ConsoleLogger.getInstance().printMessage(message);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter a " + message + " using only digits 0 to 9: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }
    
    public double getDouble(String message) {  //Easier to ask for forgiveness than permission
        ConsoleLogger.getInstance().printMessage(message);
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter a " + message + " using only digits 0 to 9: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }

    public String getString(String str) {  //Easier to ask for forgiveness than permission
        Scanner scanner = new Scanner(System.in);
        ConsoleLogger.getInstance().printMessage("Enter the " + str + ": ");
        while (true) {
            try {
                return scanner.nextLine();
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Please enter the " + str + " name using only text: ", e);
                scanner.nextLine();                                             //flushing the scanner buffer
            }
        }
    }


}
