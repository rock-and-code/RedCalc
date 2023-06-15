
package com.rockandcode.redcalc.repository;

import java.sql.SQLException;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.FairRentRates;

/**
 * A repository for storing and retrieving Fair Market Rent rates.
 */
public class FairMarketRentRepository {
    private final Datasource source;
    /**
     * Creates a new FairMarketRentRepository instance.
     *
     * @param source The datasource to use for storing and retrieving Fair Market Rent rates.
     */
    public FairMarketRentRepository(Datasource source) {
        this.source = source;
    }
    /**
     * Finds the Fair Market Rent rates for a given zipcode.
     *
     * @param zipcode The zipcode of the Fair Market Rent rates to find.
     * @return The Fair Market Rent rates for the given zipcode.
     * @throws SQLException If an error occurs while retrieving the Fair Market Rent rates.
     */
    public FairRentRates findFairMarketRentRatesByZipcode(int zipcode) throws SQLException {
        return source.findFairMarketRentByZipcode(zipcode);
    }
    /**
     * Saves the Fair Market Rent rates for a given zipcode.
     *
     * @param zipCode The zipcode of the Fair Market Rent rates to save.
     * @param studioRate The studio rent rate.
     * @param oneBedRate The one bedroom rent rate.
     * @param twoBedRate The two bedroom rent rate.
     * @param threeBedRate The three bedroom rent rate.
     * @param fourBedRate The four bedroom rent rate.
     * @return Whether the Fair Market Rent rates were saved successfully.
     * @throws SQLException If an error occurs while saving the Fair Market Rent rates.
     */
    public boolean saveFairMarketRent(
            int zipCode,
            int studioRate,
            int oneBedRate,
            int twoBedRate,
            int threeBedRate,
            int fourBedRate) {
        return source.insertFairRentRate(zipCode, studioRate, oneBedRate, twoBedRate, threeBedRate, fourBedRate);
    }
    /**
     * Deletes all of the Fair Market Rent rates.
     *
     * @throws SQLException If an error occurs while deleting the Fair Market Rent rates.
     */
    public void deleteFairMarketRent() {
        source.deleteFairMarketRentTableData();
    }
}
