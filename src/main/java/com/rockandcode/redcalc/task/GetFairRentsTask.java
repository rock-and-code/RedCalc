
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.util.FileDataReader;
import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;

public class GetFairRentsTask extends Task {

    // The file containing the fair market rent rates.
    private final File selectedFile;
    /**
     * Constructs a new GetFairRentsTask.
     * @param selectedFile The file containing the fair market rent rates.
     **/
    public GetFairRentsTask(File selectedFile) {
        this.selectedFile = selectedFile;
    }
    /**
     * Reads the fair market rent rates from the CSV file and inserts them into the database.
     * @return True if the operation was successful, false otherwise.
     * @throws IOException If an error occurs while reading the file or inserting the data into the database.
     **/
    @Override
    public Boolean call() throws Exception {
        // Try to read the fair market rent rates from the file.
        try {
            FileDataReader.getInstance().readFairMarketRentRatesFromCSVFileForDatabase(selectedFile.getPath());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
