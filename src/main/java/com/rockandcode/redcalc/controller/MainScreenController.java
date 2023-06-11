package com.rockandcode.redcalc.controller;

import java.io.IOException;

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
    public static final String GET_AVG_LIST_PRICE_BY_BEDS_BATHS_DIALOG_FXML = "get_average_list_price_by_beds_baths_dialog.fxml";
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
    private final ZipcodeService zipcodeService = new ZipcodeServiceImpl(this, contextMenu);
    private final ListingService listingService = new ListingServiceImpl(this, contextMenu);
    private final FairRentService fairRentService = new FairRentServiceImpl();
    private final RentService rentService = new RentServiceImpl(this, contextMenu);
    private final ImportDataFromCSVService importDataFromCSVService = new ImportDataFromCSVServiceImpl(this, contextMenu);
    private final UtilityService clearDataService = new UtilityServiceImpl(this);

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
        //ConsoleLogger.getInstance().printMessage("readFairRentsFROMSCV called");
        importDataFromCSVService.readFairRentsFromCSV(table, borderPane, progressBar, progressBarMessage);
    }

    @FXML
    public void clearDatabase() {
        clearDataService.clearDatabase(table, borderPane, progressBar, buttonsContainer);
    }

    @FXML
    public void clearFairRents() {
        fairRentService.deleteFairRents(table, borderPane);
    }
    
    @FXML
    public void clearMarketRents() {
        rentService.deleteMarketRents(table, borderPane);
    }

    @FXML
    public void getAverageListPriceByZipcodeNumBedsAndBaths() {
        zipcodeService.findAverageListPriceByZipcodeNumBedsAndBaths(table, borderPane);

    }

    @FXML
    public void getAverageListPriceByCityNumBedsAndBaths() {
        cityService.findAverageListPriceByCityNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageListPriceByStateNumBedsAndBaths() {
        stateService.findAverageListPriceByStateIdBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getBack() { //From zipcode table view to City table view
        clearDataService.getBack(table, buttonsContainer, previous, currentCity);
    }

    @FXML
    public void getAverageRentByStateNumBedsAndBaths() {
        stateService.findAverageRentByStateIdBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageRentByCityNumBedsAndBaths() {
        cityService.findAverageRentByCityNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getAverageRentByZipcodeNumBedsAndBaths() {
        zipcodeService.findAverageRentByZipcodeNumBedsAndBaths(table, borderPane);
    }

    @FXML
    public void getListingByZipcodeAndUnderwrittenVal() {
        zipcodeService.findListingByZipcodeAndUnderwrittenVal(table, borderPane, buttonsContainer);
    }

    @FXML
    public void getListingByCityAndUnderwrittenVal() {
        cityService.findListingByCityAndUnderwrittenVal(table, borderPane, buttonsContainer);
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

