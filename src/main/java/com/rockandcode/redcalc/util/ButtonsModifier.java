package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.controller.MainScreenController;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonsModifier {
    // Singleton instance of ButtonsModifier
    private static final ButtonsModifier instance = new ButtonsModifier();

    // Returns the instance of ButtonsModifier
    public static ButtonsModifier getInstance() {
        return instance;
    }

    // Returns a Button for listing states
    private Button getListStatesButton(MainScreenController mc) {
        Button btn = new Button("List States");
        btn.setId("listStatesBtn");
        btn.setOnAction((e) -> {
            mc.listStates();
        });
        return btn;
    }

    // Returns a Button for getting average list price by state
    private Button getAvgListPriceByStateButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPriceByStateBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByStateNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for getting average rent price by state
    private Button getAvgRenByStateButton(MainScreenController mc) {
        Button btn = new Button("Average rent price");
        btn.setId("getAvgRentByStateBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByStateNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for listing cities
    private Button getListCitiesButton(MainScreenController mc) {
        Button btn = new Button("List Cities");
        btn.setId("listCitiesForState");
        btn.setOnAction((e) -> {
            mc.listCitiesForState();
        });
        return btn;
    }

    // Returns a Button for showing zip codes
    private Button getListZipcodesButton(MainScreenController mc) {
        Button btn = new Button("Show Zipcodes");
        btn.setId("showZipcodesBtn");
        btn.setOnAction((e) -> {
            mc.listZipcodesForCity();
        });
        return btn;
    }

    // Returns a Button for getting average list price by zip code
    private Button getAvgListPriceByZipcodeButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPriceByZipcodeBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByZipcodeNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for showing listings
    private Button getShowListingsButton(MainScreenController mc) {
        Button btn = new Button("Show Listings");
        btn.setId("showListingsBtn");
        btn.setOnAction((e) -> {
            mc.listListingsForZipCodeId();
        });
        return btn;
    }

    // Returns a Button for updating a city
    private Button getUpdateCityButton(MainScreenController mc) {
        Button btn = new Button("Update City");
        btn.setId("updateCityBtn");
        btn.setOnAction((e) -> {
            mc.updateCity();
        });
        return btn;
    }

    // Returns a Button for getting average list price by city
    private Button getAvgListPriceByCityButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPriceByCityBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByCityNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for getting to the previous screen
    private Button getBackButton(MainScreenController mc) {
        Button btn = new Button("< Back");
        btn.setId("getBack");
        btn.setOnAction((e) -> {
            mc.getBack();
        });
        return btn;
    }

    // Returns a Button for getting average rent by city
    private Button getAvgRentByCityButton(MainScreenController mc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByCityBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByCityNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for getting average rent by zipcode
    private Button getAvgRentByZipcodeButton(MainScreenController mc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByZipcodeBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByZipcodeNumBedsAndBaths();
        });
        return btn;
    }

    // Returns a Button for getting listing that meets the underwriting criteria for a given zipcode
    private Button getListingByZipcodeAndUnderwrittenValButton(MainScreenController mc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByZipcodeAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            mc.getListingByZipcodeAndUnderwrittenVal();
        });
        return btn;
    }

    // Returns a Button for getting listing that meets the underwriting criteria for a given city
    private Button getListingByCityAndUnderwrittenValButton(MainScreenController mc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByCityAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            mc.getListingByCityAndUnderwrittenVal();
        });
        return btn;
    }

    // Returns a set of buttons for the state table
    public void setButtonsForStatesTable(MainScreenController mc, HBox hbox){
        /* Removing some buttons not applicable at this point */
        hbox.getChildren().clear();
        
        Button listCitiesBtn = getListCitiesButton(mc);
        Button averageListPriceByState = getAvgListPriceByStateButton(mc);
        Button averageRentPriceByState = getAvgRenByStateButton(mc);
        hbox.getChildren().add(listCitiesBtn);
        hbox.getChildren().add(averageListPriceByState);
        hbox.getChildren().add(averageRentPriceByState);
    }

    // Returns a set of buttons for the city table
    public void setButtonsForCitiesTable(MainScreenController mc, HBox hbox){
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(mc);
        
        Button showZipcodesBtn = getListZipcodesButton(mc);
        Button updateCityBtn = getUpdateCityButton(mc);
        Button getAvgListPriceByCityBtn = getAvgListPriceByCityButton(mc);
        Button getAvgRentByCityBtn = getAvgRentByCityButton(mc);
        Button getListingByCityAndUnderwrittenValBtn = getListingByCityAndUnderwrittenValButton(mc);
        hbox.getChildren().clear();
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPriceByCityBtn);
        hbox.getChildren().add(getAvgRentByCityBtn);
        hbox.getChildren().add(showZipcodesBtn);
        hbox.getChildren().add(getListingByCityAndUnderwrittenValBtn);
        hbox.getChildren().add(updateCityBtn);
    }

    // Returns a set of buttons for the zipcode table
    public void setButtonsForZipcodesTable(MainScreenController mc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(mc);
        Button getAvgListPriceByZipcodeBtn = getAvgListPriceByZipcodeButton(mc);
        Button getAvgRentByZipcodeBtn = getAvgRentByZipcodeButton(mc);
        Button showListingsBtn = getShowListingsButton(mc);
        Button backBtn = getBackButton(mc);
        Button getListingByZipcodeAndUnderwrittenValBtn = getListingByZipcodeAndUnderwrittenValButton(mc);
        hbox.getChildren().clear();
        hbox.getChildren().clear();
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPriceByZipcodeBtn);
        hbox.getChildren().add(getAvgRentByZipcodeBtn);
        hbox.getChildren().add(getListingByZipcodeAndUnderwrittenValBtn);
        hbox.getChildren().add(showListingsBtn);
    }

    // Returns a set of buttons for the listing table
    public void setButtonsForListingsTable(MainScreenController mc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        hbox.getChildren().clear();
        Button backBtn = getBackButton(mc);
        Button listStateBtn = getListStatesButton(mc);
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStateBtn);
    }

}
