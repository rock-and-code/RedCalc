package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.ui.App;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author riost02
 */
public class CalculatePropertyNOIAndDebtServiceController {

    private final String PRIMARY = "main_screen";
    @FXML
    private TextField monthlyRent;
    @FXML
    private TextField vacancyRent;
    @FXML
    private TextField creditLossRate;
    @FXML
    private TextField capRateBDS;

    /* Hidden Lables and Buttons */
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

    /* Hidden Lables and Buttons */
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
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(PRIMARY);
    }

    @FXML
    private void calculatePropertyNOI() {
        /* Chaging alert message box font */
        errorAlert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: 'helvetica';");
        errorAlert.getDialogPane().setStyle("-fx-font-family: 'helvetica';");
        double rent = 0;
        float vacancyRt = 0, creditLossRt = 0, capRtBDS = 0;

        try {
            rent = Double.parseDouble(monthlyRent.getText()) * 12;
            if (rent < 0 || rent == 0) {
                errorAlert.setContentText("Monthly Rent value must greater than zero");
                errorAlert.show();
            }
        } catch (NumberFormatException e) {
            errorAlert.setContentText("Monthly rent must be a numberical value");
            errorAlert.show();
        }
        try {
            vacancyRt = (Float.parseFloat(vacancyRent.getText())) / 100;
        } catch (Exception e) {
            errorAlert.setContentText("Vacancy rate must be a numberical value");
            errorAlert.show();
        }
        try {
            creditLossRt = (Float.parseFloat(creditLossRate.getText())) / 100;
        } catch (Exception e) {
            errorAlert.setContentText("Credit loss rate must be a numberical value");
            errorAlert.show();
        }
        try {
            capRtBDS = (Float.parseFloat(capRateBDS.getText())) / 100;
            if (capRtBDS < 0 || capRtBDS == 0) {
                errorAlert.setContentText("Capital rate before debt service value must greater than zero");
                errorAlert.show();
            }
        } catch (Exception e) {
            errorAlert.setContentText("Capital rate before debt service must be a numberical value");
            errorAlert.show();
        }

        if ((rent > 0) && (capRtBDS > 0)) {
            /* Rounding up EGI, NOI and Optimal List Price numbers*/
            double eGI = rent * (1 - (vacancyRt + creditLossRt));
            Double egiTruncated = BigDecimal.valueOf(eGI).setScale(2, RoundingMode.HALF_UP).doubleValue();

            double nOI = (float) eGI * 0.5;
            Double noiTruncated = BigDecimal.valueOf(nOI).setScale(2, RoundingMode.HALF_UP).doubleValue();

            double optimalLP = nOI / capRtBDS;
            Double optimalLPTruncated = BigDecimal.valueOf(optimalLP).setScale(2, RoundingMode.HALF_UP).doubleValue();

            /* Formatting in US currency */
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            /* Populating EFI and NOI text fields */
            effectiveGrossIncome.setText(fmt.format(egiTruncated));
            netOperatingIncome.setText(fmt.format(noiTruncated));
            optimalListPrice.setText(fmt.format(optimalLPTruncated));

            /* Set calc fields visible */
            effectiveGrossIncomeLabel.setVisible(true);
            netOperatingIncomeLabel.setVisible(true);
            optimalListPriceLabel.setVisible(true);
            effectiveGrossIncome.setVisible(true);
            netOperatingIncome.setVisible(true);
            optimalListPrice.setVisible(true);
        }
    }

    @FXML
    private void calculatePIExp() {
        /* Chaging alert message box font */
        errorAlert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: 'helvetica';");
        errorAlert.getDialogPane().setStyle("-fx-font-family: 'helvetica';");

        double sellingPrice = 0;
        float downPaymentPer = 0;

        try {
            sellingPrice = Double.parseDouble(listPrice.getText());
            if (sellingPrice == 0 || sellingPrice < 0) {
                errorAlert.setContentText("List price value must be greater than zero");
                errorAlert.show();
            }

        } catch (Exception e) {
            errorAlert.setContentText("List price value must be a numerical value");
            errorAlert.show();
        }
        try {
            downPaymentPer = (Float.parseFloat(downPaymentPercentage.getText())) / 100;
            if (downPaymentPer == 0 || downPaymentPer < 0) {
                errorAlert.setContentText("Down payment percentage value must be greater than zero");
                errorAlert.show();
            }
        } catch (Exception e) {
            errorAlert.setContentText("Down payment percentage value must be a numerical value");
            errorAlert.show();
        }

        if (sellingPrice > 0 || downPaymentPer > 0) {
            /* Rounding up EGI, NOI and Optimal List Price numbers*/
            double financedAmount = (double) sellingPrice * (1 - (downPaymentPer));
            Double financedAmountTruncated = BigDecimal.valueOf(financedAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

            double mortgageInterestRt = 0, mortgageTrms = 0;

            try {
                mortgageInterestRt = (Double.parseDouble(mortgageInterestRate.getText()) / 100) / 12;
                if (mortgageInterestRt < 0 || mortgageInterestRt == 0) {
                    errorAlert.setContentText("Interest rate value must be greater than zero");
                    errorAlert.show();
                }

            } catch (Exception e) {
                errorAlert.setContentText("Interest rate value must be a numerical value");
                errorAlert.show();
            }
            try {
                mortgageTrms = Double.parseDouble(mortgageTerms.getText()) * 12;
                if (mortgageTrms < 0 || mortgageTrms == 0) {
                    errorAlert.setContentText("Mortgage terms must be greater than zero");
                    errorAlert.show();
                }
            } catch (Exception e) {
                errorAlert.setContentText("Mortgage terms must be a numerical value");
                errorAlert.show();
            }

            if ((mortgageInterestRt > 0) && (mortgageTrms > 0)) {
                /* Calculating Mortgage Payments */
                double mortgagePayments = (financedAmount * Math.pow((1 + mortgageInterestRt), mortgageTrms) * mortgageInterestRt)
                        / (Math.pow((1 + mortgageInterestRt), mortgageTrms) - 1);
                double propertyNOIAmount = 0;
                try {
                    propertyNOIAmount = Double.parseDouble(propertyNOI.getText());
                    if ((propertyNOIAmount < 0) || (propertyNOIAmount == 0)) {
                        errorAlert.setContentText("Property NOI value must be greater than zero");
                        errorAlert.show();
                    }
                } catch (Exception e) {
                    errorAlert.setContentText("Property NOI must be a numerical value");
                    errorAlert.show();
                }

                if (propertyNOIAmount > 0) {
                    double annualPrincipalAndInterestExp = mortgagePayments * 12;
                    Double annualPrincipalAndInterestExpTruncated = BigDecimal.valueOf(annualPrincipalAndInterestExp).setScale(2, RoundingMode.HALF_UP).doubleValue();

                    /* Calculating Property's NOI after debt service */
                    double propertyNOIAfterDebtService = propertyNOIAmount - annualPrincipalAndInterestExpTruncated;
                    Double propertyNOIAfterDebtServiceTruncated = BigDecimal.valueOf(propertyNOIAfterDebtService).setScale(2, RoundingMode.HALF_UP).doubleValue();

                    double capRateADS = propertyNOIAfterDebtService / (sellingPrice * 0.2);
                    capRateADS *= 100;
                    Double capRateADSTruncated = BigDecimal.valueOf(capRateADS).setScale(2, RoundingMode.HALF_UP).doubleValue();

                    /* Formatting in US currency */
                    Locale locale = new Locale("en", "US");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                    /* Populating EFI and NOI text fields */
                    principalAndInterestExp.setText(fmt.format(annualPrincipalAndInterestExpTruncated));
                    noiAfterDebtService.setText(fmt.format(propertyNOIAfterDebtServiceTruncated));
                    capitalRateADS.setText(Double.toString(capRateADSTruncated) + "%");


                    /* Set calc fields visible */
                    principalAndInterestExpLabel.setVisible(true);
                    principalAndInterestExp.setVisible(true);
                    noiAfterDebtServiceLabel.setVisible(true);
                    noiAfterDebtService.setVisible(true);
                    capitalRateADSLabel.setVisible(true);
                    capitalRateADS.setVisible(true);
                }
            }
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
        String vacancyRtText = vacancyRent.getText();
        String creditLossRtText = creditLossRate.getText();
        String capRateBDSText = capRateBDS.getText();

        boolean disableButtons = rentText.isEmpty() || rentText.trim().isEmpty() || vacancyRtText.isEmpty() || vacancyRtText.trim().isEmpty()
                || creditLossRtText.isEmpty() || creditLossRtText.trim().isEmpty() || capRateBDSText.isEmpty() || capRateBDSText.trim().isEmpty();

        calcBtnOne.setDisable(disableButtons);
    }

    @FXML
    public void resetFields() {
        monthlyRent.setText("");
        vacancyRent.setText("");
        creditLossRate.setText("");
        capRateBDS.setText("");

        /* Hidde calculations Labels and Text fields */
        effectiveGrossIncomeLabel.setVisible(false);
        netOperatingIncomeLabel.setVisible(false);
        optimalListPriceLabel.setVisible(false);
        effectiveGrossIncome.setVisible(false);
        netOperatingIncome.setVisible(false);
        optimalListPrice.setVisible(false);

        calcBtnOne.setDisable(true);
    }

    /* Caculating property's Debt Service UI controls */
    @FXML
    public void handleKeyReleasedTwo() {
        String listPriceText = listPrice.getText();
        String downPaymentPercentageText = downPaymentPercentage.getText();
        String mortgageInterestRateText = mortgageInterestRate.getText();
        String mortgageTermsText = mortgageTerms.getText();
        String propertyNOIText = propertyNOI.getText();

        boolean disableButtons = listPriceText.isEmpty() || listPriceText.trim().isEmpty() || downPaymentPercentageText.isEmpty() || downPaymentPercentageText.trim().isEmpty()
                || mortgageInterestRateText.isEmpty() || mortgageInterestRateText.trim().isEmpty() || mortgageTermsText.isEmpty() || mortgageTermsText.trim().isEmpty()
                || propertyNOIText.isEmpty() || propertyNOIText.trim().isEmpty();

        calcBtnTwo.setDisable(disableButtons);
    }

    @FXML
    public void resetFieldsTwo() {
        listPrice.setText("");
        downPaymentPercentage.setText("");
        mortgageInterestRate.setText("");
        mortgageTerms.setText("");
        propertyNOI.setText("");

        /* Hidde calculations Labels and Text fields */
        principalAndInterestExpLabel.setVisible(false);
        principalAndInterestExp.setVisible(false);
        capitalRateADSLabel.setVisible(false);
        capitalRateADS.setVisible(false);

        calcBtnTwo.setDisable(true);
    }
}
