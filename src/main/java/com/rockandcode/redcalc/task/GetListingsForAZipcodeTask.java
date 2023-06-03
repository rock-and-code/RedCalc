
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.Listing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetListingsForAZipcodeTask extends Task {
    private final int mZipcodeId;

    public GetListingsForAZipcodeTask(int zipcodeId) {
        this.mZipcodeId = zipcodeId;
    }
    
    @Override
    public ObservableList<Listing> call() throws Exception {
        return FXCollections.observableArrayList(Datasource.getInstance().findListingsByZipcodeId(mZipcodeId));
    }

}
