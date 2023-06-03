package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ListingCity;
import java.util.List;

/**
 *
 * @author riost02
 */
public class ListingRepository {

    private final Datasource source;

    public ListingRepository(Datasource source) {
        this.source = source;
    }

    public List<Listing> findListingsByZipcodeAndUnderwrittenValue(int zipcode, int numBeds, double numBaths, double capRate) throws Exception {
        return source.findListingsByZipcodeAndUnderwrittenValue(zipcode, numBeds, numBaths, capRate);
    }

    public List<Listing> findListingsByCityAndTheUnderwrittenValue(String city, int numBeds, double numBaths, double capRate) throws Exception {
        return source.findListingsByCityAndTheUnderwrittenValue(city, numBeds, numBaths, capRate);
    }

    public List<Listing> findListingsForZipCodeId(int id) {
        return source.findListingsForZipCodeId(id);
    }

    public List<Listing> findListingsForZipCodeNumber(int zipcode) {
        return source.findListingsForZipCodeNumber(zipcode);
    }

    public List<Listing> findListingsByZipcodeId(int id) {
        return source.findListingsByZipcodeId(id);
    }

    public List<Listing> findListingsByCityName(String cityName) {
        return source.findListingsByCityName(cityName);
    }

    public List<ListingCity> findCityForListing(String listingAddress) {
        return source.findCityForListing(listingAddress);
    }

    public void findListingsMetadata() {
        source.findListingsMetadata();
    }

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

    public boolean updateListingPriceByAddress(double newListPrice, String address) {
        return source.updateListingPriceByAddress(newListPrice, address);
    }

    public void deleteListingById(int listingID) {
        source.deleteListingById(listingID);
    }

    public void deleteListings() {
        source.deleteListingsTableData();
    }
}
