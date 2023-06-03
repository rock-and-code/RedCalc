
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.RealEstateState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllStatesTask extends Task {
    @Override
    public ObservableList<RealEstateState> call() throws Exception {

        return FXCollections.observableArrayList(Datasource.getInstance().findStatesForTableView());
    }
}
