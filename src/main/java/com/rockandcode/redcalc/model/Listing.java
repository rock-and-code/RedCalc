
package com.rockandcode.redcalc.model;

import java.text.DecimalFormat;

/**
 *
 * @author riost02
 */
public class Listing {
    private int id;
    private String address;
    private String propertyType;
    private int zipcode;
    private double listPrice;
    private int numBeds;
    private double numBaths;
    private int yeartBuilt;
    private int squareFootage;
    private double latitude;
    private double longitude;
    private String url;

    @Override
    public String toString() {
        return address + ", " + propertyType + ", " + zipcode + ", " + listPrice + ", " + numBeds + ", " + numBaths + ", " + squareFootage; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getListPrice() {
        DecimalFormat myFormatter = new DecimalFormat("$###,###.##");
        String output = myFormatter.format(listPrice);
        return output;
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

    public int getYeartBuilt() {
        return yeartBuilt;
    }

    public void setYeartBuilt(int yeartBuilt) {
        this.yeartBuilt = yeartBuilt;
    }

    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
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
