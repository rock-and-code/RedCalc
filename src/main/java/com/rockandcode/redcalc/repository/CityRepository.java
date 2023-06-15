
package com.rockandcode.redcalc.repository;

import java.util.List;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;

/**
 * A repository for storing and retrieving City information.
 */
public class CityRepository {
    private final Datasource source;

    public CityRepository(Datasource source) {
        this.source = source;
    }
    /**
     * Retrieves a list of cities for a given state ID, suitable for display in a table view.
     *
     * @param stateId the ID of the state
     * @return a list of cities
     */
    public List<City> findCitiesByStateIdForTableView(int stateId) {
        return source.findCitiesByStateIdForTableView(stateId);
    }

    /**
     * Finds a city by its name.
     *
     * @param cityName the name of the city
     * @return the found city
     */
    public City findCityByName(String cityName) {
        return source.findCityByName(cityName);
    }

    /**
     * Retrieves a list of cities for a given state ID.
     *
     * @param id the ID of the state
     * @return a list of cities
     */
    public List<City> findCitiesForStateId(int id) {
        return source.findCitiesForStateId(id);
    }

    /**
     * Updates the name of a city with the specified ID.
     *
     * @param id the ID of the city to update
     * @param newName the new name for the city
     * @return true if the update was successful, false otherwise
     */
    public boolean updateCityName(int id, String newName) {
        return source.updateCityName(id, newName);
    }
    /**
     * Deletes a city with the specified ID.
     *
     * @param cityID the ID of the city to delete
     */
    public void deleteCityById(int cityID) {
        source.deleteCityById(cityID);
    }

    /**
     * Deletes a city with the specified name.
     *
     * @param cityName the name of the city to delete
     */
    public void deleteCityByName(String cityName) {
        source.deleteCityByName(cityName);
    }

    /**
     * Deletes all cities.
     */
    public void deleteCities() {
        source.deleteCitiesTableData();
    }
    /**
     * Calculates the average list price of properties in a city based on the number of bedrooms and bathrooms.
     *
     * @param cityName the name of the city
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @return the average list price
     * @throws Exception if an error occurs during the calculation
     */
    public double findAverageListPriceByCityBedsBaths(String cityName, int numBeds, double numBaths) throws Exception {
        return source.findAverageListPriceByBedsBathsAndCity(cityName, numBeds, numBaths);
    }
    /**
     * Calculates the average rent of properties in a city based on the number of bedrooms and bathrooms.
     *
     * @param cityName the name of the city
     * @param numBeds the number of bedrooms
     * @param numBaths the number of bathrooms
     * @return the average rent
     * @throws Exception if an error occurs during the calculation
     */
    public double findAverageRentByCityBedsBaths(String cityName, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndCity(cityName, numBeds, numBaths);
    }
}
