package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.ui.App;
import java.io.IOException;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author riost02
 */
public class CalculateMortgagePaymentController {
    private final String PRIMARY = "main_screen";
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
        calcInterestBtn.setDisable(true);
        calcPmtBtn.setDisable(true);
        calcTermsBtn.setDisable(true);
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(PRIMARY);
    }

    @FXML
    public void calculatePMT() {
        // TODO add your handling code here:
        double principal = 0, terms = 0;
        float interest = 0;

        /* Variables declaration */
        try {
            principal = Double.parseDouble(principalField.getText());
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Principal field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }
        try {
            interest = ((Float.parseFloat(interestField.getText())) / 100);
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Interest field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();

        }
        try {
            terms = Integer.parseInt(termsField.getText()) * 12;
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Terms field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();

        }

        double PMT = (double) (principal * (interest / 12) * Math.pow((1 + (interest / 12)), terms) / (Math.pow((1 + (interest / 12)), terms) - 1));
        DecimalFormat df = new DecimalFormat("#,###,###.##");
        //String.format("%,.2f", amount)
        pmtField.setText(String.valueOf(PMT));

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
        String mortgagePrincipalText = principalField.getText().trim();
        String mortgageInterestRateText = interestField.getText();
        String mortgageTermsText = termsField.getText();
        String mortgagePmtText = pmtField.getText();

        boolean disableCalcPmtBtn = mortgagePrincipalText.isBlank()|| mortgageInterestRateText.isBlank() || mortgageTermsText.isBlank();
        boolean disableCalcInterestBtn = mortgagePrincipalText.isBlank() || mortgagePmtText.isBlank() || mortgageTermsText.isBlank();
        boolean disableCalcTermsBtn = mortgagePrincipalText.isBlank() || mortgagePmtText.isBlank() || mortgageInterestRateText.isBlank();

        calcPmtBtn.setDisable(disableCalcPmtBtn);
        calcInterestBtn.setDisable(disableCalcInterestBtn);
        calcTermsBtn.setDisable(disableCalcTermsBtn);
    }
    @FXML
    private void calculateInterest() {
        /* Variables declaration */

        double pmt = 0, interest = 0, principal = 0, x0 = 0.1, tolerance = 1e-6;
        int terms = 0, maxIterations = 1000000;
  
        try {
            principal = Double.parseDouble(principalField.getText());
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Principal field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();

        }
        try {
            pmt = (Double.parseDouble(pmtField.getText()));
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Pmt field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }
        try {
            terms = Integer.parseInt(termsField.getText()) * 12;
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Terms field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }

        interest = newtonRaphson(principal, pmt, terms, x0, tolerance, maxIterations);

        // Convert the monthly rate to an annual percentage rate
        double APR = 12 * interest * 100;

        DecimalFormat df = new DecimalFormat("#.##");

        interestField.setText(String.valueOf(APR));
    }
    
    @FXML
    private void calculateTerms() {
        /* Variables declaration */
        double principal = 0, pmt = 0;
        float interest = 0;

        try {
            principal = Double.parseDouble(principalField.getText());
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Principal field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }
        try {
            pmt = (Double.parseDouble(pmtField.getText()));
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Pmt field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }
        try {
            interest = Float.parseFloat(interestField.getText()) / 100;
        } catch (NumberFormatException e) {
            errorAlert = Alerts.getInstance().getErrorAlert("Interest field most contains a numberic value");
            errorAlert.initOwner(calculateMortgagePaymentBorderPane.getScene().getWindow());
            errorAlert.show();
        }

        int terms = (int) (Math.log10((pmt) / (pmt - ((interest / 12) * principal))) / (Math.log10(1 + (interest / 12))));

        terms /= 12;            //To transforms the terms into years

        termsField.setText(Integer.toString(terms));
    }

    // Define the function f(r) = PV - PMT * [(1 - (1 + r)^(-n)) / r]
    private static double f(double r, double PV, double PMT, int n) {
        return PV - PMT * ((1 - Math.pow(1 + r, -n)) / r);
    }

    // Define the derivative f'(r) = PMT * [(n * (1 + r)^(-n-1)) / r^2 - (1 - (1 + r)^(-n)) / r^2] 
    //                             = PMT * [(n - (1 - (1 + r)^(-n)) / r) / r^2]
    private static double df(double r, double PMT, int n) {
        return PMT * ((n - (1 - Math.pow(1 + r, -n)) / r) / Math.pow(r, 2));
    }

    // Implement the Newton-Raphson method
    private static double newtonRaphson(double principal, double pmt, int terms, double x0, double tolerance, int maxIterations) {
        double x = x0;
        double error = Double.POSITIVE_INFINITY;
        int iter = 0;

        while (error > tolerance && iter < maxIterations) {
            double f = f(x, principal, pmt, terms);
            double df = df(x, pmt, terms);
            double dx = f / df;
            x -= dx;
            error = Math.abs(dx / x);
            iter++;
        }

        if (iter == maxIterations) {
            System.out.println("Warning: Maximum number of iterations reached.");
        }

        return x;
    }
}
