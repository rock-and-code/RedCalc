
package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.BedsAndZipcodeDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GetFairRentRateByZipcodeDialogController {
    @FXML
    private Spinner<Integer> numBedsSpinner;
    @FXML
    private TextField zipcodeTextField;
    
    @FXML
    public BedsAndZipcodeDTO getBedsAndZipcode() {
        // Gets the number of beds and zip code from the controls.
        int numBeds = numBedsSpinner.getValue();
        String zipcode = zipcodeTextField.getText();

        // Creates a new BedsAndZipcodeDTO object and returns it.
        return new BedsAndZipcodeDTO(numBeds, zipcode);
    }

}
