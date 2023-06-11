
package com.rockandcode.redcalc.repository;

import java.util.List;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.Listing;

public class CityRepository {
    private final Datasource source;

    public CityRepository(Datasource source) {
        this.source = source;
    }
    
    public List<City> findCitiesByStateIdForTableView(int stateId) {
        return source.findCitiesByStateIdForTableView(stateId);
    }
    
    public City findCityByName(String cityName) {
        return source.findCityByName(cityName);
    }
    
    public List<City> findCitiesForStateId(int id) {
        return source.findCitiesForStateId(id);
    }
    
    public boolean updateCityName(int id, String newName) {
        return source.updateCityName(id, newName);
    }
    
    public void deleteCityById(int cityID) {
        source.deleteCityById(cityID);
    }
    
    public void deleteCityByName(String cityName) {
        source.deleteCityByName(cityName);
    }
    
    public void deleteCities() {
        source.deleteCitiesTableData();
    }
    
    public double findAverageListPriceByCityBedsBaths(String cityName, int numBeds, double numBaths) throws Exception {
        return source.findAverageListPriceByBedsBathsAndCity(cityName, numBeds, numBaths);
    }
    
    public double findAverageRentByCityBedsBaths(String cityName, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndCity(cityName, numBeds, numBaths);
    }
    
    public List<Listing> findListingsByCityAndTheUnderwrittenValue(String cityName, int numBeds, double numBaths, 
            double capRate) throws Exception {
        return source.findListingsByCityAndTheUnderwrittenValue(cityName, numBeds, numBaths, capRate);
    }
}
