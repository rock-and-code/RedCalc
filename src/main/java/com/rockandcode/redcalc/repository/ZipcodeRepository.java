
package com.rockandcode.redcalc.repository;

import java.util.List;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.ZipCode;

public class ZipcodeRepository {
    public final Datasource source;

    public ZipcodeRepository(Datasource source) {
        this.source = source;
    }
    /**
     * Finds all zipcodes that belong to a given city.
     *
     * @param cityId the ID of the city
     * @return a list of zipcodes
     */
    public List<ZipCode> findZipcodesByCityIdForTableView(int cityId) {
        return source.findZipcodesByCityIdForTableView(cityId);
    }
    /**
     * Finds a zipcode by its number.
     *
     * @param zipcodeNumber the zipcode number
     * @return the zipcode
     */
    public ZipCode findZipcodeByZipcodeNumber(int zipcodeNumber) {
        return source.findZipcodeByZipcodeNumber(zipcodeNumber);
    }
    /**
     * Finds all zipcodes that belong to a given city ID.
     *
     * @param id the ID of the city
     * @return a list of zipcodes
     */
    public List<ZipCode> findZipCodesForCityId(int id) {
        return source.findZipCodesForCityId(id);
    }
    /**
     * Finds all zipcodes that belong to a given city name.
     *
     * @param cityName the name of the city
     * @return a list of zipcodes
     */
    public List<Integer> findZipCodesByCityName(String cityName) {
        return source.findZipCodesByCityName(cityName);
    }
    /**
     * Finds the average list price of all listings in a given zipcode, for a given number of bedrooms and bathrooms.
     *
     * @param zipcodeNumber the zipcode number
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @return the average list price
     * @throws Exception if an error occurs during the retrieval
     */
    public double findAverageListPriceByZipcodeBedsBaths(int zipcodeNumber, int numBeds, double numBaths) throws Exception {
        return source.findAverageListPriceByBedsBathsAndZipcode(zipcodeNumber, numBeds, numBaths);
    }

    /**
     * Finds the average rent of all listings in a given zipcode, for a given number of bedrooms and bathrooms.
     *
     * @param zipcodeNumber the zipcode number
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @return the average rent
     * @throws Exception if an error occurs during the retrieval
     */
    public double findAverageRentByZipcodeBedsBaths(int zipcodeNumber, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndZipcode(zipcodeNumber, numBeds, numBaths);
    }
    /**
     * Deletes a zipcode by its ID.
     *
     * @param zipcodeID the ID of the zipcode to delete
     */
    public void deleteZipcodeById(int zipcodeID) {
        source.deleteZipcodeById(zipcodeID);
    }
    /**
     * Deletes all zipcodes from the database.
     */
    public void deleteZipcodes() {
        source.deleteZipcodesTableData();
    }
    /**
     * Deletes a zipcode by its number and city.
     *
     * @param zipcodeNumber the zipcode number
     * @param zipcodeCity the city of the zipcode to delete
     */
    public void deleteZipcodeByZipcodeNumberAndZipcodeCity(int zipcodeNumber, int zipcodeCity) {
        source.deleteZipcodeByZipcodeNumberAndZipcodeCity(zipcodeNumber, zipcodeCity);
    }
}
