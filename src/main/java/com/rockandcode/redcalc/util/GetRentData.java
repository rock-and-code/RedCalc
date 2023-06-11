package com.rockandcode.redcalc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.concurrent.Task;
import javax.net.ssl.HttpsURLConnection;

public class GetRentData extends Task<Boolean> {

    private final String mBaseURL;
    private final String mCity;
    private final String mState;
    private final OnDownloadComplete mCallback;

    public GetRentData(OnDownloadComplete callback, String mBaseURL, String city, String state) {
        this.mCallback = callback;
        this.mBaseURL = mBaseURL;
        this.mCity = city;
        this.mState = state;
    }

    public interface OnDownloadComplete {

        public void onDownloadComplete(String data);
    }

    private String createURI(String baseURL, String city, String state) {
        StringBuilder destinationURL = new StringBuilder(baseURL);
        destinationURL.append("?city=");
        destinationURL.append(city);
        destinationURL.append("&");
        destinationURL.append("state=");
        destinationURL.append(state);
        destinationURL.append("&");
        destinationURL.append("limit=");
        destinationURL.append(1000);

        return destinationURL.toString();

    }

    @Override
    public Boolean call() {
        //System.out.println("GetRentData: starts");
        URL destinationURL = null;
        HttpsURLConnection connection = null;
        BufferedReader reader = null;

        try {
            //Setting the destination URL, connection and the buffer reader
            destinationURL = new URL(createURI(mBaseURL, mCity, mState));
            connection = (HttpsURLConnection) destinationURL.openConnection();
            //Setting the URL headers
            //TODO -> UPDATE BELOW HEADER WITH YOUR REALTY MOLE PROPERTY API KEY
            // TO CREATE A FREEMIUM ACCOUNT AND OBTAIN A KEY VISIT:  https://www.realtymole.com/api OR https://rapidapi.com/realtymole/api/realty-mole-property-api
            connection.setRequestProperty("X-RapidAPI-Key", "YOUR-KEY"); //ADD YOUR API KEY HERE
            connection.setRequestProperty("X-RapidAPI-Host", "realty-mole-property-api.p.rapidapi.com");
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                //if response code is not 200 then return false
                return false;
            }
            //System.out.println("Response code: " + responseCode);
            //System.out.println(connection.getURL());
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //String builder to append the data downloaded from internet
            StringBuilder result = new StringBuilder();
            //Appends data from internet to the string builder
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }
            //Print downloaded data
            mCallback.onDownloadComplete(result.toString());
            System.out.println("onDownloadComplete: ends");

        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL Exception " + ex);
        } catch (IOException ex) {
            System.out.println("IO Exception " + ex);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException ex) {
                    System.out.println("Error closing the buffer reader");
                }
            }
        }
        return true;
    }
}
