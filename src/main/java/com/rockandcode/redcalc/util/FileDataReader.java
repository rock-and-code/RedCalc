package com.rockandcode.redcalc.util;

import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.model.FairRentRates;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;

public class FileDataReader {

    private static final FileDataReader instance = new FileDataReader();

    public static FileDataReader getInstance() {
        return instance;
    }

    private BufferedReader openFile(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    public int getNumberOfLinesFromRedfinCSVFile(String filePath) throws Exception {
        int numberOfLines = 0;
        BufferedReader dirFile = openFile(filePath);
        String input;

        while ((input = dirFile.readLine()) != null) {
            ++numberOfLines;
        }

        return numberOfLines;
    }

    private Object[] extractDataFromRedfinCSVFileForDatabase(String fileLine) throws InputMismatchException {
        //CSV's useful column range 1-27
        String[] data = fileLine.split(",");
        String address = data[3].trim();                           //COLUMN D
        String propertyType = data[2].trim();                      //COLUMN C
        String city = data[4].trim();                              //COLUMN E
        String state = data[5].trim().toUpperCase();               //COLUMN F
        String url = data[20].trim();                              //COLUMN U

        if (city.isEmpty() || city.isBlank()) {
            throw new InputMismatchException("City field is empty");
        }
        if (address.isEmpty() || address.isBlank()) {
            throw new InputMismatchException("Address field is empty");
        }
        if (state.isEmpty() || state.isBlank()) {
            throw new InputMismatchException("State field is empty");
        }
        if (propertyType.equalsIgnoreCase("Vacant Land")) {
            throw new InputMismatchException("Property type is Vacant Land");
        }
        if (propertyType.equalsIgnoreCase("Other")) {
            throw new InputMismatchException("Property type is Other");
        }
        if (propertyType.equalsIgnoreCase("Unknown")) {
            throw new InputMismatchException("Property type is Uknown");
        }

        int zipcode = 0, numBeds = 0, squareFootage = 0, yearBuilt = 0;
        double listPrice = 0.0, numBaths = 0.0, latitude = 0.0, longitude = 0.0;
        /* In some instances zipcode has a hyphen then eliminate it */
        if (data[6].contains("-")) {
            data[6] = data[6].substring(0, data[6].indexOf("-"));
        }

        try {
            zipcode = Integer.parseInt(data[6]);            //COLUMN G
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Zipcode is not a numberical values");
        }
        try {
            listPrice = Double.parseDouble(data[7]);        //COLUMN H
        } catch (NumberFormatException e) {
            throw new NumberFormatException("List price is not a numberical values");
        }
        try {
            numBeds = Integer.parseInt(data[8]);            //COLUMN I
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bedroom is not a numberical values");
        }
        try {
            numBaths = Double.parseDouble(data[9]);         //COLUMN J
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bathroom is not a numberical values");
        }
        try {
            squareFootage = Integer.parseInt(data[11]);    //COLUMN L
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Square footage is not a numberical values");
        }
        try {
            yearBuilt = Integer.parseInt(data[13]);        //COLUMN N
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Year built is not a numberical values");
        }
        try {
            latitude = Double.parseDouble(data[25]);       //COLUMN Z
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Latitude is not a numberical values");
        }
        try {
            longitude = Double.parseDouble(data[26]);      //COLUMN AA
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Longitude is not a numberical values");
        }

        StringBuilder listingAddress = new StringBuilder(address);
        listingAddress.append(" ").append(city).append(" ").append(state);

        return new Object[]{
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
        };
    }

    /**
     * This function calls the insertListing method from Datasource class to
     * insert data in the database. The data will be parse according to the
     * database listings table fields datatype. Will discard listings where city
     * is empty and property type is equals to "Other", "Vacant Land", and
     * "Unknown".
     *
     * @param data
     * @throws Exception
     */
    private void insertListing(Object[] data) throws Exception {
        Datasource.getInstance().insertListing(
                data[0].toString().toUpperCase(), //state
                data[1].toString(), //city
                data[2].toString(), //address
                data[3].toString(), //propertyType
                Integer.parseInt(data[4].toString()), //zipcode
                Double.parseDouble(data[5].toString()), //listPrice : double
                Integer.parseInt(data[6].toString()), //numBed
                Double.parseDouble(data[7].toString()), //numBath  : double
                Integer.parseInt(data[8].toString()), //squareFootage
                Integer.parseInt(data[9].toString()), //yearBuilt
                Double.parseDouble(data[10].toString()), //latitude
                Double.parseDouble(data[11].toString()), //longitude
                data[12].toString() //url
        );
    }

    /**
     * This function reads data from a Redfin CSV file containing Real Estate
     * listings.The function reads, create listings, from the read data, and
     * adds listings to a given list of listing
     *
     * @param filePath
     * @throws IOException
     */
    public void readListingsFromRedfinCSVFile(String filePath) throws Exception {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 27;
        BufferedReader dirFile = openFile(filePath);
        int numberOfListingsToRead = getNumberOfLinesFromRedfinCSVFile(filePath);
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return;
        }

        while ((input = dirFile.readLine()) != null) {
            Object[] data = null;
            try {
                //data = extractDataFromRedfinCSVFile(input);                   //Data to create Listings Objects
                data = extractDataFromRedfinCSVFileForDatabase(input);          //Data to be inserted in the db  
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("InputMissmatch Error while reading csv file ", e);
            }
            if (data != null) {
                insertListing(data);
            }
        }
    }

