
package com.rockandcode.redcalc.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author riost02
 */
public class ZipCode {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty zipcode;
    private final SimpleIntegerProperty cityId;
    private final SimpleIntegerProperty hasRentListings;
    
    //Methods

    public ZipCode() {
        this.id = new SimpleIntegerProperty();
        this.zipcode = new SimpleIntegerProperty();
        this.cityId = new SimpleIntegerProperty();
        this.hasRentListings = new SimpleIntegerProperty();
    }
    

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    
    public int getZipcode() {
        return zipcode.get();
    }
    

    public void setZipcode(int zipcode) {
        this.zipcode.set(zipcode);
    }

    public int getCityId() {
        return cityId.get();
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }

    public String getHasRentListings() {
        return ((hasRentListings.get()==1)?"Yes":"");
    }

    public void setHasRentListings(int hasRentListings) {
        this.hasRentListings.set(hasRentListings);
    }
    
    
}
