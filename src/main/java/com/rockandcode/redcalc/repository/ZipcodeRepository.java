
package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ZipCode;
import java.util.List;

/**
 *
 * @author riost02
 */
public class ZipcodeRepository {
    public final Datasource source;

    public ZipcodeRepository(Datasource source) {
        this.source = source;
    }
    
    public List<ZipCode> findZipcodesByCityIdForTableView(int cityId) {
        return source.findZipcodesByCityIdForTableView(cityId);
    }
    
    public ZipCode findZipcodeByZipcodeNumber(int zipcodeNumber) {
        return source.findZipcodeByZipcodeNumber(zipcodeNumber);
    }
    
    public List<ZipCode> findZipCodesForCityId(int id) {
        return source.findZipCodesForCityId(id);
    }
    
    public List<Integer> findZipCodesByCityName(String cityName) {
        return source.findZipCodesByCityName(cityName);
    }
    
    public List<Listing> findListingsForZipcodeId(int zipcodeId) throws Exception {
        return source.findListingsForZipCodeId(zipcodeId);
    }
    
    public List<Listing> findListingsForZipcodeNumber(int zipcodeNumber) throws Exception {
        return source.findListingsForZipCodeNumber(zipcodeNumber);
    }
    
    public double findAverageListPriceByZipcodeBedsBaths(int zipcodeNumber, int numBeds, double numBaths) throws Exception {
        return source.findAverageListPriceByZipcodeBedsBaths(zipcodeNumber, numBeds, numBaths);
    }
    
    public double findAverageRentByZipcodeBedsBaths(int zipcodeNumber, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByZipcodeBedsBaths(zipcodeNumber, numBeds, numBaths);
    }
    
    public List<Listing> findListingsByZipcodeAndUnderwrittenValue(int zipcodeNumber, int numBeds, double numBaths, double capRate) throws Exception {
        return source.findListingsByZipcodeAndUnderwrittenValue(zipcodeNumber, numBeds, numBaths, capRate);
    }
    
    public void deleteZipcodeById(int zipcodeID) {
        source.deleteZipcodeById(zipcodeID);
    }
    
    public void deleteZipcodes() {
        source.deleteZipcodesTableData();
    }
    
    public void deleteZipcodeByZipcodeNumberAndZipcodeCity(int zipcodeNumber, int zipcodeCity) {
        source.deleteZipcodeByZipcodeNumberAndZipcodeCity(zipcodeNumber, zipcodeCity);
    }
}
