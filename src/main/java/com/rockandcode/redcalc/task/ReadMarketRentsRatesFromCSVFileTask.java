/**
 * The ReadMarketRentsRatesFromCSVFileTask class is responsible for reading and processing
 * a CSV file containing market rent rates, transforming the data into an object and inserting
 * it into the market_rents database table.
 */
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.RentListing;
import com.rockandcode.redcalc.util.ConsoleLogger;
import io.github.pixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import javafx.concurrent.Task;

/**
 * Task for reading market rent rates from a CSV file and inserting them into
 * the database.
 */
public class ReadMarketRentsRatesFromCSVFileTask extends Task<Boolean> {

    // The CSV file to read from
    private final File file;

    /**
     * Constructor for ReadMarketRentsRatesFromCSVFileTask class
     *
     * @param file The CSV file to read from
     */
    public ReadMarketRentsRatesFromCSVFileTask(File file) {
        this.file = file;
    }

    /**
     * Reads the CSV file and inserts the data into the database.
     *
     * @return true if successful, false otherwise
     */
    @Override
    protected Boolean call() throws Exception {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 9;
        BufferedReader dirFile = openFile(file.getPath());
        int numberOfListingsToRead = getNumberOfLinesFromRentalCSVFile(file.getPath());
        String input;

        // Reading headers columns
        int columnsInCSVFile = BoundedLineReader.readLine(dirFile, 5_000_000).split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follow expected format");
            return false;
        }

        int progressCounter = 1;
        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            RentListing rentListing = null;
            try {
                rentListing = extractRentDataFromACSVFile(input);
            } catch (NumberFormatException | InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading csv file: ", e);
            }
            if (rentListing != null) {
                try {
                    insertMarketRentRate(rentListing);
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }
            updateProgress(progressCounter, numberOfListingsToRead);
            ++progressCounter;
        }
        // Close the CSV file.
        dirFile.close();
        return true;
    }

    /**
     * Opens the CSV file and returns a BufferedReader object.
     *
     * @param filePath The path to the CSV file
     * @return A BufferedReader object
     * @throws IOException
     */
    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * Gets the number of lines in the CSV file.
     *
     * @param filePath The path to the CSV file
     * @return The number of lines in the CSV file
     * @throws Exception
     */
    private int getNumberOfLinesFromRentalCSVFile(String filePath) throws Exception {
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
     * This function transforms data read from a CSV file to a RentListing
     * object
     *
     * @param fileLine The line read from the CSV file
     * @return A RentListing object containing the extracted data
     * @throws InputMismatchException
     */
    private RentListing extractRentDataFromACSVFile(String fileLine) throws NumberFormatException {
        // Define constants for the indexes of each column in the CSV file
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8;

        // Split the CSV file line by commas to separate the data into individual fields
        String[] data = fileLine.split(",");

        // Extract the required fields from the data array using the column indexes
        String address = data[A], propertyType = data[B], city = data[H], state = data[I].toUpperCase();
        double numBaths = 0;
        int numBeds = 0, squareFootage = 0, zipcode = 0, rentRate = 0;
        String listedDate = new Date().toString();

        // Try to parse each required field from its corresponding column in the CSV file
        try {
            rentRate = Integer.parseInt(data[D]);           //COLUMN D
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Rent field is not a double");
        }
        try {
            numBeds = Integer.parseInt(data[E]);            //COLUMN E
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Bedrooms field is not an integer");
        }
        try {
            numBaths = Double.parseDouble(data[F]);         //COLUMN F
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Bathrooms field is not a double");
        }
        try {
            squareFootage = Integer.parseInt(data[G]);      //COLUMN G
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Square footage field is not an integer");
        }
        try {
            zipcode = Integer.parseInt(data[C]);            //COLUMN C
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Zipcode field is not an integer");
        }

        // Create a StringBuilder object to construct the listing address
        StringBuilder listingAddress = new StringBuilder(address);
        listingAddress.append(" ").append(city).append(" ").append(state);

        // Create a new RentListing object with the extracted data and return it
        return new RentListing(
                listingAddress.toString(),
                propertyType,
                listedDate,
                zipcode,
                rentRate,
                numBeds,
                numBaths,
                squareFootage,
                city,
                state
        );
    }

    /**
     *
     * This function inserts market rent rates into the database table
     * market_rents
     *
     * @param rentListing The RentListing object containing the rent data to be
     * inserted
     *
     * @throws Exception
     */
    private void insertMarketRentRate(RentListing rentListing) throws Exception {

        // Call the insertRentRate method of the Datasource class to insert the rent data into the database
        Datasource.getInstance().insertRentRate(
                rentListing.getAddress(),
                rentListing.getPropertyType(),
                rentListing.getListedDate(),
                rentListing.getZipcode(),
                rentListing.getRent(),
                rentListing.getBedrooms(),
                rentListing.getBathrooms(),
                rentListing.getSquareFootage(),
                rentListing.getCity(),
                rentListing.getState()
        );
    }

}
