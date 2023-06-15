

package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.BedsBathsAndCapRateDTO;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class GetListingByUnderwrittenValDialogController {
    @FXML
    public DialogPane getListingsByUnderwrittenValueDialogPane;
    @FXML
    private Spinner<Integer> numBedsSpinner;
    @FXML
    private Spinner<Double> numBathsSpinner;
    @FXML
    private TextField capRateTextField;
    
    @FXML
    public BedsBathsAndCapRateDTO getBedsBathsAndCapRate() {
        // Gets the number of beds, baths, and cap rate from the controls.
        int numBeds = numBedsSpinner.getValue();
        double numBaths = numBathsSpinner.getValue();
        String capRate = capRateTextField.getText();

        // Creates a new BedsBathsAndCapRateDTO object and returns it.
        return new BedsBathsAndCapRateDTO(numBeds, numBaths, capRate);
    }
}
