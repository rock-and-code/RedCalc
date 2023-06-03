
package com.rockandcode.redcalc.service;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public interface RentService {
    public void downloadRentListingByCityAndState(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage);
    public void clearMarketRents(TableView table, BorderPane borderPane);
}
