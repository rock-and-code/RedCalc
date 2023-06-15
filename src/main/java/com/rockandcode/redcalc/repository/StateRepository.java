
package com.rockandcode.redcalc.repository;

import java.util.List;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.RealEstateState;

public class StateRepository {
    private final Datasource source;
    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;
    

    public StateRepository(Datasource source) {
        this.source = source;
    }

    /**
     * Finds all states in the database.
     *
     * @param sortOrder the order in which to sort the results.
     * @return a list of states.
     * @throws Exception if an error occurs during the retrieval.
     */
    public List<RealEstateState> findAllStates(int sortOrder) throws Exception {
        return source.findAllStates(sortOrder);
    }

    /**
     * Finds all states for a table view.
     *
     * @return a list of states.
     */
    public List<RealEstateState> findStatesForTableView() {
        return source.findStatesForTableView();
    }
    /**
     * Finds the ID of a state by its name.
     *
     * @param name the name of the state.
     * @return the ID of the state.
     * @throws Exception if an error occurs during the retrieval.
     */
    public int findStateIdByName(String name) throws Exception {
        return source.findStateIdByName(name);
    }
    /**
     * Finds all cities in a state for a table view.
     *
     * @param stateId the ID of the state.
     * @return a list of cities.
     * @throws Exception if an error occurs during the retrieval.
     */
    public List<City> findCitiesByStateIdForTableView(int stateId) throws Exception {
        return source.findCitiesByStateIdForTableView(stateId);
    }
    /**
     * Finds the average list price of all listings in a state, for a given number of bedrooms and bathrooms.
     *
     * @param id the ID of the state.
     * @param numBeds the number of bedrooms.
     * @param numBaths the number of bathrooms.
     * @return the average list price.
     * @throws Exception if an error occurs during the retrieval.
     */
    public double findAverageListPriceByStateBedsAndBaths(int id, int numBeds, double numBaths) throws Exception {
        return source.findAverageListPriceByStateIdBedsAndBaths(id, numBeds, numBaths);
    }
    /**
     * Finds the average rent of all listings in a state, for a given number of bedrooms and bathrooms.
     *
     * @param id the ID of the state.
     * @param numBeds the number of bedrooms.
     * @param numBaths the number of bathrooms.
     * @return the average rent.
     * @throws Exception if an error occurs during the retrieval.
     */
    public double findAverageRentByStateBedsAndBaths(int id, int numBeds, double numBaths) throws Exception {
        return source.findAverageRentByStateIdBedsAndBaths(id, numBeds, numBaths);
    }
    /**
     * Deletes a state by its ID.
     *
     * @param stateID the ID of the state to delete.
     */
    public void deleteStateById(int stateID) {
        source.deleteStateById(stateID);
    }
    /**
     * Deletes a state by its name.
     *
     * @param stateName the name of the state to delete.
     */
    public void deleteStateByName(String stateName) {
        source.deleteStateByName(stateName);
    }
    /**
     * Deletes all states from the database.
     */
    public void deleteStates() {
        source.deleteStatesTableData();
    }
 
}
