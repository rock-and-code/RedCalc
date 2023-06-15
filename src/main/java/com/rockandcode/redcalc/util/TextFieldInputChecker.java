package com.rockandcode.redcalc.util;

import javafx.scene.control.Alert;

public class TextFieldInputChecker {
    private TextFieldInputChecker() {
    }

    public static void isPositiveDouble(double input, Alert alert, String errorMsg) {
        if (input < 0 || input == 0) {
            alert.setContentText(errorMsg);
            alert.show();
        }
    }
}
