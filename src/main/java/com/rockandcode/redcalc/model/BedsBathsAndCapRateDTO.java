
package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class BedsBathsAndCapRateDTO {
    private int numBeds;
    private double numBaths;
    private String capRate;

    public BedsBathsAndCapRateDTO() {
    }

    public BedsBathsAndCapRateDTO(int numBeds, double numBaths, String capRate) {
        this.numBeds = numBeds;
        this.numBaths = numBaths;
        this.capRate = capRate;
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

    public String getCapRate() {
        return capRate;
    }

    public void setCapRate(String capRate) {
        this.capRate = capRate;
    }
    
    
}
