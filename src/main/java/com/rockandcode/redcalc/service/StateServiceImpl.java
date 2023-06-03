package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.RealEstateState;
import com.rockandcode.redcalc.repository.StateRepository;
import com.rockandcode.redcalc.task.GetAllStatesTask;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ButtonsModifier;
import com.rockandcode.redcalc.util.RedCalcContextMenu;
import com.rockandcode.redcalc.util.TableViewEditor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author riost02
 */
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

}
