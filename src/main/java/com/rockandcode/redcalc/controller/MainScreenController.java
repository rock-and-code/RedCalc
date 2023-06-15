package com.rockandcode.redcalc.controller;

import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.service.CityService;
import com.rockandcode.redcalc.service.CityServiceImpl;
import com.rockandcode.redcalc.service.FairRentService;
import com.rockandcode.redcalc.service.FairRentServiceImpl;
import com.rockandcode.redcalc.service.ImportDataFromCSVService;
import com.rockandcode.redcalc.service.ImportDataFromCSVServiceImpl;
import com.rockandcode.redcalc.service.ListingService;
import com.rockandcode.redcalc.service.ListingServiceImpl;
import com.rockandcode.redcalc.service.RentService;
import com.rockandcode.redcalc.service.RentServiceImpl;
import com.rockandcode.redcalc.service.StateService;
import com.rockandcode.redcalc.service.StateServiceImpl;
import com.rockandcode.redcalc.service.UtilityService;
import com.rockandcode.redcalc.service.UtilityServiceImpl;
import com.rockandcode.redcalc.service.ZipcodeService;
import com.rockandcode.redcalc.service.ZipcodeServiceImpl;
import com.rockandcode.redcalc.ui.App;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainScreenController {

    public static final String UPDATE_CITY_DIALOG_FXML = "update_city_dialog.fxml";
    public static final String INSERT_LISTING_DIALOG_FXML = "insert_sales_listing_dialog.fxml";
    public static final String GET_NUM_OF_BEDS_AND_BATHS_DIALOG_FXML = "get_num_of_beds_and_baths_dialog.fxml";
    public static final String GET_LISTINGS_BY_UNDERWRITTEN_VAL_DIALOG_FXML = "get_listings_by_underwritten_val.fxml";
    public static final String GET_FAIR_RENT_BY_ZIPCODE_DIALOG_FXML = "get_fair_rent_rate_by_zipcode.fxml";
    public static final String GET_DOWNLOAD_RENT_LISTINGS_DIALOG_FXML = "download_rent_listing_dialog.fxml";
    public static final String CALCULATE_PROPERTY_NOI_AND_DEBT_SERVICE = "calculate_property_noi_and_debt_service";
    public static final String CALCULATE_MORTGAGE_PAYMENT = "calculate_mortgage_payment";

    /* For navigation purposes only */
    private Object previous;
    private City currentCity;

    @FXML
    private TableView table;
    @FXML
    public BorderPane borderPane;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public TextField progressBarMessage;
    @FXML
    public HBox buttonsContainer;

    @FXML
    public ContextMenu contextMenu;

    public void setPrevious(Object obj) {
        previous = obj;
    }
    
    public void setCurrentCity(City city) {
        currentCity = city;
    }
    
    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }
    
    public ContextMenu getContextMenu() {
        return contextMenu;
    }
    
    public HBox getButtons() {
        return buttonsContainer;
    }

    public BorderPane getPrimaryBorderPane() {
        return borderPane;
    }

    private final StateService stateService = new StateServiceImpl(this, contextMenu);
    private final CityService cityService = new CityServiceImpl(this, contextMenu);
    private final ZipcodeService zipcodeService = new ZipcodeServiceImpl(this);
    private final ListingService listingService = new ListingServiceImpl(this, contextMenu);
    private final FairRentService fairRentService = new FairRentServiceImpl();
    private final RentService rentService = new RentServiceImpl(this, contextMenu);
    private final ImportDataFromCSVService importDataFromCSVService = new ImportDataFromCSVServiceImpl(this, contextMenu);
    private final UtilityService clearDataService = new UtilityServiceImpl(this);


    @FXML
    public void listStates() {
        // Finds all states in the database.
        //
        // Returns:
        // None.
        stateService.listStates(table, progressBar, buttonsContainer);
    }

    @FXML
    public void listCitiesForState() {
        // Finds all cities in the specified state.
        //
        // Parameters:
        // state - The state selected on the table view to find cities in.
        //
        // Returns:
        // None.
        //
        stateService.listCitiesForState(table, borderPane, buttonsContainer);
    }

    @FXML
    public void listZipcodesForCity() {
        // Finds all zipcodes in the specified city.
        //
        // Parameters:
        // city - The city selected on the table view to find zipcodes in.
        //
        // Returns:
        // None.
        cityService.listZipcodesForCity(table, borderPane, buttonsContainer);
    }

    @FXML
    public void listListingsForZipCodeId() {
        // Finds all listings by zipcode id.
        //
        // Parameters:
        // zipcode id - The id of the zipcode selected on the table view to find listings in.
        //
        // Returns:
        // None.
        zipcodeService.findListingsByZipCodeId(table, borderPane, buttonsContainer);
    }

    private void listListingForZipCodeNumber(int zipcode) {
        // Lists listings for a specific zipcode.
        //
        // Parameters:
        // zipcode - The zipcode to list listings for.
        //
        // Returns:
        // None.
        zipcodeService.listListingForZipCodeNumber(zipcode, table, buttonsContainer);
    }
    
    @FXML
    private void handleExit() {
        // Handles the exit functionality of the application.
        //
        // Returns:
        // None.
        Platform.exit();
    }

    @FXML
    private void switchToCalculatePropertyNOIAndDebtServiceController() throws IOException {
        // Switches to the Calculate Property NOI and Debt Service Controller.
        //
        // Returns:
        // None.
        App.setRoot(CALCULATE_PROPERTY_NOI_AND_DEBT_SERVICE);
    }
    
    @FXML
    private void switchToCalculateMortgagePaymentController() throws IOException {
        // Switches to the Calculate Mortgage Payment Controller.
        //
        // Returns:
        // None.
        App.setRoot(CALCULATE_MORTGAGE_PAYMENT);
    }

    @FXML
    public void updateCity() {
        // Updates a city in the table view.
        //
        // Returns:
        // None.
        cityService.updateCity(table, borderPane, buttonsContainer);
    }

    @FXML
    private void insertListing() {
        // Inserts a new listing into the table view.
        //
        // Returns:
        // None.
        listingService.insertListing(table, borderPane, buttonsContainer);
    }

    @FXML
    private void deleteCity() {
        // Deletes a city from the table view.
        //
        // Returns:
        // None.
        cityService.deleteCity(table, borderPane);
    }

    @FXML
    public void readListingsFromRedfinCSV() {
        // Reads listings from a Redfin CSV file.
        //
        // Returns:
        // None.
        importDataFromCSVService.readListingsFromRedfinCSV(table, borderPane, progressBar, progressBarMessage, buttonsContainer, previous);
    }

    @FXML
    public void readMarketRentsFromCSV() {
        // Reads market rents from a CSV file.
        //
        // Returns:
        // None.
        importDataFromCSVService.readMarketRentsFromCSV(table, borderPane, progressBar, progressBarMessage);

    }
    
    @FXML
    public void readMarketRentsFromSQLITECSV() {
        // Reads market rents from a SQLite CSV file.
        //
        // Returns:
        // None.
        importDataFromCSVService.readMarketRentsFromSQLITECSV(table, borderPane, progressBar, progressBarMessage);

    }

    @FXML
    public void readFairRentsFromCSV() {
        // Reads fair rents from a CSV file and stores them in the database.
        importDataFromCSVService.readFairRentsFromCSV(table, borderPane, progressBar, progressBarMessage);
    }

    @FXML
    public void clearDatabase() {
        // Clears the database of all data.
        clearDataService.clearDatabase(table, borderPane, progressBar, buttonsContainer);
    }

    @FXML
    public void clearFairRents() {
        // Deletes all fair rents from the database.
        fairRentService.deleteFairRents(table, borderPane);
    }
    
    @FXML
    public void clearMarketRents() {
        // Deletes all market rents from the database.
        rentService.deleteMarketRents(table, borderPane);
    }

    @FXML
    public void getAverageListPriceByZipcodeNumBedsAndBaths() {
        // Finds the average list price for a given zip code, number of beds, and number of baths.
        zipcodeService.findAverageListPriceByZipcodeNumBedsAndBaths(table, borderPane);

    }

    @FXML
    public void getAverageListPriceByCityNumBedsAndBaths() {
        // Finds the average list price for a given city, number of beds, and number of baths.
        cityService.findAverageListPriceByCityNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageListPriceByStateNumBedsAndBaths() {
        // Finds the average list price for a given state, number of beds, and number of baths.
        stateService.findAverageListPriceByStateIdBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getBack() {
        // Goes back to the previous screen.
        clearDataService.getBack(table, buttonsContainer, previous, currentCity);
    }

    @FXML
    public void getAverageRentByStateNumBedsAndBaths() {
        // Finds the average rent for a given state, number of beds, and number of baths.
        stateService.findAverageRentByStateIdBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageRentByCityNumBedsAndBaths() {
        // Finds the average rent for a given city, number of beds, and number of baths.
        cityService.findAverageRentByCityNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageRentByZipcodeNumBedsAndBaths() {
        // Finds the average rent for a given zip code, number of beds, and number of baths.
        zipcodeService.findAverageRentByZipcodeNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getListingByZipcodeAndUnderwrittenVal() {
        // Finds a listing by zip code and underwritten value.
        zipcodeService.findListingByZipcodeAndUnderwrittenVal(table, borderPane, buttonsContainer);
    }

    @FXML
    public void getListingByCityAndUnderwrittenVal() {
        // Finds a listing by city and underwritten value.
        cityService.findListingByCityAndUnderwrittenVal(table, borderPane, buttonsContainer);
    }

    @FXML
    public void getFairRentRateByZipcodeAndBed() {
        // Finds the fair rent rate for a given zip code and number of beds.
        fairRentService.getFairRentRateByZipcodeAndBed(borderPane);
    }

    @FXML
    public void downloadRentListingByCityAndState() {
        // Downloads rent listings for a given city and state.
        rentService.downloadRentListingByCityAndState(table, borderPane, progressBar, progressBarMessage);
    }

}

