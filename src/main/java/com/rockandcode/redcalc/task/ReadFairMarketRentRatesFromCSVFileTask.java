package com.rockandcode.redcalc.task;

import io.github.pixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.FairRentRates;
import com.rockandcode.redcalc.util.ConsoleLogger;

import javafx.concurrent.Task;

/**
 * This class reads fair market rent rates from a CSV file and inserts them into the database.
 */
public class ReadFairMarketRentRatesFromCSVFileTask extends Task<Boolean> {

    private final File file;

    /**
     * Constructs a new ReadFairMarketRentRatesFromCSVFileTask.
     *
     * @param file The CSV file containing the fair market rent rates.
     */
    public ReadFairMarketRentRatesFromCSVFileTask(File file) {
        this.file = file;
    }

    /**
     * Reads the fair market rent rates from the CSV file and inserts them into the database.
     *
     * @return True if the operation was successful, false otherwise.
     * @throws Exception If an error occurs while reading the file or inserting the data into the database.
     */
    @Override
    protected Boolean call() throws Exception {
        // The number of columns expected in the CSV file.
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 8;

        // Open the CSV file.
        BufferedReader dirFile = openFile(file.getPath());

        // Get the number of fair market rent rates in the file.
        int numberOfFairRentRatesToRead = getNumberOfLinesFromFMRCSVFile(file.getPath());

        int progressCounter = 1;
        String input;
        //Reading headers columns
        int columnsInCSVFile = BoundedLineReader.readLine(dirFile, 5_000_000).split(",").length;
         if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
             ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
             return false;
         }
        // Iterate over the lines in the file, reading each fair market rent rate and inserting it into the database.
        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            //ConsoleLogger.getInstance().printMessage("inside While input=dirFile.readLine() != null");
            FairRentRates data = null;
            try {
                data = extractDataFromFmrCSVFileForDatabase(input);
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading a csv file line: ", e);
            } catch (Exception e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading a csv file line: ", e);
            }
            // If the fair market rent rate is not null, insert it into the database.
            if (data != null) {
                //INSERT IN DATABASE
                //ConsoleLogger.getInstance().printMessage("inside data != null");
                try {
                    insertFairRentRate(data);
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(data.getZipcode());
                    updateMessage(buffer.toString());
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }
            // Update the progress indicator.
            updateProgress(progressCounter, numberOfFairRentRatesToRead);
            ++progressCounter;
        }
        //ConsoleLogger.getInstance().printMessage("inside While input=dirFile.readLine() != null ends");
        // Close the CSV file.
        dirFile.close();
        return true;
    }

    /**
     * Opens the CSV file and returns a BufferedReader for reading the file.
     *
     * @param filePath The path to the CSV file.
     * @return A BufferedReader for reading the CSV file.
     * @throws IOException If an error occurs while opening the file.
     */
    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * Gets the number of lines in the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return The number of lines in the CSV file.
     * @throws Exception If an error occurs while reading the file.
     */
    public int getNumberOfLinesFromFMRCSVFile(String filePath) throws Exception {
        //ConsoleLogger.getInstance().printMessage("getNumberOfLinesFromFMRCSVFile called");
        int numberOfLines = 0;

        // Open the file and read each line.
        BufferedReader dirFile = openFile(filePath);
        String input;

        while ((input = BoundedLineReader.readLine(dirFile, 5_000_000)) != null) {
            ++numberOfLines;
        }

        // Close the file.
        dirFile.close();

        // Return the number of lines in the file.
        return numberOfLines;
    }
    /**
     * Extracts the fair market rent rate from the CSV file line.
     *
     * @param fileLine The line from the CSV file.
     * @return The fair market rent rate.
     **/
    private FairRentRates extractDataFromFmrCSVFileForDatabase(String fileLine) throws InputMismatchException, Exception {
        //ConsoleLogger.getInstance().printMessage("extractDataFromFmrCSVFileForDatabase called");
        //CSV's useful column range 1-8
        String[] data = fileLine.split(",");
        // Indexes for the different columns
        int zip = 2, br0 = 3, br1 = 4, br2 = 5, br3 = 6, br4 = 7;
        // Declare variables for the different rent rates
        int zipcode, studio, oneBed, twoBed, threeBed, fourBed;

        // Parse the data from the CSV line
        try {
            zipcode = Integer.parseInt(data[zip]);              //COLUMN G
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Zipcode field is not an integer");
        }
        try {
            studio = Integer.parseInt(data[br0]);               //COLUMN H
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Studio field is not an integer");
        }
        try {
            oneBed = Integer.parseInt(data[br1]);               //COLUMN I
        } catch (InputMismatchException e) {
            throw new InputMismatchException("One bedroom field is not an integer");
        }
        try {
            twoBed = Integer.parseInt(data[br2]);               //COLUMN J
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Two bedrooms field is not an integer");
        }
        try {
            threeBed = Integer.parseInt(data[br3]);             //COLUMN L
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Three bedrooms field is not an integer");
        }
        try {
            fourBed = Integer.parseInt(data[br4]);              //COLUMN N
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Four bedrooms is not an integer");
        }

        // Return a FairRentRates object with the extracted data
        return new FairRentRates(
                zipcode,
                studio,
                oneBed,
                twoBed,
                threeBed,
                fourBed
        );
    }

    /**
     * This function insert fair rent rates into the database table
     * market_rents
     *
     * @param data
     * @throws Exception
     */
    private void insertFairRentRate(FairRentRates data) throws Exception {
        //ConsoleLogger.getInstance().printMessage("insertFairRentRate called");
        // Insert the data into the database
        Datasource.getInstance().insertFairRentRate(
                data.getZipcode(),
                data.getStudioRentRate(),
                data.getOneBedRentRate(),
                data.getTwoBedsRentRate(),
                data.getThreeBedsRentRate(),
                data.getFourBedsRentRate()
        );
    }
}
