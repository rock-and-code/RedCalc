package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.FairRentRates;
import com.rockandcode.redcalc.util.ConsoleLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import javafx.concurrent.Task;

/**
 *
 * @author riost02
 */
public class ReadFairMarketRentRatesFromCSVFileTask extends Task<Boolean> {

    private final File file;

    public ReadFairMarketRentRatesFromCSVFileTask(File file) {
        this.file = file;
    }

    @Override
    protected Boolean call() throws Exception {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 8;
        BufferedReader dirFile = openFile(file.getPath());
        int numberOfFairRentRatesToRead = getNumberOfLinesFromFMRCSVFile(file.getPath());
        int progressCounter = 1;
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return false;
        }
        while ((input = dirFile.readLine()) != null) {
            FairRentRates data = null;
            try {
                data = extractDataFromFmrCSVFileForDatabase(input);
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading a csv file line: ", e);
            }
            if (data != null) {
                //INSERT IN DATABASE
                try {
                    insertFairRentRate(data);
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(data.getZipcode());
                    updateMessage(buffer.toString());
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }
            updateProgress(progressCounter, numberOfFairRentRatesToRead);
            ++progressCounter;
        }
        return true;
    }

    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    public int getNumberOfLinesFromFMRCSVFile(String filePath) throws Exception {
        int numberOfLines = 0;
        BufferedReader dirFile = openFile(filePath);
        String input;

        while ((input = dirFile.readLine()) != null) {
            ++numberOfLines;
        }

        return numberOfLines;
    }

    private FairRentRates extractDataFromFmrCSVFileForDatabase(String fileLine) throws InputMismatchException {
        //CSV's usefull column range 1-8
        String[] data = fileLine.split(",");
        int zip = 2, br0 = 3, br1 = 4, br2 = 5, br3 = 6, br4 = 7;
        int zipcode, studio, oneBed, twoBed, threeBed, fourBed;

        //Parsing data from CSV line of data
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
     * This functions insert fair rent rates into the database table
     * market_rents
     *
     * @param data
     * @throws Exception
     */
    private void insertFairRentRate(FairRentRates data) throws Exception {

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
