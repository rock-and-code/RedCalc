
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.ZipCode;
import com.rockandcode.redcalc.repository.CityRepository;
import com.rockandcode.redcalc.repository.ZipcodeRepository;
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

/**
 *
 * @author riost02
 */
public class UtilityServiceImpl implements UtilityService{
    
    private final MainScreenController mc;
    private final StateService stateService;
    private final CityRepository cityRepository;
    private final ZipcodeRepository ZipcodeRepository;

    public UtilityServiceImpl(MainScreenController mc) {
        this.mc = mc;
        this.stateService = new StateServiceImpl(mc, mc.getContextMenu());
        this.cityRepository = new CityRepository(Datasource.getInstance());
        this.ZipcodeRepository = new ZipcodeRepository(Datasource.getInstance());
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
            Datasource.getInstance().deleteListingsTableData();
            Datasource.getInstance().deleteZipcodesTableData();
            Datasource.getInstance().deleteCitiesTableData();
            Datasource.getInstance().deleteStatesTableData();
            Datasource.getInstance().deleteMarketRentTableData();
            Datasource.getInstance().deleteFairMarketRentTableData();
            //Window mainStage = borderPane.getScene().getWindow();
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
                            cityRepository.findCitiesByStateIdForTableView(city.getStateId())
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
                            ZipcodeRepository.findZipcodesByCityIdForTableView(Zipcode.getCityId())
                    );
                }
            };
            table.itemsProperty().bind(task.valueProperty());
            task.setOnSucceeded(e-> mc.setContextMenu(RedCalcContextMenu.getInstance().getContextMenuForZipcodeTable(table, mc)));
            new Thread(task).start();
        }
    }

}
