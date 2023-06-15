
package com.rockandcode.redcalc.service;

import com.rockandcode.redcalc.controller.InsertListingDialogController;
import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.ListingCity;
import com.rockandcode.redcalc.repository.CityRepository;
import com.rockandcode.redcalc.repository.ListingRepository;
import com.rockandcode.redcalc.repository.ZipcodeRepository;
import com.rockandcode.redcalc.ui.App;
import com.rockandcode.redcalc.util.Alerts;
import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.Dialogs;
import java.io.IOException;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ListingServiceImpl implements ListingService {
    
    private final MainScreenController mc;
    private final ContextMenu contextMenu;
    private final ZipcodeService zipcodeService;
    private final ListingRepository listingRepository;
    private final ZipcodeRepository ZipcodeRepository;
    private final CityRepository cityRepository;

    public ListingServiceImpl(MainScreenController mc, ContextMenu contextMenu) {
        this.mc = mc;
        this.contextMenu = contextMenu;
        this.zipcodeService = new ZipcodeServiceImpl(mc);
        this.listingRepository = new ListingRepository(Datasource.getInstance());
        this.ZipcodeRepository = new ZipcodeRepository(Datasource.getInstance());
        this.cityRepository = new CityRepository(Datasource.getInstance());
    }

    @Override
    public void insertListing(TableView table, BorderPane borderPane, HBox buttonsContainer) {
        ListingCity listingCity;

        //Getting Dialog for inserting listings
        Dialog<ButtonType> dialog = Dialogs.getInstance().getInsertListingDialog();         //creates dialog
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource(MainScreenController.INSERT_LISTING_DIALOG_FXML));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            ConsoleLogger.getInstance().printErrorMessage("Couldn't load the dialog", e);
            return;
        }
        /* To catch the dialog buttons clicked events */
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /* To use methods from Dialog Controller */
            InsertListingDialogController controller = fxmlLoader.getController();

            try {
                listingCity = controller.getNewListingInfo();
            } catch (NumberFormatException e) {
                Alert a = Alerts.getInstance().getInsertListingDialogNumericErrorAlert();
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
                return;
            } catch (RuntimeException e) {
                Alert a = Alerts.getInstance().getInsertListingDialogEmptyFieldsErrorAlert();
                a.initOwner(borderPane.getScene().getWindow());
                a.show();
                return;
            }
            /* DEBUGGIN */
            /*ConsoleLogger.getInstance().printMessage("OK pressed" + " : city= " + listingCity.getCityName() + " : Zipcode "
                    + listingCity.getZipcode() + " : adress " + listingCity.getAddress());
            /* DEBUGGIN */
            /*ConsoleLogger.getInstance().printMessage("OK pressed" + " : city= " + listingCity.getCityName() + " : Zipcode "
                    + listingCity.getZipcode() + " : adress " + listingCity.getAddress());
             */

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    //adding listing in the database
                    return listingRepository.saveListing(
                            listingCity.getState(),
                            listingCity.getCityName(),
                            listingCity.getAddress(),
                            listingCity.getPropertyType(),
                            listingCity.getZipcode(),
                            listingCity.getListPrice(),
                            listingCity.getNumBeds(),
                            listingCity.getNumBaths(),
                            listingCity.getSquareFootage(),
                            listingCity.getYearBuilt(),
                            listingCity.getLatitude(),
                            listingCity.getLongitude(),
                            listingCity.getUrl());
                }
            };
            task.setOnSucceeded(e -> {
                if (task.valueProperty().get()) {
                    Alert a = Alerts.getInstance().getInsertListingDialogConfirmationAlert(listingCity.getAddress());
                    a.initOwner(borderPane.getScene().getWindow());
                    a.show();
                    zipcodeService.listListingForZipCodeNumber(listingCity.getZipcode(), table, buttonsContainer);
                    mc.setPrevious(ZipcodeRepository.findZipcodeByZipcodeNumber(listingCity.getZipcode()));
                    mc.setCurrentCity(cityRepository.findCityByName(listingCity.getCityName()));

                } else {
                    Alert a = Alerts.getInstance().getInsertListingDialogFailedAlert(listingCity.getAddress());
                    a.initOwner(borderPane.getScene().getWindow());
                    a.show();
                }
            });

            task.setOnFailed(e -> {
                ConsoleLogger.getInstance().printMessage("Failed to insert song in the database");
            });
            new Thread(task).start();

        } else {
            /* CANCEL BUTTON PRESSED */
            //ConsoleLogger.getInstance().printMessage("Cancel pressed");
        }
    }

    @Override
    public void deleteListings() {
        listingRepository.deleteListings();
    }

}
