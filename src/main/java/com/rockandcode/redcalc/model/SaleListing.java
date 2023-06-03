
package com.rockandcode.redcalc.model;

/**
 *
 * @author riost02
 */
public class SaleListing {
    private String state;
    private String city;
    private String address;
    private String propertyType;
    private int zipcode;
    private double listPrice;
    private int bedrooms;
    private double bathrooms;
    private int squareFootage;
    private int yearBuilt;
    private double latitude;
    private double longitude;
    private String url;

    public SaleListing(String state, String city, String address, String propertyType, int zipcode, double listPrice, int bedrooms, double bathrooms, int squareFootage, int yearBuilt, double latitude, double longitude, String url) {
        this.state = state;
        this.city = city;
        this.address = address;
        this.propertyType = propertyType;
        this.zipcode = zipcode;
        this.listPrice = listPrice;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFootage = squareFootage;
        this.yearBuilt = yearBuilt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(double bathrooms) {
        this.bathrooms = bathrooms;
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
    
    

}
