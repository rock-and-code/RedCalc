
package com.rockandcode.redcalc.service;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
public interface CityService {
    public void listZipcodesForCity(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void getAverageListPriceByCityNumBedsBaths(TableView table, BorderPane borderPane);
    public void getAverageRentByCityNumBedsBaths(TableView table, BorderPane borderPane);
    public void getListingByCityAndUnderwrittenVal(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void updateCity(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void deleteCity(TableView table, BorderPane borderPane);
}
