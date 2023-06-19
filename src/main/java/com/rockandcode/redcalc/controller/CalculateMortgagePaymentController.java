package com.rockandcode.redcalc.controller;

import java.io.IOException;
import java.text.DecimalFormat;

import com.rockandcode.redcalc.ui.App;

import com.rockandcode.redcalc.util.TextFieldInputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class CalculateMortgagePaymentController {
    @FXML
    private BorderPane calculateMortgagePaymentBorderPane;
    @FXML
    private TextField principalField;
    @FXML
    private TextField interestField;
    @FXML
    private TextField termsField;
    @FXML
    private TextField pmtField;
    @FXML
    private Button calcPmtBtn;
    @FXML
    private Button calcInterestBtn;
    @FXML
    private Button calcTermsBtn;
    @FXML
    private Alert errorAlert;

    @FXML
    public void initialize() {
        // Disable the calculate buttons until the user enters some values
        calcInterestBtn.setDisable(true);
        calcPmtBtn.setDisable(true);
        calcTermsBtn.setDisable(true);
    }
    
    @FXML
    private void switchToMainScreen() throws IOException {
        String CONTROLLER = "main_screen";
        App.setRoot(CONTROLLER);
    }

    /**
     * Calculates the monthly mortgage payment
     * @param principal A double representing the mortgage principal amount
     * @param interest A double representing the mortgage interest
     * @return An double representing the mortgage monthly payment
     */
    private double calculateMortgagePMT(double principal, double interest, int terms) {
        // Calculate the monthly interest rate
        double monthlyInterest = interest / 12 / 100;

        // Calculate the number of payments
        int numberOfPayments = terms * 12;

        // Calculate the monthly payment
        double monthlyPayment = principal * monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -numberOfPayments));

        return monthlyPayment;
    }

    /**
     * Calculates the number of years for the mortgage
     * @param principal A double representing the mortgage principal amount
     * @param pmt A double representing the mortgage monthly payment
     * @param interest A double representing the mortgage interest
     * @return An integer representing the mortgage terms
     */
    private int calculateMortgageYears(double principal, double pmt, double interest) {
        // Calculate the monthly interest rate
        double monthlyInterest = interest / 12 / 100;

        // Calculate the number of payments
        int numberOfPayments = (int) (Math.log10((pmt) / (pmt - (monthlyInterest * principal))) / (Math.log10(1 + monthlyInterest)));

        // Convert the number of payments to years
        int years = numberOfPayments / 12;

        return years;
    }

    @FXML
    public void calculatePMT() {
        // Get the user input values
        double principal = validateDoubleInputValue(principalField.getText(),
                errorAlert, "Principal field");

        double interest = validateDoubleInputValue(interestField.getText(),
                errorAlert, "Interest field");

        int terms = validateIntegerInputValue(termsField.getText(),
               errorAlert, "Terms field");

        // Checks if any of the user inputs is a positive numeric value, otherwise return
        if (principal < 0 || interest < 0 || terms < 0)
            return;

        // Calculate the monthly payment
        double monthlyPayment = calculateMortgagePMT(principal, interest, terms);

        // Set the monthly payment text field to the formatted value
        // No format on this value since this value may be used to calculate the
        // mortgage terms and any rounding will render an incorrect calculation on the
        // mortgagee terms value
        pmtField.setText(String.valueOf(monthlyPayment));


    }

    @FXML
    public void resetFields() {
        principalField.setText("");
        interestField.setText("");
        termsField.setText("");
        pmtField.setText("");

        /* Disable action button */
        calcPmtBtn.setDisable(true);
    }

    @FXML
    public void handleKeyReleased() {

        boolean disableCalcPmtBtn = principalField.getText().trim().isBlank()|| interestField.getText().trim().isBlank() || termsField.getText().trim().isBlank();
        boolean disableCalcInterestBtn = principalField.getText().trim().isBlank() || pmtField.getText().trim().isBlank() || termsField.getText().trim().isBlank();
        boolean disableCalcTermsBtn = principalField.getText().trim().isBlank() || pmtField.getText().trim().isBlank() || interestField.getText().trim().isBlank();

        calcPmtBtn.setDisable(disableCalcPmtBtn);
        calcInterestBtn.setDisable(disableCalcInterestBtn);
        calcTermsBtn.setDisable(disableCalcTermsBtn);
    }
    @FXML
    private void calculateInterest() {
        /* Variables declaration */

        double x0 = 0.1, tolerance = 1e-6;
        int maxIterations = 1000000;

        double principal = validateDoubleInputValue(principalField.getText(),
                    errorAlert, "Principal field");
        double pmt = validateDoubleInputValue(pmtField.getText(),
                errorAlert, "Payment field");
        int terms = validateIntegerInputValue(termsField.getText(),
                errorAlert, "Terms field");

        // Validating user input
        if (principal < 0 || pmt < 0 || terms < 0)
            return;

        terms *= 12; //Converting terms from year to months

        double interest = newtonRaphson(principal, pmt, terms, x0, tolerance, maxIterations);

        // Convert the monthly rate to an annual percentage rate
        double APR = 12 * interest * 100;

        DecimalFormat df = new DecimalFormat("#.##");

        //interestField.setText(String.valueOf(APR));
        interestField.setText(df.format(APR));
    }

    @FXML
    private void calculateTerms() {

        double principal = validateDoubleInputValue(principalField.getText(),
                errorAlert, "Principal field");
        double pmt = validateDoubleInputValue(pmtField.getText(),
                errorAlert, "Payment field");

        double interest = validateDoubleInputValue(interestField.getText(),
                    errorAlert, "Interest field");
        // Check if any of the user input was a positive numeric value, otherwise return
        if (principal < 0 || pmt < 0 || interest < 0)
            return;

        int terms = calculateMortgageYears(principal, pmt, interest);

        termsField.setText(Integer.toString(terms));
    }

    // Define the function f(r) = PV - PMT * [(1 - (1 + r)^(-n)) / r]
    private static double f(double r, double PV, double PMT, int n) {
        // Calculates the monthly payment for a mortgage with principal PV, monthly interest rate r, and number of payments n.
        return PV - PMT * ((1 - Math.pow(1 + r, -n)) / r);
    }

    // Define the derivative f'(r) = PMT * [(n * (1 + r)^(-n-1)) / r^2 - (1 - (1 + r)^(-n)) / r^2] 
    //                             = PMT * [(n - (1 - (1 + r)^(-n)) / r) / r^2]
    private static double df(double r, double PMT, int n) {
        // Calculates the derivative of the monthly payment function f(r).
        return PMT * ((n - (1 - Math.pow(1 + r, -n)) / r) / Math.pow(r, 2));
    }


    /**
     * Calculates the interest rate for a mortgage with principal, monthly payment pmt,
     * and number of payments terms, using the Newton-Raphson method.
     * @param principal A double representing the mortgage principal
     * @param pmt A double representing the mortgage monthly payment
     * @param terms A integer representing the mortgage terms
     * @param x0
     * @param tolerance
     * @param maxIterations
     * @return double representing the interest rate
     */
    private static double newtonRaphson(double principal, double pmt, int terms, double x0, double tolerance, int maxIterations) {
        double x = x0;
        double error = Double.POSITIVE_INFINITY;
        int iter = 0;

        while (error > tolerance && iter < maxIterations) {
            // Calculates the function f(x) and its derivative df(x).
            double f = f(x, principal, pmt, terms);
            double df = df(x, pmt, terms);

            // Updates the estimate of the interest rate x.
            double dx = f / df;
            x -= dx;

            // Calculates the absolute error between the current estimate of x and the previous estimate of x.
            error = Math.abs(dx / x);
            iter++;
        }

        // prints a warning message if the maximum number of iterations is reached
        if (iter == maxIterations) {
            System.out.println("Warning: Maximum number of iterations reached.");
        }
        // Returns the estimated interest rate.
        return x;
    }

    private double validateDoubleInputValue(String userInput, Alert errorAlert,
                                            String textFieldName) {
        // Validates that the user input is a valid double value.
        try {
            double value = Double.parseDouble(userInput);
            //Checks if the double value is positive, otherwise it displays an alert with an error message
            TextFieldInputChecker.isPositiveDouble(value,
                    errorAlert, textFieldName + " must be greater than zero");
            return value;
        } catch (Exception e) {
            errorAlert.setContentText(textFieldName + " must be a numeric value");
            errorAlert.show();
            return -1.0;
        }
    }

    private int validateIntegerInputValue(String userInput, Alert errorAlert,
                                            String textFieldName) {
        // Validates that the user input is a valid integer value.
        try {
            int value = Integer.parseInt(userInput);
            //Checks if the double value is positive, otherwise it displays an alert with an error message
            TextFieldInputChecker.isPositiveInteger(value,
                    errorAlert, textFieldName + " must be greater than zero");
            return value;
        } catch (Exception e) {
            errorAlert.setContentText(textFieldName + " must be a numeric value");
            errorAlert.show();
            return -1;
        }
    }
}
