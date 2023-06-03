
package com.rockandcode.redcalc.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;

import com.rockandcode.redcalc.controller.GetFairRentRateByZipcodeDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.BedsAndZipcodeDTO;
import com.rockandcode.redcalc.model.FairRentRates;
import com.rockandcode.redcalc.repository.FairMarketRentRepository;
import com.rockandcode.redcalc.ui.App;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 *
 * @author riost02
 */
public class FairRentServiceImpl implements FairRentService {
    
    private final FairMarketRentRepository fairMarketRentRepository;

    public FairRentServiceImpl() {
        this.fairMarketRentRepository = new FairMarketRentRepository(Datasource.getInstance());
    }
    

    @Override
    public void getFairRentRateByZipcodeAndBed(BorderPane borderPane) {
        int numBeds = 0, zip = 1, beds, zipcode;
        FairRentRates fmr = null;
        //Getting parameters for db quer
        BedsAndZipcodeDTO data = null;
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getFairRentRateByZipcodeDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(MainScreenController.GET_FAIR_RENT_BY_ZIPCODE_DIALOG_FXML));
        try {
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            GetFairRentRateByZipcodeDialogController controller = fxmLoader.getController();
            data = controller.getBedsAndZipcode();
            beds = data.getNumBeds();
            try {
                zipcode = Integer.parseInt(data.getZipcode());
                fmr = fairMarketRentRepository.findFairMarketRentRatesByZipcode(zipcode);

                Alert a;
                if (fmr == null) {
                    a = Alerts.getInstance().getInformationAlert("The are no fair market rent rates that matches given criteria");
                } else {
                    DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
                    String output = myFormatter.format(fmr.getFairRentRate(beds));
                    a = Alerts.getInstance().getInformationAlert("The fair market rent is " + output);

                }
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
            } catch (NumberFormatException | SQLException e) {
                Alert a = Alerts.getInstance().getErrorAlert("Zipcode must be a numberic value!");
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
            }
        } else {
            ConsoleLogger.getInstance().printMessage("Cancel pressed");
        }
    }

    @Override
    public void clearFairRents(TableView table, BorderPane borderPane) {
        Window mainStage = borderPane.getScene().getWindow();
        /* Creating a new instance of the dialog class */
        Alert a = Alerts.getInstance().getConfirmationAlert("Are you sure you want to clear the fair rents database?");
        a.initOwner(mainStage);
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            fairMarketRentRepository.deleteFairMarketRent();
            //Window mainStage = borderPane.getScene().getWindow();
            //Refresh table
            table.refresh();
            a = Alerts.getInstance().getInformationAlert("Fair markets rates were successfully deleted from the database");
            a.initOwner(mainStage);
            a.show();
        } else {
            //CANCEL BUTTON PRESSED!
        }
    }
}
