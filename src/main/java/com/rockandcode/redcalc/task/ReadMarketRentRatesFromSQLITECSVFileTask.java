/**
 *
 * Package containing utility classes for the RedCalc application
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
import java.util.InputMismatchException;
import javafx.concurrent.Task;

/**
 *
 * This class reads market rent rates from a SQLite CSV file, transforms the
 * data
 *
 * and then inserts it into the database.
 */
public class ReadMarketRentRatesFromSQLITECSVFileTask extends Task<Boolean> {

    private final File file;

    /**
     *
     * Constructor to set the file to be read
     *
     * @param file the file to be read
     */
    public ReadMarketRentRatesFromSQLITECSVFileTask(File file) {
        this.file = file;
    }

    /**
     *
     * The main function that reads and processes the data from the CSV file.
     *
     * @return true if successful, false otherwise
     *
     * @throws Exception
     */
    @Override
    protected Boolean call() throws Exception {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 11;
        BufferedReader dirFile = openFile(file.getPath());
        int numberOfFairRentRatesToRead = getNumberOfLinesFromRentalCSVFile(file.getPath());
        int progressCounter = 1;
        String input;
        //Reading headers columns
        int columnsInCSVFile = BoundedLineReader.readLine(dirFile, 5_000_000).split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return false;
        }
        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            RentListing rentListing = null;
            try {
                rentListing = extractRentDataFromASQLCSVFile(input);
            } catch (NumberFormatException | InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading csv file: ", e);
            }

            if (rentListing != null) {
                //INSERT IN DATABASE
                try {
                    insertMarketRentRate(rentListing);
                    updateMessage(rentListing.getAddress());  //updating the message displayed below the progress bar
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }
            updateProgress(progressCounter, numberOfFairRentRatesToRead);
            ++progressCounter;
        }
        // Close the CSV file.
        dirFile.close();

        return true;
    }

    /**
     * Opens a CSV file and returns a BufferedReader
     *
     * @param filePath path to the CSV file to be read
     * @return BufferedReader object
     * @throws IOException
     */
    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    /**
     *
     * This function returns the number of lines in a given CSV file
     *
     * @param filePath path to the CSV file to be read
     *
     * @return number of lines in the CSV file
     *
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
     *
     * This function extracts rent data from a SQLITE3 CSV file and returns an
     * object of RentListing
     *
     * @param fileLine a String representing a line of data from the CSV file
     *
     * @return an object of RentListing
     *
     * @throws InputMismatchException if the data format is not as expected
     */
    private RentListing extractRentDataFromASQLCSVFile(String fileLine) throws NumberFormatException {
        // Constants representing column numbers in the CSV file
        final int B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9, K = 10;

        // Initialize variables
        double numBaths = 0;
        int numBeds = 0, squareFootage = 0, zipcode = 0, rentRate = 0;

        // Split the data line using the comma separator
        String[] data = fileLine.split(",");

        // Extract data from the relevant columns in the CSV file
        String address = data[B], propertyType = data[C], listedDate = data[D], city = data[J], state = data[K];
        try {
            zipcode = Integer.parseInt(data[E]); // Get data from column E
        } catch (NumberFormatException e) {
            zipcode = 0;
        }
        try {
            rentRate = Integer.parseInt(data[F]); // Get data from column F
        } catch (NumberFormatException e) {
            rentRate = 0;
        }
        try {
            numBeds = Integer.parseInt(data[G]); // Get data from column G
        } catch (NumberFormatException e) {
            numBeds = 0;
        }
        try {
            numBaths = Double.parseDouble(data[H]); // Get data from column H
        } catch (NumberFormatException e) {
            numBaths = 0.0;
        }
        try {
            squareFootage = Integer.parseInt(data[I]); // Get data from column I
        } catch (NumberFormatException e) {
            squareFootage = 0;
        }

        // Create a RentListing object with the extracted data and return it
        return new RentListing(
                address,
                propertyType,
                listedDate,
                zipcode,
                rentRate,
                numBeds,
                numBaths,
                squareFootage,
                city,
                state);
    }

    /**
     *
     * This function inserts a RentListing object representing market rent rates
     * into the database table "market_rents"
     *
     * @param rentListing an object of RentListing containing market rent rates
     * @throws Exception if there is an error while inserting data into the
     * database
     */
    private void insertMarketRentRate(RentListing rentListing) throws Exception {
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
