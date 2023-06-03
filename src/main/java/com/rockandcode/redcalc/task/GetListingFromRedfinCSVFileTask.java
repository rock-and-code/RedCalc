
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.util.ConsoleLogger;
import com.rockandcode.redcalc.util.FileDataReader;
import java.io.File;
import java.sql.SQLException;
import javafx.concurrent.Task;

public class GetListingFromRedfinCSVFileTask extends Task {

    private final File selectedFile;

    public GetListingFromRedfinCSVFileTask(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    @Override
    protected Boolean call() throws Exception {
        try {
            FileDataReader.getInstance().readListingsFromRedfinCSVFile(selectedFile.getPath());
            return true;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Listing couldn't been read");
            return false;
        }
    }

}
