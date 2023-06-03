
package com.rockandcode.redcalc.service;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
public interface ZipcodeService {
    public void listListingsForZipCodeId(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void listListingForZipCodeNumber(int zipcode, TableView table, HBox buttonsContainer);
    public void getAverageListPriceByZipcodeNumBedsBaths(TableView table, BorderPane borderPane);
    public void getAverageRentByZipcodeNumBedsBaths(TableView table, BorderPane borderPane);
    public void getListingByZipcodeAndUnderwrittenVal(TableView table, BorderPane borderPane, HBox buttonsContainer);
}
