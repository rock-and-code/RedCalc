package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.model.RentListing;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.concurrent.Task;
import org.json.*;

public class GetRealtyMolePropertyJSON extends Task<Boolean> implements GetRentData.OnDownloadComplete {

    private final String mBaseUrl;
    private final OnDataAvailable mCallback;
    private final String mCity;
    private final String mState;
    private int mProgressCounter;
    private int mTotalRentListings;

    public interface OnDataAvailable {

        public void onDataAvailable(GetRealtyMolePropertyJSON task, List<RentListing> rentListings);
    }

    public GetRealtyMolePropertyJSON(OnDataAvailable callback, String city, String state) {
        this.mBaseUrl = "https://realty-mole-property-api.p.rapidapi.com/rentalListings";
        this.mCallback = callback;
        this.mCity = city;
        this.mState = state;
        this.mProgressCounter = 1;
        this.mTotalRentListings = 0;
    }

    /**
     * Increment an integer representing the progress counter. This methods
     * allow the OnDataAvailable callback to continue to update the task
     * progress bar
     */
    public void incrementProgressCounter() {
        this.mProgressCounter++;
    }

    /**
     * Returns a integer representing the progress counter.This methods allow
     * the OnDataAvailable callback to continue to update the task progress bar
     *
     * @return mProgressCounter
     */
    public int getProgressCounter() {
        return mProgressCounter;
    }

    /**
     * Returns a integer representing the total work to be performed by the
     * task. This methods allow the OnDataAvailable callback to continue to
     * update the task progress bar
     *
     * @return mTotalRentListings;
     */
    public int getTotalRentListings() {
        return mTotalRentListings;
    }

    @Override
    public void updateProgress(double workDone, double max) {
        super.updateProgress(workDone, max);
    }

    @Override
    public void updateMessage(String message) {
        super.updateMessage(message);
    }
    
    

    /**
     * Process the data downloaded in JSON format to list of RentListings. Then
     * it calls OnDataAvailable interface
     *
     * @param data a string representing the JSON downloaded from Rent API
     */
    @Override
    public void onDownloadComplete(String data) {
        //System.out.println("onDownloadComplete: starts");

        List<RentListing> rentListings = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(data);
        } catch (JSONException e) {
            System.out.println("JSON Exception " + e.getMessage());
        }
        System.out.println("data = " + data);

        if (array != null) {
            /* Iterating through the JSON array data */
            //Setting the total work for the progress bar
            mTotalRentListings = array.length() * 2;
            for (int i = 0; i < array.length(); ++i) {
                String address, propertyType, city, state, listedDate;
                int zipcode, rent, bedrooms, squareFootage;
                double bathrooms;
                try {
                    JSONObject object = array.getJSONObject(i);
                    address = object.getString("formattedAddress");
                    propertyType = object.getString("propertyType");
                    try {
                        zipcode = object.getInt("zipCode");
                    } catch (JSONException e) {
                        zipcode = 0;
                    }
                    rent = object.getInt("price");
                    try {
                        bedrooms = object.getInt("bedrooms");
                    } catch (JSONException e) {
                        bedrooms = 0;
                    }
                    try {
                        bathrooms = object.getDouble("bathrooms");
                    } catch (JSONException e) {
                        bathrooms = 0;
                    }
                    try {
                        squareFootage = object.getInt("squareFootage");
                    } catch (JSONException e) {
                        squareFootage = 0;
                    }
                    try {
                        listedDate = object.getString("listedDate");
                    } catch (JSONException e) {
                        listedDate = new Date().toString();
                    }
                    try {
                        city = object.getString("city");
                    } catch (JSONException e) {
                        city = mCity;
                    }
                    try {
                        state = object.getString("state");
                    } catch (JSONException e) {
                        state = mState;
                    }
                    //TODO add listedDate field to the market_rents table
                    rentListings.add(new RentListing(address, propertyType, listedDate, zipcode, rent, bedrooms, bathrooms, squareFootage, city, state));
                    updateMessage(address);
                } catch (JSONException e) {
                    System.err.println("JSON Exception while processing data " + e.getMessage());
                }
                //Updates the progress bar
                updateProgress(mProgressCounter, mTotalRentListings);
                ++mProgressCounter;
            }
        }

        //Callback to store data in the dba
        mCallback.onDataAvailable(this, rentListings);
        //System.out.println("onDownloadComplete: ends");
    }

    @Override
    protected Boolean call() throws Exception {
        //System.out.println("GetRealtyMolePropertyJSON: starts");
        Boolean result = new GetRentData(this, mBaseUrl, mCity, mState).call();
        //System.out.println("GetRealtyMolePropertyJSON: ends");
        return result;
    }
}
