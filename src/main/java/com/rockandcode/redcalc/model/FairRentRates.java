


package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class FairRentRates {
    private int zipcode;
    private int studioRentRate;
    private int oneBedRentRate;
    private int twoBedsRentRate;
    private int threeBedsRentRate;
    private int fourBedsRentRate;
    
    public int getFairRentRate(int numBeds) {
        if (numBeds < 0) {
            return 0;
        }
        switch (numBeds) {
            case 0:
                return studioRentRate;
            case 1:
                return oneBedRentRate;
            case 2:
                return twoBedsRentRate;
            case 3:
                return threeBedsRentRate;
            case 4: 
                return fourBedsRentRate;
            default:
               return fourBedsRentRate;
        }
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getStudioRentRate() {
        return studioRentRate;
    }

    public void setStudioRentRate(int studioRentRate) {
        this.studioRentRate = studioRentRate;
    }

    public int getOneBedRentRate() {
        return oneBedRentRate;
    }

    public void setOneBedRentRate(int oneBedsRentRate) {
        this.oneBedRentRate = oneBedsRentRate;
    }

    public int getTwoBedsRentRate() {
        return twoBedsRentRate;
    }

    public void setTwoBedsRentRate(int twoBedsRentRate) {
        this.twoBedsRentRate = twoBedsRentRate;
    }

    public int getThreeBedsRentRate() {
        return threeBedsRentRate;
    }

    public void setThreeBedsRentRate(int threeBedsRentRate) {
        this.threeBedsRentRate = threeBedsRentRate;
    }

    public int getFourBedsRentRate() {
        return fourBedsRentRate;
    }

    public void setFourBedsRentRate(int fourBedsRentRate) {
        this.fourBedsRentRate = fourBedsRentRate;
    }

    public FairRentRates() {
    }
    
    

    public FairRentRates(int zipcode, int studioRentRate, int oneBedRentRate, int twoBedsRentRate, int threeBedsRentRate, int fourBedsRentRate) {
        this.zipcode = zipcode;
        this.studioRentRate = studioRentRate;
        this.oneBedRentRate = oneBedRentRate;
        this.twoBedsRentRate = twoBedsRentRate;
        this.threeBedsRentRate = threeBedsRentRate;
        this.fourBedsRentRate = fourBedsRentRate;
    }

    
}
