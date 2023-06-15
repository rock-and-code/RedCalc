
package com.rockandcode.redcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class UpdateCityDialogController {
    @FXML
    private DialogPane updateCityDialogPane;
    @FXML
    private TextField newCityName;
    
    @FXML
    public String getNewCityName(){
        // Gets the new city name from the text field.
        //
        // Returns:
        // The new city name, trimmed of any leading or trailing whitespace.
        return newCityName.getText().trim();
    }
}
