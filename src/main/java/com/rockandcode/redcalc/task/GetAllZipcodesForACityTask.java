
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.ZipCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllZipcodesForACityTask extends Task {
    private final int mCityId;

    public GetAllZipcodesForACityTask(int cityId) {
        this.mCityId = cityId;
    }
    
    @Override
    public ObservableList<ZipCode> call() throws Exception {
        return FXCollections.observableArrayList(Datasource.getInstance().findZipCodesForCityId(mCityId));
    }

}