    /**
     * This function transforms data read from a CSV file to an object
     *
     * @param fileLine
     * @return
     * @throws InputMismatchException
     */
    private Object[] extractRentDataFromACSVFile(String fileLine) throws InputMismatchException, NumberFormatException {
        final int E = 4, F = 5, G = 6, H = 7, I = 8, J = 9, K = 10, L = 11, M = 12;
        //CSV's usefull column range 0-12 OR A-M
        String[] data = fileLine.split(",");
        String address = data[E], propertyType = data[F], city = data[L], state = data[M].toUpperCase();
        String listedDate = new Date().toString();
        double numBaths = 0;
        int numBeds = 0, squareFootage = 0, zipcode = 0, rentRate = 0;
        
        try {
            rentRate = Integer.parseInt(data[H]);         //COLUMN H
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Rate rent is not a numerical value");
        }
        try {
            numBeds = Integer.parseInt(data[I]);           //COLUMN I
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bedroom is not a numerical value");
        }
        try {
            numBaths = Double.parseDouble(data[J]);        //COLUMN J
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Bathroom is not a numerical value");
        }
        try {
           squareFootage = Integer.parseInt(data[K]);     //COLUMN K 
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Square footage is not a numerical value");
        }
        try {
            zipcode = Integer.parseInt(data[G]);           //COLUMN G
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Zipcode is not a numerical value");
        }

        //DEBUGGER
        //ConsoleLogger.getInstance().printMessage(zipcode + ": " + rentRate);
        return new Object[]{
            address,
            propertyType,
            listedDate,
            zipcode,
            rentRate,
            numBeds,
            numBaths,
            squareFootage,
            city,
            state
        };
    }

    /**
     * This functions insert market rent rates into the database table
     * market_rents
     *
     * @param data
     * @throws Exception
     */
    private void insertMarketRentRate(Object[] data) throws Exception {
        final int address = 0, propertyType = 1, listedDate = 2, zipcode = 3, rentRate = 4, numBeds = 5, numBaths = 6,
                squareFootage = 7, city = 8, state = 9;
        Datasource.getInstance().insertRentRate(
                data[address].toString(),
                data[propertyType].toString(),
                data[listedDate].toString(),
                Integer.parseInt(data[zipcode].toString()),
                Integer.parseInt(data[rentRate].toString()),
                Integer.parseInt(data[numBeds].toString()),
                Double.parseDouble(data[numBaths].toString()),
                Integer.parseInt(data[squareFootage].toString()),
                data[city].toString(),
                data[state].toString()
        );
    }

