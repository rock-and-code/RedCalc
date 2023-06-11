
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllCitiesForAStateTask extends Task {
    // The state ID of the cities to retrieve.
    private final int mStateId;
    /**
     * Constructs a new GetAllCitiesForAStateTask.
     *
     * @param stateId The state ID of the cities to retrieve.
     */
    public GetAllCitiesForAStateTask(int stateId) {
        this.mStateId = stateId;
    }
    /**
     * Returns an ObservableList of Cities for the specified state ID.
     *
     * @throws Exception If an error occurs while retrieving the cities.
     */
    @Override
    public ObservableList<City> call() throws Exception {
        // Get and return the cities from the data source.
        return FXCollections.observableArrayList(Datasource.getInstance().findCitiesForStateId(mStateId));
    }

}