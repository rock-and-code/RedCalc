package com.rockandcode.redcalc.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author riost02
 */
public class RealEstateState {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty hasRentListings;

    public RealEstateState() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.hasRentListings = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        /* Transforming a state postal abbreviation into the state's full name */
        return StateNameByPostalAbbreviation.getInstance().getStateNameByPostalAbbreviation(this.name.get());
    }
    
    public String getDBName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getHasRentListings() {
        return ((hasRentListings.get()==1)?"Yes":"");
    }

    public void setHasRentListings(int hasRentListings) {
        this.hasRentListings.set(hasRentListings);
    }
    
    
}
