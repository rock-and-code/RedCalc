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
import com.rockandcode.redcalc.util.ConsoleLogger;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainScreenController {

    public static final String UPDATE_CITY_DIALOG_FXML = "update_city_dialog.fxml";
    public static final String INSERT_LISTING_DIALOG_FXML = "insert_sales_listing_dialog.fxml";
    public static final String GET_AVG_LIST_PRICE_BY_ZIPCODE_BEDS_BATHS_DIALOG_FXML = "get_average_list_price_by_zipcode_beds_baths_dialog.fxml";
    public static final String GET_AVG_LIST_PRICE_BY_CITY_BEDS_BATHS_DIALOG_FXML = "get_average_list_price_by_city_beds_baths_dialog.fxml";
    public static final String GET_LISTINGS_BY_ZIPCODE_AND_UNDERWRITTEN_VAL_DIALOG_FXML = "get_listings_by_zipcode_and_underwritten_val.fxml";
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
    
    private final StateService stateService = new StateServiceImpl(this, contextMenu);
    private final CityService cityService = new CityServiceImpl(this, contextMenu);
    private final ZipcodeService zipcodeService = new ZipcodeServiceImpl(this, contextMenu);
    private final ListingService listingService = new ListingServiceImpl(this, contextMenu);
    private final FairRentService fairRentService = new FairRentServiceImpl();
    private final RentService rentService = new RentServiceImpl(this, contextMenu);
    private final ImportDataFromCSVService importDataFromCSVService = new ImportDataFromCSVServiceImpl(this, contextMenu);
    private final UtilityService clearDataService = new UtilityServiceImpl(this);

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

    @FXML
    public void listStates() {
        stateService.listStates(table, progressBar, buttonsContainer);
    }

    @FXML
    public void listCitiesForState() {
        stateService.listCitiesForState(table, borderPane, buttonsContainer);
    }

    @FXML
    public void listZipcodesForCity() {
        cityService.listZipcodesForCity(table, borderPane, buttonsContainer);
    }

    @FXML
    public void listListingsForZipCodeId() {
        zipcodeService.listListingsForZipCodeId(table, borderPane, buttonsContainer);
    }

    private void listListingForZipCodeNumber(int zipcode) {
        zipcodeService.listListingForZipCodeNumber(zipcode, table, buttonsContainer);
    }
    
    @FXML
    private void handleExit() throws IOException {
        Platform.exit();
    }

    @FXML
    private void switchToCalculatePropertyNOIAndDebtServiceController() throws IOException {
        App.setRoot(CALCULATE_PROPERTY_NOI_AND_DEBT_SERVICE);
    }
    
    @FXML
    private void switchToCalculateMortgagePaymentController() throws IOException {
        App.setRoot(CALCULATE_MORTGAGE_PAYMENT);
    }

    @FXML
    public void updateCity() {
        cityService.updateCity(table, borderPane, buttonsContainer);
    }

    @FXML
    private void insertListing() {
        listingService.insertListing(table, borderPane, buttonsContainer);
    }

    @FXML
    private void deleteCity() {
        cityService.deleteCity(table, borderPane);
    }

    @FXML
    public void readListingsFromRedfinCSV() {
        importDataFromCSVService.readListingsFromRedfinCSV(table, borderPane, progressBar, progressBarMessage, buttonsContainer, previous);
    }

    @FXML
    public void readMarketRentsFromCSV() {
        importDataFromCSVService.readMarketRentsFromCSV(table, borderPane, progressBar, progressBarMessage);

    }
    
    @FXML
    public void readMarketRentsFromSQLITECSV() {
        importDataFromCSVService.readMarketRentsFromSQLITECSV(table, borderPane, progressBar, progressBarMessage);

    }

    @FXML
    public void readFairRentsFromCSV() {
        ConsoleLogger.getInstance().printMessage("readFairRentsFROMSCV called");
        importDataFromCSVService.readFairRentsFromCSV(table, borderPane, progressBar, progressBarMessage);
    }

    @FXML
    public void clearDatabase() {
        clearDataService.clearDatabase(table, borderPane, progressBar, buttonsContainer);
    }

    @FXML
    public void clearFairRents() {
        fairRentService.clearFairRents(table, borderPane);
    }
    
    @FXML
    public void clearMarketRents() {
        rentService.clearMarketRents(table, borderPane);
    }

    @FXML
    public void getAverageListPriceByZipcodeNumBedsBaths() {
        zipcodeService.getAverageListPriceByZipcodeNumBedsBaths(table, borderPane);

    }

    @FXML
    public void getAverageListPriceByCityNumBedsBaths() {
        cityService.getAverageListPriceByCityNumBedsBaths(table, borderPane);
    }

    @FXML
    public void getBack() { //From zipcode table view to City table view
        clearDataService.getBack(table, buttonsContainer, previous, currentCity);
    }

    @FXML
    public void getAverageRentByCityNumBedsBaths() {
        cityService.getAverageRentByCityNumBedsBaths(table, borderPane);
    }

    @FXML
    public void getAverageRentByZipcodeNumBedsBaths() {
        zipcodeService.getAverageRentByZipcodeNumBedsBaths(table, borderPane);
    }

    @FXML
    public void getListingByZipcodeAndUnderwrittenVal() {
        zipcodeService.getListingByZipcodeAndUnderwrittenVal(table, borderPane, buttonsContainer);
    }

    @FXML
    public void getListingByCityAndUnderwrittenVal() {
        cityService.getListingByCityAndUnderwrittenVal(table, borderPane, buttonsContainer);
    }

    @FXML
    public void getFairRentRateByZipcodeAndBed() {
        fairRentService.getFairRentRateByZipcodeAndBed(borderPane);
    }

    @FXML
    public void downloadRentListingByCityAndState() {
        rentService.downloadRentListingByCityAndState(table, borderPane, progressBar, progressBarMessage);
    }

}

