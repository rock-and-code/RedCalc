package com.rockandcode.redcalc.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

import com.rockandcode.redcalc.controller.GetNumOfBedsAndBathsDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.BedsAndBathsDTO;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.RealEstateState;
import com.rockandcode.redcalc.repository.StateRepository;
import com.rockandcode.redcalc.task.GetAllStatesTask;
import com.rockandcode.redcalc.ui.App;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ButtonsModifier;
import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.Dialogs;
import com.rockandcode.redcalc.util.RedCalcContextMenu;
import com.rockandcode.redcalc.util.TableViewEditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StateServiceImpl implements StateService {

    private final MainScreenController mc;
    private ContextMenu contextMenu;
    private StateRepository stateRepository;

    public StateServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.stateRepository = new StateRepository(Datasource.getInstance());
    }

    @Override
    public void listStates(TableView table, ProgressBar progressBar, HBox buttonsContainer) {
        /* Changes the table view columns and its property value factory */
        TableViewEditor.getInstance().setColumnsForStatesTable(table);
        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForStatesTable(mc, buttonsContainer);
        /* Queries database and populates the table view with cities names */
        Task<ObservableList<RealEstateState>> task = new GetAllStatesTask();
        table.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(e -> {
            progressBar.setVisible(false);
            contextMenu = RedCalcContextMenu.getInstance().getContextMenuForStateTable(table, mc);
        });
        task.setOnFailed(e -> progressBar.setVisible(false));
        new Thread(task).start();
    }

    @Override
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        final RealEstateState state = (RealEstateState) table.getSelectionModel().getSelectedItem();
        if (state == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No State Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }
        mc.setPrevious(state);
        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForCitiesTable(mc, buttonsContainer);

        //Adding columns to the cityTable dynamically
        TableViewEditor.getInstance().setColumnsForCitiesTable(table);

        //Task to get cities from database and display them in the table view
        Task<ObservableList<City>> task = new Task<ObservableList<City>>() {
            @Override
            protected ObservableList<City> call() throws Exception {
                return FXCollections.observableArrayList(
                        stateRepository.findCitiesByStateIdForTableView(state.getId())
                );
            }
        };

        table.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(e -> contextMenu = RedCalcContextMenu.getInstance().getContextMenuForCityTable(table, mc));
        new Thread(task).start();
    }

    @Override
    public void listCitiesForState(TableView table, BorderPane borderPane, HBox buttonsContainer, Object previous) {
        if (previous instanceof RealEstateState) {
            /* Removing some buttons not applicable at this point */
            ButtonsModifier.getInstance().setButtonsForCitiesTable(mc, buttonsContainer);

            //Adding columns to the cityTable dynamically
            TableViewEditor.getInstance().setColumnsForCitiesTable(table);

            //Task to get cities from database and display them in the table view
            Task<ObservableList<City>> task = new Task<ObservableList<City>>() {
                @Override
                protected ObservableList<City> call() throws Exception {
                    return FXCollections.observableArrayList(
                            stateRepository.findCitiesByStateIdForTableView(((RealEstateState) previous).getId())
                    );
                }
            };

            table.itemsProperty().bind(task.valueProperty());
            task.setOnSucceeded(e -> contextMenu = RedCalcContextMenu.getInstance().getContextMenuForCityTable(table, mc));
            new Thread(task).start();
        }
    }

    @Override
    public void findAverageListPriceByStateIdBedsAndBaths(TableView table, BorderPane borderPane) {
        int beds;
        double baths;
        final RealEstateState state = (RealEstateState) table.getSelectionModel().getSelectedItem();
        if (state == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No State Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        BedsAndBathsDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getAvgListPriceByBedsBathsDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        //System.out.println("getClass=" + getClass());
        fxmLoader.setLocation(App.class.getResource(MainScreenController.GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML));
        try {
            /* SETTING DIALOG CONTENT FROM DIALOG FXML */
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            GetNumOfBedsAndBathsDialogController controller = fxmLoader.getController();
            data = controller.getBedsAndBaths();
            //ConsoleLogger.getInstance().printMessage("OK pressed" + " : numBeds= " + data[numBeds].toString());
        } else {
            //CANCEL BUTTON PRESSED
            //ConsoleLogger.getInstance().printMessage("Cancel pressed");
            return;
        }

        beds = data.getNumBeds();
        baths = data.getNumBaths();

        Double avg;
        try {
            avg = stateRepository.findAverageListPriceByStateBedsAndBaths(state.getId(), beds, baths);
        } catch (Exception e) {
            Alert a = Alerts.getInstance().getErrorAlert("Couldn't get the average list price!");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
        String output = myFormatter.format(avg);
        Alert a;
        if (avg == 0) {
            a = Alerts.getInstance().getInformationAlert("The are no listing that matches given criteria");
        } else {
            a = Alerts.getInstance().getInformationAlert("The average list price is " + output);

        }
        a.initOwner(borderPane.getScene().getWindow());
        a.show();
    }

    @Override
    public void findAverageRentByStateIdBedsAndBaths(TableView table, BorderPane borderPane) {
        int beds;
        double baths;
        final RealEstateState state = (RealEstateState) table.getSelectionModel().getSelectedItem();
        if (state == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No State Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        BedsAndBathsDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getAvgRentByBedsBathsDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(MainScreenController.GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML));
        try {
            /* SETTING DIALOG CONTENT FROM DIALOG FXML */
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            GetNumOfBedsAndBathsDialogController controller = fxmLoader.getController();
            data = controller.getBedsAndBaths();
            //ConsoleLogger.getInstance().printMessage("OK pressed" + " : numBeds= " + data[numBeds].toString());
        } else {
            //CANCEL BUTTON PRESSED
            //ConsoleLogger.getInstance().printMessage("Cancel pressed");
            return;
        }

        beds = data.getNumBeds();
        baths = data.getNumBaths();

        Double avg;
        try {
            avg = stateRepository.findAverageRentByStateBedsAndBaths(state.getId(), beds, baths);
        } catch (Exception e) {
            Alert a = Alerts.getInstance().getErrorAlert("Couldn't get the average rent!");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
        String output = myFormatter.format(avg);
        Alert a;
        if (avg == 0) {
            a = Alerts.getInstance().getInformationAlert("The are no listing that matches given criteria");
        } else {
            a = Alerts.getInstance().getInformationAlert("The average rent rate is " + output);

        }
        a.initOwner(borderPane.getScene().getWindow());
        a.show();
    }

    @Override
    public void deleteStateById(int stateID) {
        stateRepository.deleteStateById(stateID);
    }

    @Override
    public void deleteStateByName(String stateName) {
        stateRepository.deleteStateByName(stateName);
    }

    @Override
    public void deleteStates() {
        stateRepository.deleteStates();
    }

}
