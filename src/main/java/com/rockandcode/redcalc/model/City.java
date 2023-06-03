
package com.rockandcode.redcalc.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author riost02
 */
public class City {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty stateId;
    private final SimpleIntegerProperty hasRentListings;
    

    public City() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.stateId = new SimpleIntegerProperty();
        this.hasRentListings = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public int getStateId() {
        return stateId.get();
    }

    public void setStateId(int city) {
        this.stateId.set(city);
    }

    public String getHasRentListings() {
        return ((hasRentListings.get()==1)?"Yes":"");
    }

    public void setHasRentListings(int hasRentListings) {
        this.hasRentListings.set(hasRentListings);
    }
    
    
    
}
