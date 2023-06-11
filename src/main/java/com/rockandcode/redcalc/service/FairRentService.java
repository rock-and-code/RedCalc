
package com.rockandcode.redcalc.service;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public interface FairRentService {
    public void getFairRentRateByZipcodeAndBed(BorderPane borderPane);
    public void deleteFairRents(TableView table, BorderPane borderPane);
    public void deleteFairRents();
}
