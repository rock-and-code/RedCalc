
package com.rockandcode.redcalc.service;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author riost02
 */
public interface FairRentService {
    public void getFairRentRateByZipcodeAndBed(BorderPane borderPane);
    public void clearFairRents(TableView table, BorderPane borderPane);
}
