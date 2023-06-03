
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.model.City;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
public interface UtilityService {
    public void clearDatabase(TableView table, BorderPane borderPane, ProgressBar progressBar,
            HBox buttonsContainer);
    public void getBack(TableView table, HBox buttonsContainer, Object previous, City currentCity);
}
