
package com.rockandcode.redcalc.model;

public class BedsAndBathsDTO {
    private int numBeds;
    private double numBaths;

    public BedsAndBathsDTO() {
    }

    public BedsAndBathsDTO(int numBeds, double numBaths) {
        this.numBeds = numBeds;
        this.numBaths = numBaths;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public double getNumBaths() {
        return numBaths;
    }

    public void setNumBaths(double numBaths) {
        this.numBaths = numBaths;
    }
    
}