    /**
     * This function reads data from a CSV file containing Rent listings.The
     * function reads, create rental listings, from the read data, and adds rent
     * listings to a given list of rent listings
     *
     * @param filePath
     * @throws IOException
     */
    public void readMarketRentRatesFromCSVFile(String filePath) throws IOException {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 13;
        BufferedReader dirFile = openFile(filePath);
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return;
        }
        while ((input = dirFile.readLine()) != null) {
            Object[] data;
            try {
                data = extractRentDataFromACSVFile(input);
            } catch (NumberFormatException | InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading csv file: ", e);
                continue;
            }
            //RentalListing rentListing = null;
            if (data != null) {
                //INSERT IN DATABASE
                try {
                    insertMarketRentRate(data);
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }

        }
    }

    /**
     * This function reads data from a SQLITE3 CSV file containing Rent
     * listings.The function reads, create rental listings, from the read data,
     * and adds rent listings to a given list of rent listings
     *
     * @param filePath
     * @throws IOException
     */
    public void readMarketRentRatesFromSQLCSVFile(String filePath) throws IOException {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 11;
        BufferedReader dirFile = openFile(filePath);
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return;
        }
        while ((input = dirFile.readLine()) != null) {
            Object[] data = null;
            try {
                data = extractRentDataFromASQLCSVFile(input);
            } catch (NumberFormatException | InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading csv file: ", e);
            }
            //RentalListing rentListing = null;
            if (data != null) {
                //INSERT IN DATABASE
                //rentListing = createRentalListing(data);
                try {
                    insertMarketRentRate(data);
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }

        }
    }

    /**
     * This function transforms data read from a SQLITE3 CSV file to an object
     *
     * @param fileLine
     * @return
     * @throws InputMismatchException
     */
    private Object[] extractRentDataFromASQLCSVFile(String fileLine) throws InputMismatchException, NumberFormatException {
        final int B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9, K = 10;
        //CSV's usefull column range 1-10 OR B-K
        double numBaths;
        int numBeds, squareFootage, zipcode, rentRate;
        String[] data = fileLine.split(",");
        String address = data[B], propertyType = data[C], listedDate = data[D], city, state;

        try {
            zipcode = Integer.parseInt(data[E]);            //COLUMN E
        } catch (NumberFormatException e) {
            zipcode = 0;
        }
        try {
            rentRate = Integer.parseInt(data[F]);         //COLUMN F
        } catch (NumberFormatException e) {
            rentRate = 0;
        }
        try {
            numBeds = Integer.parseInt(data[G]);            //COLUMN G
        } catch (NumberFormatException e) {
            numBeds = 0;
        }
        try {
            numBaths = Double.parseDouble(data[H]);         //COLUMN H
        } catch (NumberFormatException e) {
            numBaths = 0.0;
        }
        try {
            squareFootage = Integer.parseInt(data[K]);      //COLUMN I
        } catch (NumberFormatException e) {
            squareFootage = 0;
        }
        try {
            city = data[J];                                 //COLUMN J
        } catch (Exception e) {
            city = "";
        }
        try {
            state = data[K];                                //COLUMN K
        } catch (Exception e) {
            state = "";
        }

        //DEBUGGER
        //ConsoleLogger.getInstance().printMessage(zipcode + ": " + rentRate);
        return new Object[]{
            address,
            propertyType,
            listedDate,
            zipcode,
            rentRate,
            numBeds,
            numBaths,
            squareFootage,
            city,
            state
        };
    }

