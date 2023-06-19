package com.rockandcode.redcalc.util;

import javafx.scene.control.Alert;

public class Alerts {
    // The style for the alerts
    private static final String ALERT_STYLE = "-fx-font-family: helvetica;";

    // The Alerts instance
    private static final Alerts instance = new Alerts();

    // Returns the Alerts instance
    public static Alerts getInstance() {
        return instance;
    }

    // Creates an Alert with the ERROR type and the given message
    public Alert getInsertListingDialogNumericErrorAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Unable to add listing to the database: Some fields must contains numeric values only");
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the ERROR type and the given message
    public Alert getInsertListingDialogEmptyFieldsErrorAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Unable to add listing to the database: The text fields cannot be empty");
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the CONFIRMATION type and the given message
    public Alert getInsertListingDialogConfirmationAlert(String listingAttribute) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Listing (" + listingAttribute + ") has been added to the database");
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the ERROR type and the given message
    public Alert getInsertListingDialogFailedAlert(String listingAttribute) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Failed to add (" + listingAttribute + ") to the database");
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the CONFIRMATION type and the given message
    public Alert getConfirmationAlert(String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText(message);
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the INFORMATION type and the given message
    public Alert getInformationAlert(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

    // Creates an Alert with the ERROR type and the given message
    public Alert getErrorAlert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.getDialogPane().setStyle(ALERT_STYLE);
        return a;
    }

}
