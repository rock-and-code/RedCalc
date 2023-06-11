
package com.rockandcode.redcalc.service;

import static com.rockandcode.redcalc.controller.MainScreenController.GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML;
import static com.rockandcode.redcalc.controller.MainScreenController.GET_LISTINGS_BY_UNDERWRITTEN_VAL_DIALOG_FXML;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.rockandcode.redcalc.controller.GetNumOfBedsAndBathsDialogController;
import com.rockandcode.redcalc.controller.GetListingByUnderwrittenValDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.BedsAndBathsDTO;
import com.rockandcode.redcalc.model.BedsBathsAndCapRateDTO;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ZipCode;
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

public class ZipcodeServiceImpl implements ZipcodeService {
    
    private final MainScreenController mc;
    private ContextMenu contextMenu;
    private final ZipcodeRepository zipcodeRepository;

    public ZipcodeServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.zipcodeRepository = new ZipcodeRepository(Datasource.getInstance());
    }

    @Override
    public void listListingsForZipCodeId(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        final ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
        if (zipcode == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No Zipcode Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }
        mc.setPrevious(zipcode);
        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForListingsTable(mc, buttonsContainer);
        //Changing the columsn displayed dynamically
        TableViewEditor.getInstance().setColumnsForListingsTable(table);
        //System.out.println("DEBUGGING listSongsForAlbum: album=" + album.getName() + " : id=" + album.getId());
        Task<ObservableList<Listing>> task = new Task<ObservableList<Listing>>() {
            @Override
            protected ObservableList<Listing> call() throws Exception {
                return FXCollections.observableArrayList(
                        zipcodeRepository.findListingsForZipcodeId(zipcode.getId())
                );
            }
        };
        table.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(e->contextMenu = RedCalcContextMenu.getInstance().getContextMenuForListingTable(table, mc));
        new Thread(task).start();
    }

    @Override
    public void listListingForZipCodeNumber(int zipcode, TableView table, HBox buttonsContainer) {
        TableViewEditor.getInstance().setColumnsForListingsTable(table);
        /* Removing some buttons not applicable at this point */
        ButtonsModifier.getInstance().setButtonsForListingsTable(mc, buttonsContainer);

        Task<ObservableList<Listing>> task = new Task<ObservableList<Listing>>() {
            @Override
            protected ObservableList<Listing> call() throws Exception {
                return FXCollections.observableArrayList(
                        zipcodeRepository.findListingsForZipcodeNumber(zipcode)
                );
            }
        };
        table.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(e->contextMenu = RedCalcContextMenu.getInstance().getContextMenuForListingTable(table, mc));
        new Thread(task).start();
    }

    @Override
    public void findAverageListPriceByZipcodeNumBedsAndBaths(TableView table, BorderPane borderPane) {
        int beds;
        double baths;
        final ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
        if (zipcode == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No Zipcde Selected");
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
        fxmLoader.setLocation(App.class.getResource(GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML));
        try {
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
        } else {
            ConsoleLogger.getInstance().printMessage("Cancel pressed");
            return;
        }

        beds = data.getNumBeds();
        baths = data.getNumBaths();

        Double avg;
        try {
            avg = zipcodeRepository.findAverageListPriceByZipcodeBedsBaths(zipcode.getZipcode(), beds, baths);
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
    public void findAverageRentByZipcodeNumBedsAndBaths(TableView table, BorderPane borderPane) {
        final int numBeds = 0, numBaths = 1;
        int beds;
        double baths;
        final ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
        if (zipcode == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No Zipcde Selected");
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
        fxmLoader.setLocation(App.class.getResource(GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML));
        try {
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
            ConsoleLogger.getInstance().printMessage("Cancel pressed");
            return;
        }

        beds = data.getNumBeds();
        baths = data.getNumBaths();

        Double avg;
        try {
            avg = zipcodeRepository.findAverageRentByZipcodeBedsBaths(zipcode.getZipcode(), beds, baths);
        } catch (Exception e) {
            Alert a = Alerts.getInstance().getErrorAlert("Couldn't get the average rant rate!");
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
    public List<ZipCode> findZipcodesByCityIdForTableView(int cityId) {
        return zipcodeRepository.findZipcodesByCityIdForTableView(cityId);
    }

    @Override
    public void findListingByZipcodeAndUnderwrittenVal(TableView table, BorderPane borderPane,
                                                       HBox buttonsContainer) {
        int numBeds = 0, numBaths = 1, capRateData = 2, beds;
        double baths, capRate;
        boolean run = false;
        final ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
        if (zipcode == null) {
            Alert a = Alerts.getInstance().getErrorAlert("No Zipcode Selected");
            a.initOwner(borderPane.getScene().getWindow());
            a.show();
            return;
        }
        mc.setPrevious(zipcode);

        //Getting parameters for db quer
        BedsBathsAndCapRateDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getListingsByUnderwrittenValDialog();
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

        //Changing the columns displayed dynamically
        TableViewEditor.getInstance().setColumnsForListingsTable(table);

        //Getting listings from DB
        Task<ObservableList<Listing>> task = new Task<ObservableList<Listing>>() {
            @Override
            protected ObservableList<Listing> call() throws Exception {
                return FXCollections.observableArrayList(
                        zipcodeRepository.findListingsByZipcodeAndUnderwrittenValue(zipcode.getZipcode(), beds, baths, capRate)
                );
            }
        };
        table.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(e-> contextMenu = RedCalcContextMenu.getInstance().getContextMenuForListingTable(table, mc));
        new Thread(task).start();
    }

    @Override
    public void deleteZipcodes() {
        zipcodeRepository.deleteZipcodes();
    }

}
