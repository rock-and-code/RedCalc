
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.FileDataReader;
import java.io.File;
import java.sql.SQLException;
import javafx.concurrent.Task;

public class GetListingFromRedfinCSVFileTask extends Task {
    // The file containing the Redfin listings.
    private final File selectedFile;
    /**
     Constructs a new GetListingFromRedfinCSVFileTask.
     @param selectedFile The file containing the Redfin listings.
     */
    public GetListingFromRedfinCSVFileTask(File selectedFile) {
        this.selectedFile = selectedFile;
    }
    /**
     Reads the Redfin listings from the CSV file and inserts them into the database.
     @return True if the operation was successful, false otherwise.
     @throws SQLException If an error occurs while reading the file or inserting the data into the database.
     */
    @Override
    protected Boolean call() throws Exception {
        // Try to read the Redfin listings from the file.
        try {
            FileDataReader.getInstance().readListingsFromRedfinCSVFile(selectedFile.getPath());
            return true;
        } catch (SQLException e) {
            // If an error occurs, print a message to the console and return false.
            ConsoleLogger.getInstance().printMessage("Listing couldn't been read");
            return false;
        }
    }

}
