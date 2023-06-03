
package com.rockandcode.redcalc.service;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
public interface StateService {
    public void listStates(TableView table, ProgressBar progressBar, HBox buttonsContainer);
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer, Object previous);
}
