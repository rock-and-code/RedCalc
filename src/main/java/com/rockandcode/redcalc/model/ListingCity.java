
package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class ListingCity {
    private String state;
    private String cityName;
    private String address;
    private String propertyType;
    private int zipcode;
    private double listPrice;
    private int numBeds;
    private double numBaths;
    private int squareFootage;
    private int yearBuilt;
    private double latitude;
    private double longitude;
    private String url;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return state + ", " + cityName + ", " + address + ", " + propertyType + ", " + zipcode + ", " + listPrice + ", " + numBeds + ", " + numBaths + ", " + squareFootage;
    }
    
    

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
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

    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    
    
}
