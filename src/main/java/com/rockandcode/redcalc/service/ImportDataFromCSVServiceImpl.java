
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.model.RealEstateState;
import com.rockandcode.redcalc.task.ReadFairMarketRentRatesFromCSVFileTask;
import com.rockandcode.redcalc.task.ReadMarketRentRatesFromSQLITECSVFileTask;
import com.rockandcode.redcalc.task.ReadMarketRentsRatesFromCSVFileTask;
import com.rockandcode.redcalc.task.ReadSalesListingsFromRedfinCSVFileTask;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ConsoleLogger;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ImportDataFromCSVServiceImpl implements ImportDataFromCSVService {
    
    private final MainScreenController mc;
    private ContextMenu contextMenu;
    private StateService stateService;

    public ImportDataFromCSVServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.stateService = new StateServiceImpl(mc, contextMenu);
    }

    @Override
    public void readListingsFromRedfinCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage, HBox buttonsContainer, Object previous) {
        Window mainStage = borderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null && selectedFile.isFile()) {
            ReadSalesListingsFromRedfinCSVFileTask task = new ReadSalesListingsFromRedfinCSVFileTask(selectedFile);

            progressBar.progressProperty().bind(task.progressProperty());
            progressBarMessage.promptTextProperty().bind(task.messageProperty());
            progressBar.setVisible(true);
            progressBarMessage.setVisible(true);
            task.setOnSucceeded(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                //REFRESH TABLE
                if (previous instanceof RealEstateState) {
                    stateService.listCitiesForState(table, borderPane, buttonsContainer, previous);
                } else {
                    stateService.listStates(table, progressBar, buttonsContainer);
                }
                Alert a = Alerts.getInstance().getConfirmationAlert(selectedFile.getName() + " Listings successfully added to the database");
                a.initOwner(mainStage);
                a.show();
            });
            task.setOnFailed(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                Alert a = Alerts.getInstance().getErrorAlert("Unable to add " + selectedFile.getName() + " listings to the database");
                a.initOwner(mainStage);
                a.show();
            });
            new Thread(task).start();
        }
    }

    @Override
    public void readMarketRentsFromCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage) {
        Window mainStage = borderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null && selectedFile.isFile()) {

            ReadMarketRentsRatesFromCSVFileTask task = new ReadMarketRentsRatesFromCSVFileTask(selectedFile);
            progressBar.progressProperty().bind(task.progressProperty());
            progressBar.setVisible(true);
            task.setOnSucceeded(e -> {
                progressBar.setVisible(false);
                String fileName = selectedFile.getName();
                Alert a = Alerts.getInstance().getConfirmationAlert(fileName + " rentals successfully added to the database");
                a.initOwner(mainStage);
                a.show();
                //REFRESH TABLE
                table.refresh();
            });
            task.setOnFailed(e -> {
                progressBar.setVisible(false);
                Alert a = Alerts.getInstance().getErrorAlert("Unable to add rentals to the database");
                a.initOwner(mainStage);
                a.show();
            });
            new Thread(task).start();
        }
    }

    @Override
    public void readMarketRentsFromSQLITECSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage) {
        Window mainStage = borderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null && selectedFile.isFile()) {

            ReadMarketRentRatesFromSQLITECSVFileTask task = new ReadMarketRentRatesFromSQLITECSVFileTask(selectedFile);
            progressBar.progressProperty().bind(task.progressProperty());
            progressBarMessage.promptTextProperty().bind(task.messageProperty());
            progressBar.setVisible(true);
            progressBarMessage.setVisible(true);
            task.setOnSucceeded(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                String fileName = selectedFile.getName();
                Alert a = Alerts.getInstance().getConfirmationAlert(fileName + " rentals successfully added to the database");
                a.initOwner(mainStage);
                a.show();
                //REFRESH TABLE
                table.refresh();
            });
            task.setOnFailed(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                Alert a = Alerts.getInstance().getErrorAlert("Unable to add rentals to the database");
                a.initOwner(mainStage);
                a.show();
            });
            new Thread(task).start();
        }
    }

    @Override
    public void readFairRentsFromCSV(TableView table, BorderPane borderPane, 
            ProgressBar progressBar, TextField progressBarMessage) {
                ConsoleLogger.getInstance().printMessage("readFairRentsFromCSV called");
        Window mainStage = borderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null && selectedFile.isFile()) {

            ReadFairMarketRentRatesFromCSVFileTask task = new ReadFairMarketRentRatesFromCSVFileTask(selectedFile);

            progressBar.progressProperty().bind(task.progressProperty());
            progressBarMessage.promptTextProperty().bind(task.messageProperty());
            progressBar.setVisible(true);
            progressBarMessage.setVisible(true);
            task.setOnSucceeded(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                Alert a = Alerts.getInstance().getConfirmationAlert("Listings successfully added to the database");
                a.initOwner(mainStage);
                a.show();
                //REFRESH TABLE
                table.refresh();
            });
            task.setOnFailed(e -> {
                progressBar.setVisible(false);
                progressBarMessage.setVisible(false);
                Alert a = Alerts.getInstance().getErrorAlert("Unable to add listings to the database");
                a.initOwner(mainStage);
                a.show();
            });
            new Thread(task).start();
        }

    }

}
