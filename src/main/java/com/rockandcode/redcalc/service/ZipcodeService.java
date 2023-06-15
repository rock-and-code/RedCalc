
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.model.ZipCode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public interface ZipcodeService {
    public void findListingsByZipCodeId(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void listListingForZipCodeNumber(int zipcode, TableView table, HBox buttonsContainer);
    public void findAverageListPriceByZipcodeNumBedsAndBaths(TableView table, BorderPane borderPane);
    public void findAverageRentByZipcodeNumBedsAndBaths(TableView table, BorderPane borderPane);
    public List<ZipCode> findZipcodesByCityIdForTableView(int cityId);
    public void findListingByZipcodeAndUnderwrittenVal(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void deleteZipcodes();
}
