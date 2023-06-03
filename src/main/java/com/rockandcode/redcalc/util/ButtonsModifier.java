package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.controller.MainScreenController;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

/**
 *
 * @author riost02
 */
public class ButtonsModifier {

    private static final ButtonsModifier instance = new ButtonsModifier();
    public static ButtonsModifier getInstance() {
        return instance;
    }
    
    private Button getListStatesButton(MainScreenController pc) {
        Button btn = new Button("List States");
        btn.setId("listStatesBtn");
        btn.setOnAction((e) -> {
            pc.listStates();
        });
        return btn;
    }
    
    private Button getListCitiesButton(MainScreenController pc) {
        Button btn = new Button("List Cities");
        btn.setId("listCitiesForState");
        btn.setOnAction((e) -> {
            pc.listCitiesForState();
        });
        return btn;
    }
    
    private Button getListZipcodesButton(MainScreenController pc) {
        Button btn = new Button("Show Zipcodes");
        btn.setId("showZipcodesBtn");
        btn.setOnAction((e) -> {
            pc.listZipcodesForCity();
        });
        return btn;
    }
    
    private Button getAvgListPricebyZipcodeButton(MainScreenController pc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPricebyZipcodeBtn");
        btn.setOnAction((e) -> {
            pc.getAverageListPriceByZipcodeNumBedsBaths();
        });
        return btn;
    }
    
    private Button getShowListingsButton(MainScreenController pc) {
        Button btn = new Button("Show Listings");
        btn.setId("showListingsBtn");
        btn.setOnAction((e) -> {
            pc.listListingsForZipCodeId();
        });
        return btn;
    }
    
    private Button getUpdateCityButtton(MainScreenController pc) {
        Button btn = new Button("Update City");
        btn.setId("updateCityBtn");
        btn.setOnAction((e) -> {
            pc.updateCity();
        });
        return btn;
    }
    
    private Button getAvgListPricebyCityButton(MainScreenController pc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPricebyCityBtn");
        btn.setOnAction((e) -> {
            pc.getAverageListPriceByCityNumBedsBaths();
        });
        return btn;
    }
    
    private Button getBackButton(MainScreenController pc) {
        Button btn = new Button("< Back");
        btn.setId("getBack");
        btn.setOnAction((e) -> {
            pc.getBack();
        });
        return btn;
    } 
    private Button getAvgRentByCityButton(MainScreenController pc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByCityBtn");
        btn.setOnAction((e) -> {
            pc.getAverageRentByCityNumBedsBaths();
        });
        return btn;
    }
    
    private Button getAvgRentByZipcodeButton(MainScreenController pc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByZipcodeBtn");
        btn.setOnAction((e) -> {
            pc.getAverageRentByZipcodeNumBedsBaths();
        });
        return btn;
    }
    
    private Button getListingByZipcodeAndUnderwrittenValButton(MainScreenController pc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByZipcodeAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            pc.getListingByZipcodeAndUnderwrittenVal();
        });
        return btn;
    }
    
    private Button getListingByCityAndUnderwrittenValButton(MainScreenController pc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByCityAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            pc.getListingByCityAndUnderwrittenVal();
        });
        return btn;
    }
    
    public void setButtonsForStatesTable(MainScreenController pc, HBox hbox){
        /* Removing some buttons not applicable at this point */
        hbox.getChildren().clear();
        Button listCitiesBtn = getListCitiesButton(pc);
        hbox.getChildren().add(listCitiesBtn);
    }
    
    public void setButtonsForCitiesTable(MainScreenController pc, HBox hbox){
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(pc);
        
        Button showZipcodesBtn = getListZipcodesButton(pc);
        Button updateCityBtn = getUpdateCityButtton(pc);
        Button getAvgListPricebyCityBtn = getAvgListPricebyCityButton(pc);
        Button getAvgRentByCityBtn = getAvgRentByCityButton(pc);
        Button getListingByCityAndUnderwrittenValBtn = getListingByCityAndUnderwrittenValButton(pc);
        hbox.getChildren().clear();
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPricebyCityBtn);
        hbox.getChildren().add(getAvgRentByCityBtn);
        hbox.getChildren().add(showZipcodesBtn);
        hbox.getChildren().add(getListingByCityAndUnderwrittenValBtn);
        hbox.getChildren().add(updateCityBtn);
    }
    
    public void setButtonsForZipcodesTable(MainScreenController pc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(pc);
        Button getAvgListPricebyZipcodeBtn = getAvgListPricebyZipcodeButton(pc);
        Button getAvgRentByZipcodeBtn = getAvgRentByZipcodeButton(pc);
        Button showListingsBtn = getShowListingsButton(pc);
        Button backBtn = getBackButton(pc);
        Button getListingByZipcodeAndUnderwrittenValBtn = getListingByZipcodeAndUnderwrittenValButton(pc);
        hbox.getChildren().clear();
        hbox.getChildren().clear();
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPricebyZipcodeBtn);
        hbox.getChildren().add(getAvgRentByZipcodeBtn);
        hbox.getChildren().add(getListingByZipcodeAndUnderwrittenValBtn);
        hbox.getChildren().add(showListingsBtn);
    }
    
    public void setButtonsForListingsTable(MainScreenController pc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        hbox.getChildren().clear();
        Button backBtn = getBackButton(pc);
        Button listStateBtn = getListStatesButton(pc);
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStateBtn);
    }

}
