
package com.rockandcode.redcalc.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DownloadRentListingDialogController {

    @FXML
    private ComboBox stateComboBox;
    @FXML
    private TextField cityTextField;

    @FXML
    public Map<String, String> getNewListingInfo() throws RuntimeException {
        //Validating input 
        String cityValue = cityTextField.getText().trim();
        String stateValue = stateComboBox.getValue().toString().trim();
        
        if(cityValue.isBlank()) {
            throw new RuntimeException("City field is required");
        } 
        if (stateValue.isBlank()) {
            throw new RuntimeException("State field is required");
        }
        // Creates a new HashMap and adds the city and state values to it.
        return new HashMap<>() {{
            put("city", cityValue);
            put("state", stateValue);
        }};
        

    }

}
