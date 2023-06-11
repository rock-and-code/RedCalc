
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.Listing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetListingsForAZipcodeTask extends Task {
    // The zipcode ID of the listings to retrieve.
    private final int mZipcodeId;

    /**
     * Constructs a new GetListingsForAZipcodeTask.
     *
     * @param zipcodeId The zipcode ID of the listings to retrieve.
     */
    public GetListingsForAZipcodeTask(int zipcodeId) {
        this.mZipcodeId = zipcodeId;
    }
    /**
     * Returns an ObservableList of Listings for the specified zipcode ID.
     *
     * @throws Exception If an error occurs while retrieving the listings.
     */
    @Override
    public ObservableList<Listing> call() throws Exception {
        // Get the and return listings from the data source.
        return FXCollections.observableArrayList(Datasource.getInstance().findListingsByZipcodeId(mZipcodeId));
    }

}