    private Object[] extractDataFromFairMarketRentCSVFile(String fileLine) throws InputMismatchException {
        //CSV's usefull column range 0-7 OR A-H
        int zipcode, studioRentRate, oneBedRentRate, twoBedsRentRate, threeBedsRentRate,
                fourBedsRentRate;
        String[] data = fileLine.split(",");

        //##REFACTOR CODE BELOW###
        zipcode = ((data[2].isBlank()) ? 0 : Integer.parseInt(data[2]));            //COLUMN C
        studioRentRate = ((data[3].isBlank()) ? 0 : Integer.parseInt(data[3]));     //COLUMN D
        oneBedRentRate = ((data[4].isBlank()) ? 0 : Integer.parseInt(data[4]));     //COLUMN E
        twoBedsRentRate = ((data[5].isBlank()) ? 0 : Integer.parseInt(data[5]));    //COLUMN F
        threeBedsRentRate = ((data[6].isBlank()) ? 0 : Integer.parseInt(data[6]));  //COLUMN G
        fourBedsRentRate = ((data[7].isBlank()) ? 0 : Integer.parseInt(data[7]));   //COLUMN H

        //DEBUG
        //ConsoleLogger.getInstance().printMessage(zipcode + ": " + oneBedRentRate);

        return new Object[]{
            zipcode,
            studioRentRate,
            oneBedRentRate,
            twoBedsRentRate,
            threeBedsRentRate,
            fourBedsRentRate
        };
    }

    private FairRentRates createFairRentRates(Object[] data) {
        //DEBUGGING
        /**
         * for(int i=0; i<data.length; ++i){
         * ConsoleLogger.getInstance().printMessage("INDEX " + i + " " +
         * data[i].toString()); }
         */

        return new FairRentRates() {
            {
                //setId(id);
                setZipcode(Integer.parseInt(data[0].toString()));
                setStudioRentRate(Integer.parseInt(data[1].toString()));
                setOneBedRentRate(Integer.parseInt(data[2].toString()));
                setTwoBedsRentRate(Integer.parseInt(data[3].toString()));
                setThreeBedsRentRate(Integer.parseInt(data[4].toString()));
                setFourBedsRentRate(Integer.parseInt(data[5].toString()));
            }
        };

    }

    public void readFairMarketRentRatesFromCSVFile(String filePath) throws IOException {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 8;
        BufferedReader dirFile = openFile(filePath);
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return;
        }

        while ((input = dirFile.readLine()) != null) {
            Object[] data;
            try {
                data = extractDataFromFairMarketRentCSVFile(input);
            } catch (InputMismatchException e) {
                System.out.println("Input mismatch error while reading csv file: " + e.getMessage());
                continue;
            }
            FairRentRates frr = null;
            if (data != null) {
                frr = createFairRentRates(data);
            }

        }
    }

    /**
     * This function reads data from a CSV file containing Fair rent rates.The
     * function reads, create fair rentals objects, from the read data, and adds
     * rent listings to a given list of rent listings
     *
     * @param filePath
     * @throws IOException
     */
    public void readFairMarketRentRatesFromCSVFileForDatabase(String filePath) throws IOException {
        int NUM_COLUMNS_EXPECTED_IN_CSV_FILE = 8;
        BufferedReader dirFile = openFile(filePath);
        String input;
        //Reading headers columns
        int columnsInCSVFile = dirFile.readLine().split(",").length;
        if (columnsInCSVFile != NUM_COLUMNS_EXPECTED_IN_CSV_FILE) {
            ConsoleLogger.getInstance().printMessage("Error: Entered CSV file does not follows expected format");
            return;
        }
        while ((input = dirFile.readLine()) != null) {
            FairRentRates data = null;
            try {
                data = extractDataFromFmrCSVFileForDatabase(input);
            } catch (InputMismatchException e) {
                ConsoleLogger.getInstance().printErrorMessage("Error while reading csv file: ", e);
            }
            //RentalListing rentListing = null;
            if (data != null) {
                //INSERT IN DATABASE
                //rentListing = createRentalListing(data);
                try {
                    insertFairRentRate(data);
                } catch (Exception e) {
                    ConsoleLogger.getInstance().printErrorMessage(input, e);
                }

            }
        }
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
