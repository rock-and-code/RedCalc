package com.rockandcode.redcalc.util;


import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.RealEstateState;
import com.rockandcode.redcalc.model.ZipCode;
import com.rockandcode.redcalc.task.GetAllCitiesForAStateTask;
import com.rockandcode.redcalc.task.GetAllStatesTask;
import com.rockandcode.redcalc.task.GetAllZipcodesForACityTask;
import com.rockandcode.redcalc.task.GetListingsForAZipcodeTask;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

/**
 *
 * @author riost02
 */
public class RedCalcContextMenu {

    private static final RedCalcContextMenu instance = new RedCalcContextMenu();

    public static RedCalcContextMenu getInstance() {
        return instance;
    }

    public ContextMenu getContextMenuForStateTable(TableView table, MainScreenController mainScreenController) {
        ContextMenu menu = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        MenuItem listCities = new MenuItem("List Cities");
        MenuItem averageListPrice = new MenuItem("Get Average List Price");
        MenuItem averageRentPrice = new MenuItem("Get Average Rent");
        MenuItem delete = new MenuItem("Delete");

        copy.setOnAction((ActionEvent e) -> {
            RealEstateState state = (RealEstateState) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(state.getName());
            clipboard.setContent(content);
        });
        averageListPrice.setOnAction((ActionEvent e) -> {
            mainScreenController.getAverageListPriceByStateNumBedsAndBaths();
        });
        averageRentPrice.setOnAction((ActionEvent e) -> {
            mainScreenController.getAverageRentByStateNumBedsAndBaths();
        });
        
        listCities.setOnAction((ActionEvent e) -> mainScreenController.listCitiesForState());

        delete.setOnAction((ActionEvent e) -> {
            Window mainStage = mainScreenController.getPrimaryBorderPane().getScene().getWindow();
            Alert confirmationBeforeDeleteAlert = null;
            RealEstateState state = (RealEstateState) table.getSelectionModel().getSelectedItem();
            confirmationBeforeDeleteAlert = Alerts.getInstance().getConfirmationAlert("Are you sure you want to delete " + state.getName() + " from the database?");
            confirmationBeforeDeleteAlert.initOwner(mainStage);
            confirmationBeforeDeleteAlert.showAndWait();
            if (confirmationBeforeDeleteAlert.getResult() == ButtonType.OK) {
                //Delete State
                Datasource.getInstance().deleteStateByName(state.getDBName());
                TableViewEditor.getInstance().setColumnsForStatesTable(table);
                /* Removing some buttons not applicable at this point */

                HBox hboxOfButtons = mainScreenController.getButtons();
                ButtonsModifier.getInstance().setButtonsForStatesTable(mainScreenController, hboxOfButtons);
                /* Queries database and populates the table view with cities names */
                Task<ObservableList<RealEstateState>> task = new GetAllStatesTask();
                table.itemsProperty().bind(task.valueProperty());
                new Thread(task).start();
                Alert a = Alerts.getInstance().getConfirmationAlert(state.getName() + " state has been deleted");
                a.initOwner(table.getScene().getWindow());
                a.show();

            }
        });

        menu.getItems().addAll(copy);
        menu.getItems().addAll(listCities);
        menu.getItems().addAll(averageListPrice);
        menu.getItems().addAll(averageRentPrice);
        menu.getItems().addAll(delete);
        table.getSelectionModel().getTableView().setContextMenu(menu);
        return menu;
    }

