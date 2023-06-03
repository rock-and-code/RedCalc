
package com.rockandcode.redcalc.task;

import com.rockandcode.redcalc.util.FileDataReader;
import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;

public class GetFairRentsTask extends Task {

    private final File selectedFile;

    public GetFairRentsTask(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileDataReader.getInstance().readFairMarketRentRatesFromCSVFileForDatabase(selectedFile.getPath());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
