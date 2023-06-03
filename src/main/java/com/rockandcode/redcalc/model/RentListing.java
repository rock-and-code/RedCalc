

package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class RentListing {
    private String mAddress;
    private String mPropertyType;
    private String mListedDate;
    private int mZipcode;
    private int mRent;
    private int mBedrooms;
    private double mBathrooms;
    private int mSquareFootage;
    private String mCity;
    private String mState;

    public RentListing(String address, String propertyType, String listedDate, int zipcode, int rent, int bedrooms, double bathrooms, int squareFootage, String city, String state) {
        this.mAddress = address;
        this.mPropertyType = propertyType;
        this.mListedDate = listedDate;
        this.mZipcode = zipcode;
        this.mRent = rent;
        this.mBedrooms = bedrooms;
        this.mBathrooms = bathrooms;
        this.mSquareFootage = squareFootage;
        this.mCity = city;
        this.mState = state;
    }

    @Override
    public String toString() {
        return "RentListing{" + "\n\tbmAddress=" + mAddress + "\n\tmPropertyType=" + mPropertyType + "\n\tZipcode=" + mZipcode + "\n\tmRent=" + mRent + "\n\t mBedrooms=" + mBedrooms + "\n\t mBathrooms=" + mBathrooms + "\n\t mSquareFootage=" + mSquareFootage + "\n\t mCity=" + mCity + "\t\n mState=" + mState + "\n\t}";
    }
    
    

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getPropertyType() {
        return mPropertyType;
    }

    public void setPropertyType(String propertyType) {
        this.mPropertyType = propertyType;
    }

    public String getListedDate() {
        return mListedDate;
    }

    public void setListedDate(String mListedDate) {
        this.mListedDate = mListedDate;
    }

    public int getZipcode() {
        return mZipcode;
    }

    public void setZipcode(int zipcode) {
        this.mZipcode = zipcode;
    }

    public int getRent() {
        return mRent;
    }

    public void setmRent(int rent) {
        this.mRent = rent;
    }

    public int getBedrooms() {
        return mBedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.mBedrooms = bedrooms;
    }

    public double getBathrooms() {
        return mBathrooms;
    }

    public void setBathrooms(double bathrooms) {
        this.mBathrooms = bathrooms;
    }

    public int getSquareFootage() {
        return mSquareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.mSquareFootage = squareFootage;
    }

    public String getCity() {
        return mCity;
    }

    public void setmCity(String city) {
        this.mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setmState(String state) {
        this.mState = state;
    }
    
}
