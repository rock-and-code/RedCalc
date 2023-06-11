
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.model.City;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public interface CityService {
    public void listZipcodesForCity(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void findAverageListPriceByCityNumBedsAndBaths(TableView table, BorderPane borderPane);
    public void findAverageRentByCityNumBedsAndBaths(TableView table, BorderPane borderPane);
    public void findListingByCityAndUnderwrittenVal(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public List<City> findCitiesByStateIdForTableView(int stateId);
    public void updateCity(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void deleteCity(TableView table, BorderPane borderPane);
    public void deleteCities();
}
