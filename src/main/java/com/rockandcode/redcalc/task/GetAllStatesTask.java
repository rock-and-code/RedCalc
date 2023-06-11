
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.RealEstateState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllStatesTask extends Task {
    /**
     * Returns an ObservableList of RealEstateStates for the table view.
     *
     * @throws Exception If an error occurs while retrieving the states.
     */
    @Override
    public ObservableList<RealEstateState> call() throws Exception {
        // Get and return the states from the data source.
        return FXCollections.observableArrayList(Datasource.getInstance().findStatesForTableView());
    }
}
