
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllCitiesForAStateTask extends Task {
    private final int mStateId;

    public GetAllCitiesForAStateTask(int stateId) {
        this.mStateId = stateId;
    }
    
    @Override
    public ObservableList<City> call() throws Exception {
        return FXCollections.observableArrayList(Datasource.getInstance().findCitiesForStateId(mStateId));
    }

}