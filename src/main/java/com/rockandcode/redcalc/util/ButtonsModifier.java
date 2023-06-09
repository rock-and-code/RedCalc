package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.controller.MainScreenController;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
public class ButtonsModifier {

    private static final ButtonsModifier instance = new ButtonsModifier();
    public static ButtonsModifier getInstance() {
        return instance;
    }
    
    private Button getListStatesButton(MainScreenController mc) {
        Button btn = new Button("List States");
        btn.setId("listStatesBtn");
        btn.setOnAction((e) -> {
            mc.listStates();
        });
        return btn;
    }

    private Button getAvgListPriceByStateButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPriceByStateBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByStateNumBedsAndBaths();
        });
        return btn;
    }

    private Button getAvgRenByStateButton(MainScreenController mc) {
        Button btn = new Button("Average rent price");
        btn.setId("getAvgRentByStateBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByStateNumBedsAndBaths();
        });
        return btn;
    }
    
    private Button getListCitiesButton(MainScreenController mc) {
        Button btn = new Button("List Cities");
        btn.setId("listCitiesForState");
        btn.setOnAction((e) -> {
            mc.listCitiesForState();
        });
        return btn;
    }
    
    private Button getListZimcodesButton(MainScreenController mc) {
        Button btn = new Button("Show Zimcodes");
        btn.setId("showZimcodesBtn");
        btn.setOnAction((e) -> {
            mc.listZipcodesForCity();
        });
        return btn;
    }
    
    private Button getAvgListPricebyZimcodeButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPricebyZimcodeBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByZipcodeNumBedsAndBaths();
        });
        return btn;
    }
    
    private Button getShowListingsButton(MainScreenController mc) {
        Button btn = new Button("Show Listings");
        btn.setId("showListingsBtn");
        btn.setOnAction((e) -> {
            mc.listListingsForZipCodeId();
        });
        return btn;
    }
    
    private Button getUpdateCityButtton(MainScreenController mc) {
        Button btn = new Button("Update City");
        btn.setId("updateCityBtn");
        btn.setOnAction((e) -> {
            mc.updateCity();
        });
        return btn;
    }
    
    private Button getAvgListPricebyCityButton(MainScreenController mc) {
        Button btn = new Button("Average list price");
        btn.setId("getAvgListPricebyCityBtn");
        btn.setOnAction((e) -> {
            mc.getAverageListPriceByCityNumBedsAndBaths();
        });
        return btn;
    }
    
    private Button getBackButton(MainScreenController mc) {
        Button btn = new Button("< Back");
        btn.setId("getBack");
        btn.setOnAction((e) -> {
            mc.getBack();
        });
        return btn;
    } 
    private Button getAvgRentByCityButton(MainScreenController mc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByCityBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByCityNumBedsAndBaths();
        });
        return btn;
    }
    
    private Button getAvgRentByZimcodeButton(MainScreenController mc) {
        Button btn = new Button("Average Rent Rate");
        btn.setId("getAvgRentByZimcodeBtn");
        btn.setOnAction((e) -> {
            mc.getAverageRentByZipcodeNumBedsAndBaths();
        });
        return btn;
    }
    
    private Button getListingByZimcodeAndUnderwrittenValButton(MainScreenController mc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByZimcodeAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            mc.getListingByZipcodeAndUnderwrittenVal();
        });
        return btn;
    }
    
    private Button getListingByCityAndUnderwrittenValButton(MainScreenController mc) {
        Button btn = new Button("Listings by Underwritten value");
        btn.setId("getListingByCityAndUnderwrittenValBtn");
        btn.setOnAction((e) -> {
            mc.getListingByCityAndUnderwrittenVal();
        });
        return btn;
    }
    
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
    
    public void setButtonsForCitiesTable(MainScreenController mc, HBox hbox){
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(mc);
        
        Button showZimcodesBtn = getListZimcodesButton(mc);
        Button updateCityBtn = getUpdateCityButtton(mc);
        Button getAvgListPricebyCityBtn = getAvgListPricebyCityButton(mc);
        Button getAvgRentByCityBtn = getAvgRentByCityButton(mc);
        Button getListingByCityAndUnderwrittenValBtn = getListingByCityAndUnderwrittenValButton(mc);
        hbox.getChildren().clear();
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPricebyCityBtn);
        hbox.getChildren().add(getAvgRentByCityBtn);
        hbox.getChildren().add(showZimcodesBtn);
        hbox.getChildren().add(getListingByCityAndUnderwrittenValBtn);
        hbox.getChildren().add(updateCityBtn);
    }
    
    public void setButtonsForZipcodesTable(MainScreenController mc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        Button listStatesBtn = getListStatesButton(mc);
        Button getAvgListPricebyZimcodeBtn = getAvgListPricebyZimcodeButton(mc);
        Button getAvgRentByZimcodeBtn = getAvgRentByZimcodeButton(mc);
        Button showListingsBtn = getShowListingsButton(mc);
        Button backBtn = getBackButton(mc);
        Button getListingByZimcodeAndUnderwrittenValBtn = getListingByZimcodeAndUnderwrittenValButton(mc);
        hbox.getChildren().clear();
        hbox.getChildren().clear();
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStatesBtn);
        hbox.getChildren().add(getAvgListPricebyZimcodeBtn);
        hbox.getChildren().add(getAvgRentByZimcodeBtn);
        hbox.getChildren().add(getListingByZimcodeAndUnderwrittenValBtn);
        hbox.getChildren().add(showListingsBtn);
    }
    
    public void setButtonsForListingsTable(MainScreenController mc, HBox hbox) {
        /* Removing some buttons not applicable at this point */
        hbox.getChildren().clear();
        Button backBtn = getBackButton(mc);
        Button listStateBtn = getListStatesButton(mc);
        hbox.getChildren().add(backBtn);
        hbox.getChildren().add(listStateBtn);
    }

}
