
package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class BedsAndZipcodeDTO {
    private int numBeds;
    private String zipcode;

    public BedsAndZipcodeDTO() {
    }

    public BedsAndZipcodeDTO(int numBeds, String zipcode) {
        this.numBeds = numBeds;
        this.zipcode = zipcode;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
}
