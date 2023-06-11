
package com.rockandcode.redcalc.service;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public interface ListingService {
    public void insertListing(TableView table, BorderPane borderPane, HBox buttonsContainer);
    public void deleteListings();
}
