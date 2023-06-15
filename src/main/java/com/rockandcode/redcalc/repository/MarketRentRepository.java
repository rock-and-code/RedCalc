
package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;


public class MarketRentRepository {
    private final Datasource source;

    public MarketRentRepository(Datasource source) {
        this.source = source;
    }
    /**
     * Finds the average rent of all listings in a city, for a given number of bedrooms and bathrooms.
     *
     * @param city the name of the city.
     * @param numBeds the number of bedrooms.
     * @param numBaths the number of bathrooms.
     * @return the average rent.
     * @throws Exception if an error occurs during the retrieval.
     */
    public double findAverageRentByCityBedsBaths(String city, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndCity(city, numBeds, numBaths);
    }
    /**
     * Finds the average rent of all listings in a zipcode, for a given number of bedrooms and bathrooms.
     *
     * @param zipcode the zipcode of the city.
     * @param numBeds the number of bedrooms.
     * @param numBaths the number of bathrooms.
     * @return the average rent.
     * @throws Exception if an error occurs during the retrieval.
     */
    public double findAverageRentByZipcodeBedsBaths(int zipcode, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByBedsBathsAndZipcode(zipcode, numBeds, numBaths);
    }
    /**
     * Saves a rent rate to the database.
     *
     * @param address the address of the property.
     * @param propertyType the type of property.
     * @param listedDate the date the property was listed.
     * @param zipCode the zipcode of the property.
     * @param rent the rent of the property.
     * @param numBed the number of bedrooms in the property.
     * @param numBath the number of bathrooms in the property.
     * @param sqft the square footage of the property.
     * @param city the city of the property.
     * @param state the state of the property.
     * @return true if the rent rate was saved successfully, false otherwise.
     */
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

    /**
     * Deletes all the fair market rents from the database
     */
    public void deleteMarketRents() {
        source.deleteMarketRentTableData();
    }
}