    public ContextMenu getContextMenuForCityTable(TableView table, MainScreenController mainScreenController) {
        ContextMenu menu = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        MenuItem averageListPrice = new MenuItem("Get Average List Price");
        MenuItem averageRentPrice = new MenuItem("Get Average Rent Price");
        MenuItem listZipcodes = new MenuItem("List Zipcodes");
        MenuItem delete = new MenuItem("Delete");

        copy.setOnAction((ActionEvent e) -> {
            City city = (City) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(city.getName()); // item is captured in the closure
            clipboard.setContent(content);
        });
        averageListPrice.setOnAction((ActionEvent e) -> {
            mainScreenController.getAverageListPriceByCityNumBedsAndBaths();
        });
        averageRentPrice.setOnAction((ActionEvent e) -> {
            mainScreenController.getAverageRentByCityNumBedsAndBaths();
        });
        listZipcodes.setOnAction((ActionEvent e) -> {
            mainScreenController.listZipcodesForCity();
        });
        delete.setOnAction((ActionEvent e) -> {
            Window mainStage = mainScreenController.getPrimaryBorderPane().getScene().getWindow();
            Alert confirmationBeforeDeleteAlert = null;
            City city = (City) table.getSelectionModel().getSelectedItem();
            confirmationBeforeDeleteAlert = Alerts.getInstance().getConfirmationAlert("Are you sure you want to delete " + city.getName() + " from the database?");
            confirmationBeforeDeleteAlert.initOwner(mainStage);
            confirmationBeforeDeleteAlert.showAndWait();
            if (confirmationBeforeDeleteAlert.getResult() == ButtonType.OK) {
                //Saving reference of the state Id to refresh city tables
                int stateId = city.getStateId();
                //Delete City
                Datasource.getInstance().deleteCityByName(city.getName());
                TableViewEditor.getInstance().setColumnsForCitiesTable(table);
                /* Removing some buttons not applicable at this point */

                HBox hboxOfButtons = mainScreenController.getButtons();
                ButtonsModifier.getInstance().setButtonsForCitiesTable(mainScreenController, hboxOfButtons);
                /* Queries database and populates the table view with cities names */

                Task<ObservableList<City>> task = new GetAllCitiesForAStateTask(stateId);
                table.itemsProperty().bind(task.valueProperty());
                new Thread(task).start();
                Alert a = Alerts.getInstance().getConfirmationAlert(city.getName() + " city has been deleted");
                a.initOwner(table.getScene().getWindow());
                a.show();
            }
        });

        menu.getItems().addAll(copy);
        menu.getItems().addAll(averageListPrice);
        menu.getItems().addAll(averageRentPrice);
        menu.getItems().addAll(listZipcodes);
        menu.getItems().addAll(delete);
        table.getSelectionModel().getTableView().setContextMenu(menu);
        return menu;

    }

    public ContextMenu getContextMenuForZipcodeTable(TableView table, MainScreenController MainScreenController) {
        ContextMenu menu = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        MenuItem averageListPrice = new MenuItem("Get Average List Price");
        MenuItem averageRentPrice = new MenuItem("Get Average Rent Price");
        MenuItem listListings = new MenuItem("List Listings");
        MenuItem delete = new MenuItem("Delete");

        copy.setOnAction((ActionEvent e) -> {
            ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(Integer.toString(zipcode.getZipcode())); // item is captured in the closure
            clipboard.setContent(content);
        });
        averageListPrice.setOnAction((ActionEvent e) -> {
           MainScreenController.getAverageListPriceByZipcodeNumBedsAndBaths();
        });
        averageRentPrice.setOnAction((ActionEvent e) -> {
           MainScreenController.getAverageRentByZipcodeNumBedsAndBaths();
        });
        listListings.setOnAction((ActionEvent e) -> {
            MainScreenController.listListingsForZipCodeId();
        });
        delete.setOnAction((ActionEvent e) -> {
            Window mainStage = MainScreenController.getPrimaryBorderPane().getScene().getWindow();
            Alert confirmationBeforeDeleteAlert = null;
            ZipCode zipcode = (ZipCode) table.getSelectionModel().getSelectedItem();
            confirmationBeforeDeleteAlert = Alerts.getInstance().getConfirmationAlert("Are you sure you want to delete " + zipcode.getZipcode() + " zipcode from the database?");
            confirmationBeforeDeleteAlert.initOwner(mainStage);
            confirmationBeforeDeleteAlert.showAndWait();
            if (confirmationBeforeDeleteAlert.getResult() == ButtonType.OK) {
                //Getting the city id to update the zipcode state
                int cityId = zipcode.getCityId();
                Datasource.getInstance().deleteZipcodeByZipcodeNumberAndZipcodeCity(zipcode.getZipcode(), zipcode.getCityId());
                TableViewEditor.getInstance().setColumnsForZipcodesTable(table);
                /* Removing some buttons not applicable at this point */

                HBox hboxOfButtons = MainScreenController.getButtons();
                ButtonsModifier.getInstance().setButtonsForZipcodesTable(MainScreenController, hboxOfButtons);
                /* Queries database and populates the table view with cities names */
                Task<ObservableList<ZipCode>> task = new GetAllZipcodesForACityTask(cityId);
                table.itemsProperty().bind(task.valueProperty());
                new Thread(task).start();
                Alert a = Alerts.getInstance().getConfirmationAlert(zipcode.getZipcode() + " zipcode has been deleted");
                a.initOwner(table.getScene().getWindow());
                a.show();
            }
        });

        menu.getItems().addAll(copy);
        menu.getItems().addAll(averageListPrice);
        menu.getItems().addAll(averageRentPrice);
        menu.getItems().addAll(listListings);
        menu.getItems().addAll(delete);
        table.getSelectionModel().getTableView().setContextMenu(menu);
        return menu;

    }

