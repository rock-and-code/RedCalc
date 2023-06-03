
package com.rockandcode.redcalc.repository;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.RealEstateState;
import java.util.List;

/**
 *
 * @author riost02
 */
public class StateRepository {
    private final Datasource source;
    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;
    

    public StateRepository(Datasource source) {
        this.source = source;
    }
    
    public List<RealEstateState> findAllStates(int sortOrder) throws Exception {
        return source.findAllStates(sortOrder);
    }
    
    public List<RealEstateState> findStatesForTableView() {
        return source.findStatesForTableView();
    }
    
    public int findStateIdByName(String name) throws Exception {
        return source.findStateIdByName(name);
    }
    
    public List<City> findCitiesByStateIdForTableView(int stateId) throws Exception {
        return source.findCitiesByStateIdForTableView(stateId);
    }
    
    public void deleteStateById(int stateID) {
        source.deleteStateById(stateID);
    }
    
    public void deleteStateByName(String stateName) {
        source.deleteStateByName(stateName);
    }
    
    public void deleteStates() {
        source.deleteStatesTableData();
    }
 
}
