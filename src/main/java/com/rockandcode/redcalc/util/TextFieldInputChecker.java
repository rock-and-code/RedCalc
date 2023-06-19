package com.rockandcode.redcalc.util;

import javafx.scene.control.Alert;

public class TextFieldInputChecker {
    private TextFieldInputChecker() {
    }

    // Checks if the given double value is positive.
    // If not, an error message is displayed in the given alert.
    public static void isPositiveDouble(double input, Alert alert, String errorMsg) {
        if (input < 0 || input == 0) {
            alert.setContentText(errorMsg);
            alert.show();
        }
    }
    // Checks if the given integer value is positive.
    // If not, an error message is displayed in the given alert.
    public static void isPositiveInteger(int input, Alert alert, String errorMsg) {
        if (input < 0 || input == 0) {
            alert.setContentText(errorMsg);
            alert.show();
        }
    }
}
