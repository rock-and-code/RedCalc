
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
        return new BedsAndZipcodeDTO(numBedsSpinner.getValue(), zipcodeTextField.getText());
    }

}
