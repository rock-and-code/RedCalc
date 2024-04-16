package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.SaleListing;
import com.rockandcode.redcalc.util.ConsoleLogger;
import io.github.pixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import javafx.concurrent.Task;

/**
 * This class is responsible for reading sales listing data from a Redfin CSV
 * file and inserting it into a database. It extends the Task class and returns
 * a Boolean value indicating whether the operation was successful.
 *
 * @author riost02
 */
public class ReadSalesListingsFromRedfinCSVFileTask extends Task<Boolean> {

    private final File file; // The Redfin CSV file to read data from

    /**
     * Constructor that initializes the Redfin CSV file to read data from.
     *
     * @param file The Redfin CSV file to read data from.
     */
    public ReadSalesListingsFromRedfinCSVFileTask(File file) {
        this.file = file;
    }

    /**
     * This method is responsible for reading data from the Redfin CSV file and
     * inserting it into a database.
     *
     * @return A boolean value indicating whether the operation was successful.
     * @throws Exception if there's an issue reading the file or inserting data
     * into the database.
     */
    @Override
    protected Boolean call() throws Exception {

        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 27; // The number of columns expected in the CSV file
        BufferedReader dirFile = openFile(file.getPath()); // Create a buffered reader to read the file
        int numberOfListingsToRead = getNumberOfLinesFromRedfinCSVFile(file.getPath()); // Get the number of lines in the file
        String input;
        // Reading headers columns
        int columnsInCSVFile = BoundedLineReader.readLine(dirFile, 5_000_000).split(",").length; // Get the number of columns in the CSV file
        // Checking if the number of columns in the file matches the expected number of columns
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format"); // Display an error message
            return false;
        }
        int progressCounter = 1; // A counter to keep track of progress
        // Reading each line of the file and extracting data to insert into the database
        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            SaleListing saleListing = null; // Initialize the SaleListing object to null
            try {
                saleListing = extractDataFromRedfinCSVFileForDatabase(input); // Extract data from the CSV line to insert into the database
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("InputMissmatch Error while reading data from csv file -->", e); // Display an error message
            } catch (Exception ex) {
                ConsoleLogger.getInstance().printErrorMessage("Exception Error while reading data from csv file -->", ex); // Display an error message
            }
            if (saleListing != null) {
                insertListing(saleListing); // Insert the SaleListing object into the database
                updateMessage(saleListing.getUrl()); // Update the message displayed below the progress bar
            }
            updateProgress(progressCounter, numberOfListingsToRead); // Update the progress bar
            ++progressCounter; // Increment the progress counter
        }
        // Close the CSV file.
        dirFile.close();

        return true; // Return true indicating the operation was successful
    }

    /**
     * This method creates a buffered reader to read a file.
     *
     * @param filePath The path of the file to read.
     * @return A buffered reader
     */
    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    private int getNumberOfLinesFromRedfinCSVFile(String filePath) throws Exception {
        int numberOfLines = 0;
        BufferedReader dirFile = openFile(filePath);
        String input;

        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            ++numberOfLines;
        }
        // Close the CSV file.
        dirFile.close();
        return numberOfLines;
    }

    /**
     * Reads data from a CSV's line
     *
     * @param fileLine a String representing one line of data from a CSV's file
     * @return
     * @throws InputMismatchException
     */
    private SaleListing extractDataFromRedfinCSVFileForDatabase(String fileLine) throws InputMismatchException, Exception {
        // Split the CSV line by commas
        String[] data = fileLine.split(",");
        // Extract the relevant fields from the CSV
        String address = data[3].trim(); //COLUMN D
        String propertyType = data[2].trim(); //COLUMN C
        String city = data[4].trim(); //COLUMN E
        String state = data[5].trim(); //COLUMN F
        String url = data[20].trim(); //COLUMN U

        // Validate that the required string fields are not empty
        if (state.isEmpty() || state.isBlank()) {
            throw new Exception("State field is empty");
        }

        if (city.isEmpty() || city.isBlank()) {
            throw new Exception("City field is empty");
        }
        if (address.isEmpty() || address.isBlank()) {
            throw new Exception("Address field is empty");
        }

        // Validate that the property type is not vacant land, other, or unknown
        if ("vacant land".equalsIgnoreCase(propertyType) || "Other".equalsIgnoreCase(propertyType)
                || "Unknown".equalsIgnoreCase(propertyType)) {
            throw new Exception("Listing's property type is vacant land, other or unknown");
        }

        // Initialize variables for numeric fields
        int zipcode = 0, numBeds = 0, squareFootage = 0, yearBuilt = 0;
        double listPrice = 0.0, numBaths = 0.0, latitude = 0.0, longitude = 0.0;

        // Parse the numeric fields from the CSV, handling parsing errors with InputMismatchException
        try {
            zipcode = Integer.parseInt(data[6]); //COLUMN G
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Zipcode field is not an integer");
        }
        try {
            listPrice = Double.parseDouble(data[7]); //COLUMN H
        } catch (InputMismatchException e) {
            throw new InputMismatchException("List price field is not a double");
        }
        try {
            numBeds = Integer.parseInt(data[8]); //COLUMN I
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Bedrooms field is not an integer");
        }
        try {
            numBaths = Double.parseDouble(data[9]); //COLUMN J
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Bathrooms field is not a double");
        }
        try {
            squareFootage = Integer.parseInt(data[11]); //COLUMN L
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Square footage field is not an integer");
        }
        try {
            yearBuilt = Integer.parseInt(data[13]); //COLUMN N
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Year built field is not an integer");
        }
        try {
            latitude = Double.parseDouble(data[25]); // COLUMN Z
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Latitude field is not a double");
        }
        try {
            longitude = Double.parseDouble(data[26]); // COLUMN AA
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Longitude field is not a double");
        }

        // Combine the address, city, and state into a single string using a StringBuilder
        StringBuilder listingAddress = new StringBuilder(address);
        listingAddress.append(" ").append(city).append(" ").append(state);

        // Create a new SaleListing object using the parsed data and the concatenated address string
        return new SaleListing(
                state,
                city,
                listingAddress.toString(),
                propertyType,
                zipcode,
                listPrice,
                numBeds,
                numBaths,
                squareFootage,
                yearBuilt,
                latitude,
                longitude,
                url
        );
    }

    private void insertListing(SaleListing saleListing) throws Exception {
        /**
         * Inserting sale listing in the database
         */
        Datasource.getInstance().insertListing(
                saleListing.getState(), //state
                saleListing.getCity(), //city
                saleListing.getAddress(), //address
                saleListing.getPropertyType(), //propertyType
                saleListing.getZipcode(), //zipcode : int
                saleListing.getListPrice(), //listPrice : double
                saleListing.getBedrooms(), //numBed : int
                saleListing.getBathrooms(), //numBath  : double
                saleListing.getSquareFootage(), //squareFootage
                saleListing.getYearBuilt(), //yearBuilt : int
                saleListing.getLatitude(), //latitude : double
                saleListing.getLongitude(), //longitude : double
                saleListing.getUrl() //url
        );
    }
}
