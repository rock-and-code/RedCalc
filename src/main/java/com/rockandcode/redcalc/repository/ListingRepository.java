package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ListingCity;
import java.util.List;

/**
 * A repository for storing and retrieving Listing information.
 */
public class ListingRepository {

    private final Datasource source;

    public ListingRepository(Datasource source) {
        this.source = source;
    }

    /**
     * Finds all listings that meet the specified criteria.
     *
     * @param zipcodeNumber the zipcode of the listings to find
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @param capRate the desired cap rate
     * @return a list of listings
     * @throws Exception if an error occurs during the retrieval
     */
    public List<Listing> findListingsByZipcodeAndUnderwrittenValue(int zipcodeNumber, int numBeds, double numBaths, double capRate) throws Exception {
        return source.findListingsByZipcodeAndUnderwrittenValue(zipcodeNumber, numBeds, numBaths, capRate);
    }
    /**
     * Retrieves a list of listings in a city that meet the specified underwritten value criteria.
     *
     * @param cityName the name of the city
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @param capRate the desired cap rate
     * @return a list of listings
     * @throws Exception if an error occurs during the retrieval
     */
    public List<Listing> findListingsByCityAndTheUnderwrittenValue(String cityName, int numBeds, double numBaths, double capRate) throws Exception {
        return source.findListingsByCityAndTheUnderwrittenValue(cityName, numBeds, numBaths, capRate);
    }

    /**
     * Retrieves a list of listings that meet the specified underwritten value criteria.
     *
     * @param zipcodeNumber the zipcode number of a city
     * @return a list of listings
     * @throws Exception if an error occurs during the retrieval
     */
    public List<Listing> findListingsByZipcodeNumber(int zipcodeNumber) {
        return source.findListingsByZipCodeNumber(zipcodeNumber);
    }

    /**
     * Finds all listings that meet the specified criteria.
     *
     * @param id the ID of the listings to find
     * @return a list of listings
     */
    public List<Listing> findListingsByZipcodeId(int id) {
        return source.findListingsByZipcodeId(id);
    }

    /**
     * Finds all listings that meet the specified criteria.
     *
     * @param cityName the name of the city of the listings to find
     * @return a list of listings
     */
    public List<Listing> findListingsByCityName(String cityName) {
        return source.findListingsByCityName(cityName);
    }

    /**
     * Finds the city for a given listing.
     *
     * @param listingAddress the address of the listing
     * @return the city of the listing
     */
    public List<ListingCity> findCityForListing(String listingAddress) {
        return source.findCityForListing(listingAddress);
    }
    /**
     * Retrieves the metadata for all listings.
     */
    public void findListingsMetadata() {
        source.findListingsMetadata();
    }

    /**
     * Saves a new listing to the database.
     *
     * @param state the state of the listing
     * @param city the city of the listing
     * @param address the address of the listing
     * @param propertyType the type of the listing
     * @param zipCode the zipcode of the listing
     * @param listPrice the list price of the listing
     * @param numBed the number of bedrooms of the listing
     * @param numBath the number of bathrooms of the listing
     * @param sqft the square footage of the listing
     * @param yearBuilt the year on which the property listed was built
     * @param latitude the latitude of the listing
     * @param longitude the longitude of the listing
     * @param url the url of the listing
     * @return true if the listing was saved successfully, false otherwise
     */
    public boolean saveListing(
            String state, //1
            String city, //2
            String address, //3
            String propertyType, //4
            int zipCode, //5
            double listPrice, //6
            int numBed, //7
            double numBath, //8
            int sqft, //9
            int yearBuilt, //10
            double latitude, //11
            double longitude, //12
            String url) {
        return source.insertListing(state, city, address, propertyType, zipCode, listPrice, numBed, numBath, sqft, yearBuilt, latitude, longitude, url);
    }
    /**
     * Updates the price of a listing by its address.
     *
     * @param newListPrice the new list price of the listing
     * @param address the address of the listing
     * @return true if the price was updated successfully, false otherwise
     */
    public boolean updateListingPriceByAddress(double newListPrice, String address) {
        return source.updateListingPriceByAddress(newListPrice, address);
    }
    /**
     * Deletes a listing by its ID.
     *
     * @param listingID the ID of the listing to delete
     */
    public void deleteListingById(int listingID) {
        source.deleteListingById(listingID);
    }
    /**
     * Deletes all listings from the database.
     */
    public void deleteListings() {
        source.deleteListingsTableData();
    }
}
