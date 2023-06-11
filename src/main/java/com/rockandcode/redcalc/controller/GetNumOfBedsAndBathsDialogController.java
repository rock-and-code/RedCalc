
package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.BedsAndBathsDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

public class GetNumOfBedsAndBathsDialogController {
    @FXML
    private Spinner<Integer> numBedsSpinner;
    @FXML
    private Spinner<Double> numBathsSpinner;
    
    @FXML
    public BedsAndBathsDTO getBedsAndBaths() {
        return new BedsAndBathsDTO(numBedsSpinner.getValue(), numBathsSpinner.getValue());
    }
}
