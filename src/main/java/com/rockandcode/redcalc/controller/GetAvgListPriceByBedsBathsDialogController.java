
package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.BedsAndBathsDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

public class GetAvgListPriceByBedsBathsDialogController {
    @FXML
    private Spinner<Integer> numBedsSpinner;
    @FXML
    private Spinner<Double> numBathsSpinner;
    
    @FXML
    public BedsAndBathsDTO getBedsAndBaths() {
        // Gets the number of beds and baths from the spinners.
        int numBeds = numBedsSpinner.getValue();
        double numBaths = numBathsSpinner.getValue();

        // Creates a new BedsAndBathsDTO object and returns it.
        return new BedsAndBathsDTO(numBeds, numBaths);
    }
}
