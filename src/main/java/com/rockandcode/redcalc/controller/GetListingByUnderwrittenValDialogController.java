

package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.BedsBathsAndCapRateDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 *
 * @author riost02
 */
public class GetListingByZipcodeAndUnderwrittenValDialogController {
    @FXML
    private Spinner<Integer> numBedsSpinner;
    @FXML
    private Spinner<Double> numBathsSpinner;
    @FXML
    private TextField capRateTextField;
    
    @FXML
    public BedsBathsAndCapRateDTO getBedsBathsAndCapRate() {
        return new BedsBathsAndCapRateDTO(numBedsSpinner.getValue(), numBathsSpinner.getValue(), capRateTextField.getText());
    }
}
