
package com.rockandcode.redcalc.service;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public interface ImportDataFromCSVService {
    public void readListingsFromRedfinCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage, HBox buttonsContainer, Object previous);
    public void readMarketRentsFromCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage);
    public void readMarketRentsFromSQLITECSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage);
    public void readFairRentsFromCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage);
}
