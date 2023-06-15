
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.ZipCode;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ButtonsModifier;
import com.rockandcode.redcalc.util.RedCalcContextMenu;
import com.rockandcode.redcalc.util.TableViewEditor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class UtilityServiceImpl implements UtilityService{
    
    private final MainScreenController mc;
    private final StateService stateService;
    private final CityService cityService;
    private final ZipcodeService zipcodeService;
    private final ListingService listingService;
    private final RentService rentService;
    private final FairRentService fairRentService;

    public UtilityServiceImpl(MainScreenController mc) {
        this.mc = mc;
        this.stateService = new StateServiceImpl(mc, mc.getContextMenu());
        this.cityService = new CityServiceImpl(mc, mc.getContextMenu());
        this.zipcodeService = new ZipcodeServiceImpl(mc);
        this.listingService = new ListingServiceImpl(mc, mc.getContextMenu());
        this.rentService = new RentServiceImpl(mc, mc.getContextMenu());
        this.fairRentService = new FairRentServiceImpl();
    }

    @Override
    public void clearDatabase(TableView table, BorderPane borderPane, ProgressBar progressBar,
            HBox buttonsContainer) {
        Window mainStage = borderPane.getScene().getWindow();
        /* Creating a new instance of the dialog class */
        Alert a = Alerts.getInstance().getConfirmationAlert("Are you sure you want to clear the database?");
        a.initOwner(mainStage);
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            listingService.deleteListings();
            zipcodeService.deleteZipcodes();
            cityService.deleteCities();
            stateService.deleteStates();
            rentService.deleteMarketRents();
            fairRentService.deleteFairRents();
            //Refresh table
            stateService.listStates(table, progressBar, buttonsContainer);
            a = Alerts.getInstance().getInformationAlert("Database was successfully deleted");
            a.initOwner(mainStage);
            a.show();
        } else {
            //CANCEL BUTTON PRESSED!
        }
    }

    @Override
    public void getBack(TableView table, HBox buttonsContainer, Object previous, City currentCity) {
        if (previous instanceof City) {
            City city = (City) previous;
            previous = null;
            //ConsoleLogger.getInstance().printMessage(city.toString());
            /* Removing some buttons not applicable at this point */
            ButtonsModifier.getInstance().setButtonsForCitiesTable(mc, buttonsContainer);
            //Adding columns to the cityTable dynamically
            TableViewEditor.getInstance().setColumnsForCitiesTable(table);
            Task<ObservableList<City>> task = new Task<ObservableList<City>>() {
                @Override
                protected ObservableList<City> call() throws Exception {
                    return FXCollections.observableArrayList(
                            cityService.findCitiesByStateIdForTableView(city.getStateId())
                    );
                }
            };
            table.itemsProperty().bind(task.valueProperty());
            task.setOnSucceeded(e-> mc.setContextMenu(RedCalcContextMenu.getInstance().getContextMenuForCityTable(table, mc)));
            new Thread(task).start();
        } else if (previous instanceof ZipCode) { //From listing table view to Zipcode table view
            ZipCode Zipcode = (ZipCode) previous;
            mc.setPrevious(currentCity);
            /* Removing some buttons not applicable at this point */
            ButtonsModifier.getInstance().setButtonsForZipcodesTable(mc, buttonsContainer);
            //Adding columns to the cityTable dynamically
            TableViewEditor.getInstance().setColumnsForZipcodesTable(table);
            Task<ObservableList<ZipCode>> task = new Task<ObservableList<ZipCode>>() {
                @Override
                protected ObservableList<ZipCode> call() throws Exception {
                    return FXCollections.observableArrayList(
                            zipcodeService.findZipcodesByCityIdForTableView(Zipcode.getCityId())
                    );
                }
            };
            table.itemsProperty().bind(task.valueProperty());
            task.setOnSucceeded(e-> mc.setContextMenu(RedCalcContextMenu.getInstance().getContextMenuForZipcodeTable(table, mc)));
            new Thread(task).start();
        }
    }

}
