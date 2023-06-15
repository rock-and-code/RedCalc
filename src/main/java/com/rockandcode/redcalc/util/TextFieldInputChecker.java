package com.rockandcode.redcalc.util;

import javafx.scene.control.Alert;

public class TextFieldInputValidator {
    private TextFieldInputValidator() {
    }

    public static void getPositiveDouble(double input, Alert alert, String errorMsg) {
        if (input < 0 || input == 0) {
            alert.setContentText(errorMsg);
            alert.show();
        }
    }
}
