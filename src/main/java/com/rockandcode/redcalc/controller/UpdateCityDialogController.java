
package com.rockandcode.redcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 *
 * @author riost02
 */
public class UpdateCityDialogController {
    @FXML
    private DialogPane updateCityDialogPane;
    @FXML
    private TextField newCityName;
    
    @FXML
    public String getNewCityName(){
        //Return new artist name
        return newCityName.getText().trim();
        
    }
}