    public ContextMenu getContextMenuForListingTable(TableView table, MainScreenController MainScreenController) {
        ContextMenu menu = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        MenuItem copyURL = new MenuItem("Copy URL");
        MenuItem copyAddress = new MenuItem("Copy Address");
        MenuItem copyListPrice = new MenuItem("Copy List Price");
        MenuItem delete = new MenuItem("Delete");

        copy.setOnAction((ActionEvent e) -> {
            Listing listing = (Listing) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(listing.getUrl()); // item is captured in the closure
            clipboard.setContent(content);
        });

        copyAddress.setOnAction(EventHandler -> {
            Listing listing = (Listing) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(listing.getAddress()); // item is captured in the closure
            clipboard.setContent(content);

        });
        copyListPrice.setOnAction(EventHandler -> {
            Listing listing = (Listing) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(listing.getListPrice()); // item is captured in the closure
            clipboard.setContent(content);
        });
        copyURL.setOnAction(EventHandler -> {
            Listing listing = (Listing) table.getSelectionModel().getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(listing.getUrl()); // item is captured in the closure
            clipboard.setContent(content);
        });
        delete.setOnAction((ActionEvent e) -> {
            Window mainStage = MainScreenController.getPrimaryBorderPane().getScene().getWindow();
            Alert confirmationBeforeDeleteAlert = null;
            Listing listing = (Listing) table.getSelectionModel().getSelectedItem();
            confirmationBeforeDeleteAlert = Alerts.getInstance().getConfirmationAlert("Are you sure you want to delete " + listing.getAddress() + " from the database?");
            confirmationBeforeDeleteAlert.initOwner(mainStage);
            confirmationBeforeDeleteAlert.showAndWait();
            if (confirmationBeforeDeleteAlert.getResult() == ButtonType.OK) {
                //Get zipcode id to refresh the listing table
                int zipcodeId = listing.getZipcode();
                Datasource.getInstance().deleteListingById(listing.getId());
                TableViewEditor.getInstance().setColumnsForListingsTable(table);
                /* Removing some buttons not applicable at this point */

                HBox hboxOfButtons = MainScreenController.getButtons();
                ButtonsModifier.getInstance().setButtonsForListingsTable(MainScreenController, hboxOfButtons);
                /* Queries database and populates the table view with cities names */
                Task<ObservableList<Listing>> task = new GetListingsForAZipcodeTask(zipcodeId);
                table.itemsProperty().bind(task.valueProperty());
                new Thread(task).start();
                Alert a = Alerts.getInstance().getConfirmationAlert("Listing with id " + listing.getId() + " has been deleted");
                a.initOwner(table.getScene().getWindow());
                a.show();
            }
        });

        menu.getItems().addAll(copy);
        menu.getItems().addAll(copyAddress);
        menu.getItems().addAll(copyListPrice);
        menu.getItems().addAll(copyURL);
        menu.getItems().addAll(delete);
        table.getSelectionModel().getTableView().setContextMenu(menu);
        return menu;

    }

}
