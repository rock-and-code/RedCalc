package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ListingCity;
import com.rockandcode.redcalc.model.RealEstateState;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;


public class DatabaseInquerier {

    private static DatabaseInquerier instance = new DatabaseInquerier();
    private static List<Integer> zipcodes;
    private static List<Listing> listings;
    private static List<City> cities;
    private static List<RealEstateState> states;
    private static List<ListingCity> listingCities;

    public static DatabaseInquerier getInstance() {
        return instance;
    }

    public void queryAndPrintZipCodes() {
        String cityName = InputValidator.getInstance().getString("city");
        zipcodes = null;
        zipcodes = Datasource.getInstance().findZipCodesByCityName(cityName);
        if (zipcodes != null) {
            if (!zipcodes.isEmpty()) {
                for (Integer zipcode : zipcodes) {
                    ConsoleLogger.getInstance().printMessage(zipcode.toString());
                }
            } else {
                ConsoleLogger.getInstance().printMessage("No zipcodes for city " + cityName + "!");
            }
        }
        zipcodes = null;
    }

    public void queryAndPrintListingsByZipcodeId() {
        int zipcode = InputValidator.getInstance().getInteger("zipcode");
        listings = Datasource.getInstance().findListingsByZipcodeId(zipcode);
        if (!listings.isEmpty()) {
            for (Listing listing : listings) {
                ConsoleLogger.getInstance().printMessage(listing.toString());
            }
        } else {
            ConsoleLogger.getInstance().printMessage("No listing for zipcode " + zipcode + "!");
        }
        listings = null;
    }
    
    public void queryAndPrintStates() {
        states = Datasource.getInstance().findAllStates(Datasource.ORDER_BY_ASC);
        if (states != null) {
            if (!states.isEmpty()) {
                for (RealEstateState state : states) {
                    ConsoleLogger.getInstance().printMessage(state.getName());
                }
            } else {
                ConsoleLogger.getInstance().printMessage("No states!");
            }
        }
        cities = null;
    }

    public void queryAndPrintCitiesById() {
        String cityName = InputValidator.getInstance().getString("city");
        int stateId = 0;
        try {
            stateId = Datasource.getInstance().findStateIdByName(cityName);
        } catch (SQLException e) {
            cities = null;
        }
       
        cities = Datasource.getInstance().findCitiesForStateId(stateId);
        if (cities != null) {
            if (!cities.isEmpty()) {
                for (City city : cities) {
                    ConsoleLogger.getInstance().printMessage(city.getName());
                }
            } else {
                ConsoleLogger.getInstance().printMessage("No cities!");
            }
        }
        cities = null;
    }

    public void queryAndPrintListingByCityName() {
        String cityName = InputValidator.getInstance().getString("city");
        listings = Datasource.getInstance().findListingsByCityName(cityName);
        if (!listings.isEmpty()) {
            for (Listing listing : listings) {
                ConsoleLogger.getInstance().printMessage(listing.toString());
            }
        } else {
            ConsoleLogger.getInstance().printMessage("No listings for city " + cityName + "!");
        }
        listings = null;
    }

    public void queryAndPrintCityForListing() {
        String listingAddress = InputValidator.getInstance().getString("listing address");
        listingCities = Datasource.getInstance().findCityForListing(listingAddress);
        if (!listingCities.isEmpty()) {
            for (ListingCity listingCity : listingCities) {
                ConsoleLogger.getInstance().printMessage(listingCity.toString());
            }
        } else {
            ConsoleLogger.getInstance().printMessage("No city for listing's address " + listingAddress + "!");
        }
        listingCities = null;
    }

    public void queryListingForCityView() {
        String listingAddress = InputValidator.getInstance().getString("listing address");
        listingCities = Datasource.getInstance().findListingsForCityView(listingAddress);
        if (!listingCities.isEmpty()) {
            for (ListingCity listingCity : listingCities) {
                ConsoleLogger.getInstance().printMessage(listingCity.getCityName() + " : " + listingCity.getZipcode() + " : " + listingCity.getYearBuilt());
            }
        } else {
            ConsoleLogger.getInstance().printMessage("No city for listing's address " + listingAddress + "!");
        }
        listingCities = null;
    }

    public void insertListing() {
        String state = InputValidator.getInstance().getString("state");
        String cityName = InputValidator.getInstance().getString("city");
        String address = InputValidator.getInstance().getString("address");
        String propertyType = InputValidator.getInstance().getString("property type");
        int zipcode = InputValidator.getInstance().getInteger("Enter the zipcode:");
        int listPrice = InputValidator.getInstance().getInteger("Enter the list price:");
        int numBeds = InputValidator.getInstance().getInteger("Enter the number of beds:");
        int numBaths = InputValidator.getInstance().getInteger("Enter the number of baths:");
        int sqft = InputValidator.getInstance().getInteger("Enter the squarefootage:");
        int yearBuilt = InputValidator.getInstance().getInteger("Enter the year on which the property was built:");
        double latitude = InputValidator.getInstance().getDouble("Enter the listing latitude:");
        double longitude = InputValidator.getInstance().getDouble("Enter the listing longitude:");
        String url = InputValidator.getInstance().getString("listing url");

        Datasource.getInstance().insertListing(
                state,
                cityName, 
                address, 
                propertyType, 
                zipcode, 
                listPrice, 
                numBeds, 
                numBaths, 
                sqft, 
                yearBuilt,
                latitude,
                longitude,
                url);
    }
    
    public void updateListingPriceByAddress() throws Exception {
        String address = InputValidator.getInstance().getString("listing address");
        double newListPrice = InputValidator.getInstance().getDouble("Enter the new list price: ");
        Datasource.getInstance().updateListingPriceByAddress(newListPrice, address);
    }
    
    public void getAverageListPriceByNumBedsBathsZipcode() throws Exception {
        int numBeds = InputValidator.getInstance().getInteger("Enter the number of beds: ");
        double numBaths = InputValidator.getInstance().getDouble("Enter the number of baths: ");
        int zipcode = InputValidator.getInstance().getInteger("Enter the zipcode: ");
        Double avg = Datasource.getInstance().findAverageListPriceByZipcodeBedsBaths(zipcode, numBeds, numBaths);
        DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
        String output = myFormatter.format(avg);
        ConsoleLogger.getInstance().printMessage("The average list price is " + output);
    }
    
     public void getAverageListPriceByCityNumBedsBaths() throws Exception {
        int numBeds = InputValidator.getInstance().getInteger("Enter the number of beds: ");
        double numBaths = InputValidator.getInstance().getDouble("Enter the number of baths: ");
        String city = InputValidator.getInstance().getString("zipcode");
        Double avg = Datasource.getInstance().findAverageListPriceByCityBedsBaths(city, numBeds, numBaths);
        DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
        String output = myFormatter.format(avg);
        ConsoleLogger.getInstance().printMessage("The average list price is " + output);
    }
}