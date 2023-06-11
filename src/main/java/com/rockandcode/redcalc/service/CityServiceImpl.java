
package com.rockandcode.redcalc.service;

import static com.rockandcode.redcalc.controller.MainScreenController.GET_AVG_LIST_PRICE_BY_BEDS_BATHS_DIALOG_FXML;
import static com.rockandcode.redcalc.controller.MainScreenController.GET_LISTINGS_BY_UNDERWRITTEN_VAL_DIALOG_FXML;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.rockandcode.redcalc.controller.GetAvgListPriceByBedsBathsDialogController;
import com.rockandcode.redcalc.controller.GetListingByUnderwrittenValDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.controller.UpdateCityDialogController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.BedsAndBathsDTO;
import com.rockandcode.redcalc.model.BedsBathsAndCapRateDTO;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ZipCode;
import com.rockandcode.redcalc.repository.CityRepository;
import com.rockandcode.redcalc.repository.ZipcodeRepository;
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
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class CityServiceImpl implements CityService {
    
    private final MainScreenController mc;
    private ContextMenu contextMenu;
    private CityRepository cityRepository;
    private ZipcodeRepository zipcodeRepository;

    public CityServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.cityRepository = new CityRepository(Datasource.getInstance());
        this.zipcodeRepository = new ZipcodeRepository(Datasource.getInstance());
    }

    @Override
    public void listZipcodesForCity(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }
        mc.setPrevious(city);
        mc.setCurrentCity(city);
        
        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForZipcodesTable(mc, buttonsContainer);

        //Adding columns to the cityTable dynamically
        TableViewEditor.getInstance().setColumnsForZipcodesTable(table);

        Task<ObservableList<ZipCode>> task = new Task<ObservableList<ZipCode>>() {
            @Override
            protected ObservableList<ZipCode> call() throws Exception {
                return FXCollections.observableArrayList(
                        zipcodeRepository.findZipcodesByCityIdForTableView(city.getId())
                );
            }
        };
        task.setOnSucceeded(e->contextMenu = RedCalcContextMenu.getInstance().getContextMenuForZipcodeTable(table, mc));
        table.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
    
    @Override
    public void findAverageListPriceByCityNumBedsAndBaths(TableView table, BorderPane borderPane) {
        final int numBeds = 0, numBaths = 1;
        int beds;
        double baths;
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        BedsAndBathsDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getAvgListPriceByCityBedsBathsDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        //System.out.println("getClass=" + getClass());
        fxmLoader.setLocation(App.class.getResource(GET_AVG_LIST_PRICE_BY_BEDS_BATHS_DIALOG_FXML));
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
            GetAvgListPriceByBedsBathsDialogController controller = fxmLoader.getController();
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
            avg = cityRepository.findAverageListPriceByCityBedsBaths(city.getName(), beds, baths);
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
    public void findAverageRentByCityNumBedsAndBaths(TableView table, BorderPane borderPane) {
        final int numBeds = 0, numBaths = 1;
        int beds;
        double baths;
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        BedsAndBathsDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getAvgRentByCityBedsBathsDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(GET_AVG_LIST_PRICE_BY_BEDS_BATHS_DIALOG_FXML));
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
            GetAvgListPriceByBedsBathsDialogController controller = fxmLoader.getController();
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
            avg = cityRepository.findAverageRentByCityBedsBaths(city.getName(), beds, baths);
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
    public void findListingByCityAndUnderwrittenVal(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        int numBeds = 0, numBaths = 1, capRateData = 2, beds;
        double baths, capRate;
        boolean run = false;
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }
        mc.setPrevious(city);
        mc.setCurrentCity(city);

        //Getting parameters for db quer
        BedsBathsAndCapRateDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getListingsByZipcodeandUnderwrittenValDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(GET_LISTINGS_BY_UNDERWRITTEN_VAL_DIALOG_FXML));
        try {
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            GetListingByUnderwrittenValDialogController controller = fxmLoader.getController();
            data = controller.getBedsBathsAndCapRate();
            beds = data.getNumBeds();
            baths = data.getNumBaths();
            try {
                double capR = Double.parseDouble(data.getCapRate());
                capRate = capR / 100;
                run = true;
            } catch (NumberFormatException e) {
                Alert a = Alerts.getInstance().getErrorAlert("Cap rate must be a numberic value!");
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
                return;
            }
        } else {
            ConsoleLogger.getInstance().printMessage("Cancel pressed");
            return;
        }

        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForListingsTable(mc, buttonsContainer);

        //Changing the columsn displayed dynamically
        TableViewEditor.getInstance().setColumnsForListingsTable(table);

        //Getting listings from DB
        Task<ObservableList<Listing>> task = new Task<ObservableList<Listing>>() {
            @Override
            protected ObservableList<Listing> call() throws Exception {
                return FXCollections.observableArrayList(
                        cityRepository.findListingsByCityAndTheUnderwrittenValue(city.getName(), beds, baths, capRate)
                );
            }
        };
        table.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(e-> contextMenu = RedCalcContextMenu.getInstance().getContextMenuForListingTable(table, mc));
        new Thread(task).start();
    }

    @Override
    public List<City> findCitiesByStateIdForTableView(int stateId) {
        return cityRepository.findCitiesByStateIdForTableView(stateId);
    }

    @Override
    public void updateCity(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        String newName;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getUpdateCityDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(MainScreenController.UPDATE_CITY_DIALOG_FXML));
        try {
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            UpdateCityDialogController controller = fxmLoader.getController();
            newName = controller.getNewCityName();
            //System.out.println("OK pressed" + " : newName= " + newName);
        } else {
            System.out.println("Cancel pressed");
            newName = city.getName();     //Use the selected artist name
        }

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return cityRepository.updateCityName(city.getId(), newName);
            }
        };
        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()) {
                city.setName(newName);
                table.refresh();
            }
        });

        new Thread(task).start();
    }

    @Override
    public void deleteCity(TableView table, BorderPane borderPane) {
        final City city = (City) table.getSelectionModel().getSelectedItem();
        if (city == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No City Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }

        Window mainStage = borderPane.getScene().getWindow();
        /* Creating a new instance of the dialog class */
        Alert a = Alerts.getInstance().getConfirmationAlert("Are you sure you want to delete " + city.getName() + " from the database?");
        a.initOwner(mainStage);
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            /* First: delete listing from a city */
            /* Second: delete zipcode from a city */
            cityRepository.deleteCityById(city.getId());
            //Refresh table
            table.refresh();
            a = Alerts.getInstance().getInformationAlert(city.getName() + " was successfully deleted");
            a.initOwner(mainStage);
            a.show();
        } else {
        }
    }

    @Override
    public void deleteCities() {
        cityRepository.deleteCities();
    }

}
