package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.ui.App;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.rockandcode.redcalc.util.TextFieldInputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class CalculatePropertyNOIAndDebtServiceController {

    private final String PRIMARY = "main_screen";
    private final DecimalFormat decimalFormat = new DecimalFormat("$###,###.##");
    @FXML
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    @FXML
    public BorderPane calculateNOIAndDebtServiceBorderPane;
    @FXML
    private TextField monthlyRent;
    @FXML
    private TextField vacancyRate;
    @FXML
    private TextField operatingExpRatio;
    @FXML
    private TextField creditLossRate;
    @FXML
    private TextField capRateBDS;
    /* Hidden Labels and Buttons */
    @FXML
    private Label effectiveGrossIncomeLabel;
    @FXML
    private Label netOperatingIncomeLabel;
    @FXML
    private Label optimalListPriceLabel;
    @FXML
    private Label effectiveGrossIncome;
    @FXML
    private Label netOperatingIncome;
    @FXML
    private Label optimalListPrice;
    @FXML
    private TextField listPrice;
    @FXML
    private TextField downPaymentPercentage;
    @FXML
    private TextField mortgageInterestRate;
    @FXML
    private TextField mortgageTerms;
    @FXML
    private TextField propertyNOI;
    /* Hidden Labels and Buttons */
    @FXML
    private Label principalAndInterestExpLabel;
    @FXML
    private Label capitalRateADSLabel;
    @FXML
    private Label noiAfterDebtServiceLabel;
    @FXML
    private Label noiAfterDebtService;
    @FXML
    private Label principalAndInterestExp;
    @FXML
    private Label capitalRateADS;
    @FXML
    private Button calcBtnOne;
    @FXML
    private Button calcBtnTwo;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(PRIMARY);
    }

    /*
     * Rounds a double to the specified number of digits.
     *
     * @param num A double.
     * @param numDigits The number of digits to round the double value.
     * @return The rounded double.
     */
    private double roundDouble(double num, int numDigits) {
        return BigDecimal.valueOf(num).setScale(numDigits, RoundingMode.HALF_UP).doubleValue();
    }

    /*
     * Calculates the effective gross income (EGI) for a property.
     *
     * @param monthlyRent The monthly rent for the property.
     * @param vacancyRent The monthly vacancy rate for the property.
     * @param creditLossRate The monthly credit loss rate for the property.
     * @return The EGI for the property.
     */
    private double calculateEGI(double monthlyRent, double vacancyRent, double creditLossRate) {
        return monthlyRent * (1 - (vacancyRent + creditLossRate));
    }

    /*
     * Calculates the net operating income (NOI) for a property.
     *
     * @param egi The effective gross income for the property.
     * @param oer The operating expense ratio for the property
     * @return The NOI for the property.
     */
    private double calculateNOI(double egi, double oer) {
        return egi * oer;
    }

    /*
     * Calculates the optimal list price for a property.
     *
     * @param noi The net operating income for the property.
     * @param capRateBeforeDebt The cap rate before rate for the property
     * @return The optimal list price for the property.
     */
    private double calculateOptimalListPrice(double noi, double capRateBeforeDebt) {
        return noi / capRateBeforeDebt;
    }

    /*
     * Calculates the monthly mortgage payment for a property.
     *
     * @param financedAmount The amount of money financed for the property.
     * @param mortgageInterestRate The mortgage interest rate.
     * @param mortgageTerms The number of years of the mortgage.
     * @return The principal and interest expenses for the property.
     */
    private double calculateMortgagePayment(double financedAmount, double mortgageInterestRate, double mortgageTerms) {
        return (financedAmount * Math.pow((1 + mortgageInterestRate), mortgageTerms) * mortgageInterestRate)
                / (Math.pow((1 + mortgageInterestRate), mortgageTerms) - 1);
    }

    /*
     * Calculates the property's NOI after debt service.
     *
     * @param propertyNOI The property's NOI.
     * @param principalAndInterestExpenses The property's principal and interest expenses.
     * @return The property's NOI after debt service.
     */
    private double calculateNOIAfterDebtService(double propertyNOI, double principalAndInterestExpenses) {
        return propertyNOI - principalAndInterestExpenses;
    }

    /*
     * Calculates the capital rate after debt service (CADS) for a property.
     *
     * @param noiAfterDebtService The property's NOI after debt service.
     * @param listPrice The list price of the property.
     * @return The CADS for the property.
     */
    private double calculateCADS(double noiAfterDebtService, double listPrice) {
        return noiAfterDebtService / (listPrice * 0.2);
    }

    /*
     * Calculates the monthly interest rate.
     *
     * @param mortgageInterestRate The text field for the mortgage annual interest rate.
     * @return The monthly interest rate for the mortgage.
     */
    private double calculateMonthlyInterestRate(TextField mortgageInterestRate) {
        return (Double.parseDouble(mortgageInterestRate.getText()) / 100) / 12;
    }

    /*
     * Calculates the list price amount to be financed.
     *
     * @param listPrice The property list price.
     * @param downPaymentPercentage The down payment percentage.
     * @return The list price amount to be financed.
     */
    private double calculateFinancedAmount(double listPrice, double downPaymentPercentage) {
        return (double) listPrice * (1 - (downPaymentPercentage));
    }

    @FXML
    private void calculatePropertyNOI() {

        /**
         * Validates user input
         * This function validates the user input for the monthly rent, vacancy rate,
         * credit loss rate, and cap rate before debt service.
         * If any of the inputs are invalid, an error message is displayed.
         * */
        double monthlyRent = validateDoubleInputValue(this.monthlyRent.getText(),
                errorAlert, "Monthly Rent");
        double vacancyRate = validateDoubleInputValue(this.vacancyRate.getText(),
                errorAlert, "Vacancy Rate");
        double oer = validateDoubleInputValue(this.operatingExpRatio.getText(),
                errorAlert, "Operating Expense Ratio");
        double creditLossRate = validateDoubleInputValue(this.creditLossRate.getText(),
                errorAlert, "Credit Loss Rate");
        double capRateBDS = validateDoubleInputValue(this.capRateBDS.getText(),
                errorAlert, "Cap Rate Before Debt");

        // Check if any of the users was a positive numeric value, otherwise return
        if (monthlyRent < 0 || vacancyRate < 0 || creditLossRate < 0 || capRateBDS < 0 ||
            creditLossRate < 0)
            return;

        monthlyRent *= 12; // Annualized monthly rent income
        vacancyRate /= 100; //Converting integer to decimal
        creditLossRate /= 100; //Converting integer to decimal
        oer = (1 - (oer / 100)); //Converting integer to decimal
        capRateBDS /= 100; //Converting integer to decimal

        // Calculate the EGI
        double egi = calculateEGI(monthlyRent, vacancyRate, creditLossRate);

        // Calculate the NOI
        double noi = calculateNOI(egi, oer);

        // Calculate the optimal list price
        double optimalListPrice = calculateOptimalListPrice(noi, capRateBDS);

        // Populate the text fields
        effectiveGrossIncome.setText(decimalFormat.format(egi));
        netOperatingIncome.setText(decimalFormat.format(noi));
        this.optimalListPrice.setText(decimalFormat.format(optimalListPrice));

        // Display results to user
        displayPropertyNOIResults();
    }

    /**
     * Set calculate principal and Interest expense results to user
     */
    private void displayPropertyNOIResults() {
        /* Set calc fields visible */
        effectiveGrossIncomeLabel.setVisible(true);
        netOperatingIncomeLabel.setVisible(true);
        optimalListPriceLabel.setVisible(true);
        effectiveGrossIncome.setVisible(true);
        netOperatingIncome.setVisible(true);
        this.optimalListPrice.setVisible(true);
    }

    @FXML
    private void calculatePIExp() {
        /**
         * Validates user input
         * This function validates the user input for the list price, down payment percentage,
         * mortgage interest rate, mortgage terms, and NOI.
         * If any of the inputs are invalid, an error message is displayed.
         * */
        double listPrice = validateDoubleInputValue(this.listPrice.getText(),
                errorAlert, "List Price");
        double downPaymentPercentage = validateDoubleInputValue(this.downPaymentPercentage.getText(),
                errorAlert, "Down Payment Percentage");
        double mortgageInterestRate = validateDoubleInputValue(this.mortgageInterestRate.getText(),
                errorAlert, "Mortgage Interest Rate");
        double mortgageTerms = validateDoubleInputValue(this.mortgageTerms.getText(),
                errorAlert, "Mortgage Terms");
        double noi = validateDoubleInputValue(propertyNOI.getText(),
                errorAlert, "NOI");

        // Check if any of the users was a positive numeric value, otherwise return
        if (listPrice < 0 || downPaymentPercentage < 0 || mortgageInterestRate < 0||
                mortgageTerms < 0 || noi < 0)
            return;

        mortgageInterestRate = ((mortgageInterestRate / 100) /12); //To convert first integer to decimal and then annual interest rate to a monthly basis
        mortgageTerms *= 12; //To convert the terms from year to months

        downPaymentPercentage /= 100; //To convert an integer to a decimal

        //Calculates the amount of the list price to be financed
        double financedAmount = calculateFinancedAmount(listPrice, downPaymentPercentage);

        // Calculating Mortgage Payments
        double mortgagePayments = calculateMortgagePayment(financedAmount, mortgageInterestRate, mortgageTerms);

        // Calculates the annual principal and interest expense
        double principalAndInterestExpenses = mortgagePayments * 12;

        // Calculate Property's NOI after debt service
        double noiAfterDebtService = calculateNOIAfterDebtService(noi, principalAndInterestExpenses);

        // Calculates cap rate after debt service
        double cads = calculateCADS(noiAfterDebtService, listPrice);
        cads *= 100;

        // Formats cap rate after debt to two decimal points
        double formattedCads = roundDouble(cads, 2);

        /* Populating EFI and NOI text fields */
        principalAndInterestExp.setText(decimalFormat.format(principalAndInterestExpenses));
        this.noiAfterDebtService.setText(decimalFormat.format(noiAfterDebtService));
        capitalRateADS.setText(Double.toString(formattedCads) + "%");

        // Display results
        displayCalculatePIExpResult();

    }

    /**
     * Display Principal and Interest Expense results to user
     * by revealing the result labels and text fields
     */
    private void displayCalculatePIExpResult() {
        principalAndInterestExpLabel.setVisible(true);
        principalAndInterestExp.setVisible(true);
        noiAfterDebtServiceLabel.setVisible(true);
        noiAfterDebtService.setVisible(true);
        capitalRateADSLabel.setVisible(true);
        capitalRateADS.setVisible(true);
    }

    private double validateDoubleInputValue(String userInput, Alert errorAlert,
                                            String textFieldName) {
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

    @FXML
    public void initialize() {
        calcBtnOne.setDisable(true);
        calcBtnTwo.setDisable(true);
    }

    /**
     * Check if any of the text fields is empty to disable button
     */
    @FXML
    public void handleKeyReleased() {
        String rentText = monthlyRent.getText();
        String vacancyRtText = vacancyRate.getText();
        String creditLossRtText = creditLossRate.getText();
        String operatingExpRtText = operatingExpRatio.getText();
        String capRateBDSText = capRateBDS.getText();

        boolean disableButtons = rentText.trim().isEmpty() || vacancyRtText.trim().isEmpty() ||
                creditLossRtText.trim().isEmpty() || operatingExpRtText.trim().isEmpty() || capRateBDSText.trim().isEmpty();

        calcBtnOne.setDisable(disableButtons);
    }

    @FXML
    public void resetFields() {
        // Resets the input fields and hides calculation labels and text fields.
        monthlyRent.setText("");
        vacancyRate.setText("");
        creditLossRate.setText("");
        capRateBDS.setText("");

        /* Hide calculations Labels and Text fields */
        effectiveGrossIncomeLabel.setVisible(false);
        netOperatingIncomeLabel.setVisible(false);
        optimalListPriceLabel.setVisible(false);
        effectiveGrossIncome.setVisible(false);
        netOperatingIncome.setVisible(false);
        optimalListPrice.setVisible(false);

        calcBtnOne.setDisable(true);
    }

    /**
     * Check if any of the text fields is empty to disable button
     */
    @FXML
    public void handleKeyReleasedTwo() {
        // Handles the key released event on text fields.
        String listPriceText = listPrice.getText();
        String downPaymentPercentageText = downPaymentPercentage.getText();
        String mortgageInterestRateText = mortgageInterestRate.getText();
        String mortgageTermsText = mortgageTerms.getText();
        String propertyNOIText = propertyNOI.getText();

        boolean disableButtons = listPriceText.trim().isEmpty() || downPaymentPercentageText.trim().isEmpty() ||
                mortgageInterestRateText.trim().isEmpty() || mortgageTermsText.trim().isEmpty() ||
                propertyNOIText.trim().isEmpty();

        calcBtnTwo.setDisable(disableButtons);
    }

    @FXML
    public void resetFieldsTwo() {
        // Resets the input fields and hides calculation labels and text fields for debt service.
        listPrice.setText("");
        downPaymentPercentage.setText("");
        mortgageInterestRate.setText("");
        mortgageTerms.setText("");
        propertyNOI.setText("");

        /* Hide calculations Labels and Text fields */
        principalAndInterestExpLabel.setVisible(false);
        principalAndInterestExp.setVisible(false);
        capitalRateADSLabel.setVisible(false);
        capitalRateADS.setVisible(false);

        calcBtnTwo.setDisable(true);
    }
}
