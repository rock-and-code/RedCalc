package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.DownloadRentListingDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import static com.rockandcode.redcalc.controller.MainScreenController.GET_DOWNLOAD_RENT_LISTINGS_DIALOG_FXML;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.RentListing;
import com.rockandcode.redcalc.repository.MarketRentRepository;
import com.rockandcode.redcalc.ui.App;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.Dialogs;
import com.rockandcode.redcalc.util.GetRealtyMolePropertyJSON;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 *
 * @author riost02
 */
public class RentServiceImpl implements RentService, GetRealtyMolePropertyJSON.OnDataAvailable {

    private final MainScreenController mc;
    private ContextMenu contextMenu;
    private final MarketRentRepository marketRentRepository;

    public RentServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.marketRentRepository = new MarketRentRepository(Datasource.getInstance());
    }

    @Override
    public void downloadRentListingByCityAndState(TableView table, BorderPane borderPane,
            ProgressBar progressBar, TextField progressBarMessage) {
        /* Creating a new instance of the dialog class */
        Dialog<ButtonType> dialog = Dialogs.getInstance().getDownloadRentListingDialog();
        /* to select main windows and change it to dialog pane, instead of openning a new window */
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmLoader = new FXMLLoader();
        fxmLoader.setLocation(App.class.getResource(GET_DOWNLOAD_RENT_LISTINGS_DIALOG_FXML));
        try {
            dialog.getDialogPane().setContent(fxmLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Error: Couldn't load the dialog", e);
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            DownloadRentListingDialogController controller = fxmLoader.getController();
            try {
                final Map<String, String> data = controller.getNewListingInfo();
                GetRealtyMolePropertyJSON task = new GetRealtyMolePropertyJSON(this, data.get("city"), data.get("state"));

                progressBar.progressProperty().bind(task.progressProperty());
                progressBar.setVisible(true);
                progressBarMessage.promptTextProperty().bind(task.messageProperty());
                progressBarMessage.setVisible(true);
                task.setOnSucceeded(e -> {
                    progressBar.setVisible(false);
                    progressBarMessage.setVisible(false);
                    Alert a = Alerts.getInstance().getConfirmationAlert("Rent listings from " + data.get("city")
                            + " has been succesfully added to the database");
                    a.initOwner(borderPane.getScene().getWindow());
                    a.show();
                    //REFRESH CITY TABLE

                    table.refresh();
                });
                task.setOnFailed(e -> {
                    progressBar.setVisible(false);
                    progressBarMessage.setVisible(false);
                    Alert a = Alerts.getInstance().getErrorAlert("Unable to add rent listings from " + data.get("city") + " to the database");
                    a.initOwner(borderPane.getScene().getWindow());
                    a.show();
                });
                new Thread(task).start();
            } catch (RuntimeException e) {
                Alert a = Alerts.getInstance().getErrorAlert(e.toString());
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
            }
        } else {
            ConsoleLogger.getInstance().printMessage("Cancel pressed");
        }
    }

    /**
     * Callback to insert rent listings into the database
     *
     * @param rentListings
     * @param task
     */
    @Override
    public void onDataAvailable(GetRealtyMolePropertyJSON task, List<RentListing> rentListings) {
        for (RentListing listing : rentListings) {
            String address = listing.getAddress();
            String propertyType = listing.getPropertyType();
            int zipcode = listing.getZipcode();
            String listedDate = listing.getListedDate();
            int rent = listing.getRent();
            int bedroom = listing.getBedrooms();
            double bathrooms = listing.getBathrooms();
            int squareFootage = listing.getSquareFootage();
            String city = listing.getCity();
            String state = listing.getState();
            marketRentRepository.saveRentRate(address, propertyType, listedDate, zipcode, rent, bedroom, bathrooms, squareFootage, city, state);
            task.updateProgress(task.getProgressCounter(), task.getTotalRentListings());
            task.updateMessage("Adding " + address);
            task.incrementProgressCounter();
        }
    }

    @Override
    public void clearMarketRents(TableView table, BorderPane borderPane) {
        Window mainStage = borderPane.getScene().getWindow();
        /* Creating a new instance of the dialog class */
        Alert a = Alerts.getInstance().getConfirmationAlert("Are you sure you want to clear the market rents database?");
        a.initOwner(mainStage);
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            marketRentRepository.deleteMarketRents();
            //Window mainStage = borderPane.getScene().getWindow();
            //Refresh table
            table.refresh();
            a = Alerts.getInstance().getInformationAlert("Markets rates were successfully deleted from the database");
            a.initOwner(mainStage);
            a.show();
        } else {
            //CANCEL BUTTON PRESSED!
        }

    }

}
