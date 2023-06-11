package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.ListingCity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class InsertListingDialogController {

    @FXML
    private GridPane insertListingGridPane;
    @FXML
    private ComboBox stateComboBox;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox propertyTypeComboBox;
    @FXML
    private TextField zipcodeTextField;
    @FXML
    private TextField listPriceTextField;
    @FXML
    private TextField squareFootageTextField;
    @FXML
    private TextField yearBuiltTextField;
    @FXML
    private TextField latitudeTextField;
    @FXML
    private TextField longitudeTextField;
    @FXML
    private TextField urlTextField;
    @FXML
    private Spinner<Integer> numBedsSpinner; // = new Spinner<>() {{setEditable(true);}};
    @FXML
    private Spinner<Double> numBathsSpinner; // = new Spinner<>(){{setEditable(true);}};

    @FXML
    public ListingCity getNewListingInfo() throws NumberFormatException, RuntimeException {
        //Selecting all Nodes from the UI GridPane
        ObservableList<Node> elements = insertListingGridPane.getChildren();
        for (Node node : elements) {
            if (node instanceof TextField) {
                //Verifying if any Text field is empty
                if (((TextField) node).getText().isEmpty()) {
                    throw new RuntimeException("Fields cannot be empty");
                }
            }
        }

        ListingCity listingCity = new ListingCity();
        listingCity.setState(stateComboBox.getValue().toString().trim());
        listingCity.setCityName(cityTextField.getText().trim());
        listingCity.setAddress(addressTextField.getText().trim());
        listingCity.setPropertyType(propertyTypeComboBox.getValue().toString().trim());
        listingCity.setUrl(urlTextField.getText().trim());
        try {
            listingCity.setZipcode(Integer.parseInt(zipcodeTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Zipcode field must contains a numeric value");
        }
        try {
            listingCity.setListPrice(Double.parseDouble(listPriceTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("List price field must contains a numeric value");
        }
        try {
            listingCity.setNumBeds(numBedsSpinner.getValue());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bedrooms field must contains a numeric value");
        }
        try {
            listingCity.setNumBaths(numBathsSpinner.getValue());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bathrooms field must contains a numeric value");
        }
        try {
            listingCity.setSquareFootage(Integer.parseInt(squareFootageTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Square footage field must contains a numeric value");
        }
        try {
            listingCity.setYearBuilt(Integer.parseInt(yearBuiltTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Year built field must contains a numeric value");
        }
        try {
            listingCity.setLatitude(Double.parseDouble(latitudeTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Latitude field must contains a numeric value");
        }
        try {
            listingCity.setLongitude(Double.parseDouble(longitudeTextField.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Longitude field must contains a numeric value");
        }
        return listingCity;

    }

}
