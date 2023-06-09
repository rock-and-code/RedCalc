
package com.rockandcode.redcalc.service;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public interface StateService {
    public void listStates(TableView table, ProgressBar progressBar, HBox buttonsContainer);
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer, Object previous);
    public void findAverageListPriceByStateIdBedsAndBaths(TableView table, BorderPane borderPane);
    public void findAverageRentByStateIdBedsAndBaths(TableView table, BorderPane borderPane);
    public void deleteStateById(int stateID);
    public void deleteStateByName(String stateName);
    public void deleteStates();
}
