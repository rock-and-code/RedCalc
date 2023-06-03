
package com.rockandcode.redcalc.repository;

import java.sql.SQLException;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.FairRentRates;

/**
 *
 * @author riost02
 */
public class FairMarketRentRepository {
    private final Datasource source;

    public FairMarketRentRepository(Datasource source) {
        this.source = source;
    }
    
    public FairRentRates findFairMarketRentRatesByZipcode(int zipcode) throws SQLException {
        return source.findFairMarketRentByZipcode(zipcode);
    }
    
    public boolean saveFairMarketRent(
            int zipCode,
            int studioRate,
            int oneBedRate,
            int twoBedRate,
            int threeBedRate,
            int fourBedRate) {
        return source.insertFairRentRate(zipCode, studioRate, oneBedRate, twoBedRate, threeBedRate, fourBedRate);
    }
    
    public void deleteFairMarketRent() {
        source.deleteFairMarketRentTableData();
    }
}
