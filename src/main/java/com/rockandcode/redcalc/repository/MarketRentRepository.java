
package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;

/**
 *
 * @author riost02
 */
public class MarketRentRepository {
    private final Datasource source;

    public MarketRentRepository(Datasource source) {
        this.source = source;
    }
    
    public double findAverageRentByCityBedsBaths(String city, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndCity(city, numBeds, numBaths);
    }
    
    public double findAverageRentByZipcodeBedsBaths(int zipcode, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndZipcode(zipcode, numBeds, numBaths);
    }
    
    public boolean saveRentRate(
            String address,
            String propertyType,
            String listedDate,
            int zipCode,
            double rent,
            int numBed,
            double numBath,
            int sqft,
            String city,
            String state) {
        return source.insertRentRate(address, propertyType, listedDate, zipCode, rent, numBed, numBath, sqft, city, state);
    }
    
    public void deleteMarketRents() {
        source.deleteMarketRentTableData();
    }
}
