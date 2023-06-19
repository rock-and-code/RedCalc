package com.rockandcode.redcalc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rockandcode.redcalc.model.City;
import com.rockandcode.redcalc.model.FairRentRates;
import com.rockandcode.redcalc.model.Listing;
import com.rockandcode.redcalc.model.ListingCity;
import com.rockandcode.redcalc.model.RealEstateState;
import com.rockandcode.redcalc.model.ZipCode;
import com.rockandcode.redcalc.util.ConsoleLogger;

/**
 *
 * @author riost02
 */
public class Datasource {

    /* Inner class that defines the states table contents */
    public static class States {

        private static final String TABLE_NAME = "states";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_NAME = "name";
        private static final String COLUMN_NAME_HAS_SALES_LISTINGS = "has_sales_listings";
        private static final String COLUMN_NAME_HAS_RENT_LISTINGS = "has_rent_listings";

        private static final int INDEX_ID = 1;
        private static final int INDEX_NAME = 2;
        private static final int INDEX_HAS_SALES_LISTINGS = 3;
        private static final int INDEX_HAS_RENT_LISTINGS = 4;
    }

    /* Inner class that defines the cities table contents */
    public static class Cities {

        private static final String TABLE_NAME = "cities";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_NAME = "name";
        private static final String COLUMN_NAME_STATE = "state";
        private static final String COLUMN_NAME_HAS_SALE_LISTINGS = "has_sales_listings";
        private static final String COLUMN_NAME_HAS_RENT_LISTINGS = "has_rent_listings";

        private static final int INDEX_ID = 1;
        private static final int INDEX_NAME = 2;
        private static final int INDEX_STATE = 3;
        private static final int INDEX_HAS_SALES_LISTINGS = 4;
        private static final int INDEX_HAS_RENT_LISTINGS = 5;
    }

    /* Inner class that defines the zipcodes table contents */
    public static class Zipcodes {

        private static final String TABLE_NAME = "zipcodes";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_NUMBER = "zipcode";
        private static final String COLUMN_NAME_CITY = "city";
        private static final String COLUMN_NAME_HAS_SALE_LISTINGS = "has_sales_listings";
        private static final String COLUMN_NAME_HAS_RENT_LISTINGS = "has_rent_listings";

        private static final int INDEX_ID = 1;
        private static final int INDEX_NUMBER = 2;
        private static final int INDEX_CITY = 3;
        private static final int INDEX_HAS_SALES_LISTINGS = 4;
        private static final int INDEX_HAS_RENT_LISTINGS = 5;
    }

    /* Inner class that defines the zipcodes table contents */
    public static class Listings {

        private static final String TABLE_NAME = "listings";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_PROPERTY_TYPE = "propertyType";
        private static final String COLUMN_NAME_ADDRESS = "address";
        private static final String COLUMN_NAME_ZIP_CODE = "zipcode";
        private static final String COLUMN_NAME_LIST_PRICE = "listPrice";
        private static final String COLUMN_NAME_NUM_BED = "numBeds";
        private static final String COLUMN_NAME_NUM_BATH = "numBaths";
        private static final String COLUMN_NAME_SQFT = "squareFootage";
        private static final String COLUMN_NAME_YEAR_BUILT = "yeartBuilt";
        private static final String COLUMN_NAME_LATITUDE = "latitude";
        private static final String COLUMN_NAME_LONGITUDE = "longitude";
        private static final String COLUMN_NAME_URL = "url";

        private static final int INDEX_ID = 1;
        private static final int INDEX_PROPERTY_TYPE = 2;
        private static final int INDEX_ADDRESS = 3;
        private static final int INDEX_ZIP_CODE = 4;
        private static final int INDEX_LIST_PRICE = 5;
        private static final int INDEX_NUM_BED = 6;
        private static final int INDEX_NUM_BATH = 7;
        private static final int INDEX_SQFT = 8;
        private static final int INDEX_YEAR_BUILT = 9;
        private static final int INDEX_LATITUDE = 10;
        private static final int INDEX_LONGITUDE = 11;
        private static final int INDEX_URL = 12;
    }

    public static class MarketRents {

        private static final String TABLE_NAME = "market_rents";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_ADDRESS = "address";
        private static final String COLUMN_NAME_PROPERTY_TYPE = "propertyType";
        private static final String COLUMN_NAME_LISTED_DATE = "listedDate";
        private static final String COLUMN_NAME_ZIP_CODE = "zipcode";
        private static final String COLUMN_NAME_RENT = "rent";
        private static final String COLUMN_NAME_NUM_BED = "numBeds";
        private static final String COLUMN_NAME_NUM_BATH = "numBaths";
        private static final String COLUMN_NAME_SQFT = "squareFootage";
        private static final String COLUMN_NAME_CITY = "city";
        private static final String COLUMN_NAME_STATE = "state";

        private static final int INDEX_ID = 1;
        private static final int INDEX_ADDRESS = 2;
        private static final int INDEX_PROPERTY_TYPE = 3;
        private static final int INDEX_ZIP_CODE = 4;
        private static final int INDEX_RENT = 5;
        private static final int INDEX_NUM_BED = 6;
        private static final int INDEX_NUM_BATH = 7;
        private static final int INDEX_SQFT = 8;
        private static final int INDEX_CITY = 9;
        private static final int INDEX_STATE = 10;
    }

    public static class FairRents {

        private static final String TABLE_NAME = "fair_rents";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_ZIP_CODE = "zipcode";
        private static final String COLUMN_NAME_STUDIO = "studio";
        private static final String COLUMN_NAME_ONE_BED = "oneBed";
        private static final String COLUMN_NAME_TWO_BED = "twoBed";
        private static final String COLUMN_NAME_THREE_BED = "threeBed";
        private static final String COLUMN_NAME_FOUR_BED = "fourBed";

        private static final int INDEX_ID = 1;
        private static final int INDEX_ZIP_CODE = 2;
        private static final int INDEX_ZIP_STUDIO = 3;
        private static final int INDEX_ONE_BED = 4;
        private static final int INDEX_TWO_BED = 5;
        private static final int INDEX_THREE_BED = 6;
        private static final int INDEX_FOUR_BED = 7;
    }

    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_ZIPCODE = "zipcode";
    private static final String COLUMN_LISTING = "listing";

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    /* SQL QUERIES */
    private static final String QUERY_ZIP_CODE_BY_CITY_START = "SELECT " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_NUMBER
            + " FROM " + Zipcodes.TABLE_NAME + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " WHERE " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_NAME + " = ?";

    private static final String QUERY_ZIP_CODE_BY_CITY_SORT = " ORDER BY " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_NAME
            + " COLLATE NOCASE ";

    private static final String QUERY_LISTINGS_BY_ZIP_CODE_NUMBER = "SELECT * FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " WHERE " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_NUMBER + " = ? "
            + "ORDER BY " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS
            + " COLLATE NOCASE ";

    private static final String QUERY_LISTINGS_BY_CITIES_START = "SELECT DISTINCT " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_ADDRESS
            + " FROM " + Listings.TABLE_NAME + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_ZIP_CODE + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?";

    private static final String QUERY_LISTINGS_BY_CITIES_SORT = " ORDER BY " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_ADDRESS
            + " COLLATE NOCASE ";

    // TODO fix the name it is querying address for listings fix it to query
    // listings by city
    private static final String QUERY_CITY_FOR_LISTING_START = "SELECT " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_NAME + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS + ", " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_PROPERTY_TYPE + ", " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_LIST_PRICE + ", " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_NUM_BED + ", " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BATH + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_SQFT + ", " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_YEAR_BUILT
            + " FROM " + Listings.TABLE_NAME + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_ZIP_CODE + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?";
    // + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS + " =
    // ?";

    private static final String QUERY_CITY_FOR_LISTING_SORT = " ORDER BY " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_NAME + ", " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS
            + " COLLATE NOCASE ";

    /* QUERY AVERAGE RENT RATES BY ZIPCODE, CITY, STATE */
    private static final String QUERY_AVG_RENT_BY_ZIPCODE_BEDS_AND_BATHS = "SELECT AVG(" + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_RENT + ")"
            + " FROM " + MarketRents.TABLE_NAME + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME
            + '.' + MarketRents.COLUMN_NAME_ZIP_CODE + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " WHERE " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND " + MarketRents.TABLE_NAME
            + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ?";

    private static final String QUERY_AVG_RENT_BY_CITY_BEDS_AND_BATHS = "SELECT AVG(" + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_RENT + ")"
            + " FROM " + MarketRents.TABLE_NAME + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME
            + '.' + MarketRents.COLUMN_NAME_ZIP_CODE + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " INNER JOIN " + Cities.TABLE_NAME + " ON "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY + " = "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " WHERE " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_CITY + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND " + MarketRents.TABLE_NAME
            + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ?";

    private static final String TABLE_STATE_LISTING_VIEW = "state_list";

    private static final String CREATE_STATE_FOR_LISTING_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_STATE_LISTING_VIEW
            + " AS SELECT "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " AS " + COLUMN_STATE + ", "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " AS " + COLUMN_CITY + ", "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " AS " + COLUMN_ZIPCODE + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_PROPERTY_TYPE + ", " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_LIST_PRICE + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BED + ", " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_NUM_BATH + ", " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_SQFT + ", "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_YEAR_BUILT
            + " FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " ORDER BY " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + ", " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_NUMBER + ", " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS;

    private static final String QUERY_CITY_FOR_LISTING_VIEW = "SELECT * FROM " + TABLE_STATE_LISTING_VIEW + " WHERE "
            + COLUMN_CITY + " = ?";

    private static final String QUERY_CITY_FOR_LISTING_VIEW_SORT = " ORDER BY " + COLUMN_CITY + ", " + COLUMN_ZIPCODE
            + ", "
            + " COLLATE NOCASE ";

    /* SQL INSERT STATEMENTS */
    private static final String INSERT_STATE = "INSERT INTO " + States.TABLE_NAME + " (" + States.COLUMN_NAME_NAME
            + ", " + States.COLUMN_NAME_HAS_SALES_LISTINGS + ", " + States.COLUMN_NAME_HAS_RENT_LISTINGS
            + ") VALUES (?, ?, ?)";

    private static final String INSERT_CITY = "INSERT INTO " + Cities.TABLE_NAME + " (" + Cities.COLUMN_NAME_NAME + ", "
            + Cities.COLUMN_NAME_STATE + ", " + Cities.COLUMN_NAME_HAS_SALE_LISTINGS + ", "
            + Cities.COLUMN_NAME_HAS_RENT_LISTINGS + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_ZIP_CODE = "INSERT INTO " + Zipcodes.TABLE_NAME + " ("
            + Zipcodes.COLUMN_NAME_NUMBER + ", " + Zipcodes.COLUMN_NAME_CITY + ", "
            + Zipcodes.COLUMN_NAME_HAS_SALE_LISTINGS + ", " + Zipcodes.COLUMN_NAME_HAS_RENT_LISTINGS
            + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_LISTING = "INSERT INTO " + Listings.TABLE_NAME + " ("
            + Listings.COLUMN_NAME_ADDRESS + ", " + Listings.COLUMN_NAME_PROPERTY_TYPE + ", "
            + Listings.COLUMN_NAME_ZIP_CODE + ", "
            + Listings.COLUMN_NAME_LIST_PRICE + ", " + Listings.COLUMN_NAME_NUM_BED + ", "
            + Listings.COLUMN_NAME_NUM_BATH + ", " + Listings.COLUMN_NAME_SQFT + ", " + Listings.COLUMN_NAME_YEAR_BUILT
            + ", " + Listings.COLUMN_NAME_LATITUDE + ", "
            + Listings.COLUMN_NAME_LONGITUDE + ", " + Listings.COLUMN_NAME_URL
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_MARKET_RENT = "INSERT INTO " + MarketRents.TABLE_NAME + " ("
            + MarketRents.COLUMN_NAME_ADDRESS + ", " + MarketRents.COLUMN_NAME_PROPERTY_TYPE + ", "
            + MarketRents.COLUMN_NAME_LISTED_DATE + ", " + MarketRents.COLUMN_NAME_ZIP_CODE + ", "
            + MarketRents.COLUMN_NAME_RENT + ", " + MarketRents.COLUMN_NAME_NUM_BED + ", "
            + MarketRents.COLUMN_NAME_NUM_BATH + ", " + MarketRents.COLUMN_NAME_SQFT + ", "
            + MarketRents.COLUMN_NAME_CITY + ", " + MarketRents.COLUMN_NAME_STATE
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_FAIR_RENT = "INSERT INTO " + FairRents.TABLE_NAME + " ("
            + FairRents.COLUMN_NAME_ZIP_CODE + ", " + FairRents.COLUMN_NAME_STUDIO + ", "
            + FairRents.COLUMN_NAME_ONE_BED + ", " + FairRents.COLUMN_NAME_TWO_BED + ", "
            + FairRents.COLUMN_NAME_THREE_BED + ", " + FairRents.COLUMN_NAME_FOUR_BED
            + ") VALUES (?, ?, ?, ?, ?, ?)";

    /* QUERYING DATA FROM TABLES */
    private static final String QUERY_STATE_ID = "SELECT " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " FROM "
            + States.TABLE_NAME
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?";

    private static final String QUERY_STATE = "SELECT * FROM " + States.TABLE_NAME
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?";

    private static final String QUERY_STATE_HAS_SALES_LISTING_BY_ID = "SELECT " + States.TABLE_NAME + '.'
            + States.COLUMN_NAME_HAS_SALES_LISTINGS
            + " FROM " + States.TABLE_NAME + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_CITY_HAS_SALES_LISTING_BY_ID = "SELECT " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_HAS_SALE_LISTINGS
            + " FROM " + Cities.TABLE_NAME + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_ZIPCODE_HAS_SALES_LISTING_BY_ID = "SELECT " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_HAS_SALE_LISTINGS
            + " FROM " + Zipcodes.TABLE_NAME + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_STATE_HAS_RENT_LISTING_BY_ID = "SELECT " + States.TABLE_NAME + '.'
            + States.COLUMN_NAME_HAS_RENT_LISTINGS
            + " FROM " + States.TABLE_NAME + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_CITY_HAS_RENT_LISTING_BY_ID = "SELECT " + Cities.TABLE_NAME + '.'
            + Cities.COLUMN_NAME_HAS_RENT_LISTINGS
            + " FROM " + Cities.TABLE_NAME + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_ZIPCODE_HAS_RENT_LISTING_BY_ID = "SELECT " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_HAS_RENT_LISTINGS
            + " FROM " + Zipcodes.TABLE_NAME + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_CITY = "SELECT * FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?";

    private static final String QUERY_ZIPCODE_BY_NUMBER = "SELECT * FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ?";

    private static final String QUERY_ZIPCODE_NUMBER_BY_ID = "SELECT " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_NUMBER + " FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_CITY_BY_NAME_AND_STATE_ID = "SELECT * FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ? AND "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = ?";

    private static final String QUERY_ZIPCODE_BY_ZIPCODE_AND_CITY_ID = "SELECT * FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ? AND "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY + " = ?";

    private static final String QUERY_LISTING = "SELECT * FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Listings.COLUMN_NAME_ID
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ADDRESS + " = ? AND " + Zipcodes.TABLE_NAME
            + '.' + Listings.COLUMN_NAME_ID + " = ?";

    private static final String QUERY_RENT_RATES = "SELECT * FROM " + MarketRents.TABLE_NAME
            + " WHERE " + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_ADDRESS + " = ?";

    private static final String QUERY_CITIES_BY_STATE_ID = "SELECT * FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = ? ORDER BY "
            + Cities.COLUMN_NAME_NAME + " COLLATE NOCASE";

    private static final String QUERY_ZIP_CODES_BY_CITY_ID = "SELECT * FROM " + Zipcodes.TABLE_NAME + " WHERE "
            + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_CITY + " = ? ORDER BY " + Zipcodes.COLUMN_NAME_NUMBER + " COLLATE NOCASE";

    private static final String QUERY_LISTINGS_BY_ZIP_CODE_ID = "SELECT * FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " WHERE " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_ID + " = ? ORDER BY " + Listings.COLUMN_NAME_ADDRESS + " COLLATE NOCASE";

    private static final String QUERY_LISTINGS_BY_CITY_NAME = "SELECT * FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME
            + " = ? ORDER BY " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE + ", " + Listings.TABLE_NAME
            + '.' + Listings.COLUMN_NAME_ADDRESS + " COLLATE NOCASE";

    private static final String QUERY_FAIR_RENTS_BY_ZIP_CODE = "SELECT " + FairRents.COLUMN_NAME_ZIP_CODE + ", "
            + FairRents.COLUMN_NAME_STUDIO + ", "
            + FairRents.COLUMN_NAME_ONE_BED + ", " + FairRents.COLUMN_NAME_TWO_BED + ", "
            + FairRents.COLUMN_NAME_THREE_BED + ", "
            + FairRents.COLUMN_NAME_FOUR_BED + " FROM " + FairRents.TABLE_NAME
            + " WHERE " + FairRents.TABLE_NAME + '.' + FairRents.COLUMN_NAME_ZIP_CODE + " = ?";

    private static final String QUERY_ZIPCODE_BY_ZIPCODE_NUMBER = "SELECT * FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " =?";

    private static final String QUERY_CITY_BY_CITY_NAME = "SELECT * FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " =?";

    /* SQL UPDATE STATEMENT */
    private static final String UPDATE_CITY_NAME = "UPDATE " + Cities.TABLE_NAME + " SET "
            + Cities.COLUMN_NAME_NAME + " = ? " + " WHERE " + Cities.COLUMN_NAME_ID + " = ?";

    /* QUERY TABLES FOR TABLE VIEW GUI */
    private static final String QUERY_STATES_FOR_TABLE_VIEW = "SELECT * FROM " + States.TABLE_NAME
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_HAS_SALES_LISTINGS + " =1 ORDER BY "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " ASC";

    private static final String QUERY_CITIES_FOR_STATE_ID_AND_TABLE_VIEW = "SELECT * FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " =? AND "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_HAS_SALE_LISTINGS + " =1 ORDER BY "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " ASC";

    private static final String QUERY_ZIPCODES_FOR_CITY_ID_AND_TABLE_VIEW = "SELECT * FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY + " = ? AND "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_HAS_SALE_LISTINGS + " =1 ORDER BY "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " ASC";

    /* UPDATING DATA FROM TABLES */
    private static final String UPDATE_LISTING_PRICE_BY_ADDRESS = "UPDATE " + Listings.TABLE_NAME + " SET "
            + Listings.COLUMN_NAME_LIST_PRICE + " = ? WHERE " + Listings.COLUMN_NAME_ADDRESS + " = ?";

    private static final String UPDATE_STATE_HAS_SALES_LISTINGS_BY_ID = "UPDATE " + States.TABLE_NAME + " SET "
            + States.COLUMN_NAME_HAS_SALES_LISTINGS + " = 1 WHERE " + States.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_CITY_HAS_SALES_LISTINGS_BY_ID = "UPDATE " + Cities.TABLE_NAME + " SET "
            + Cities.COLUMN_NAME_HAS_SALE_LISTINGS + " = 1 WHERE " + Cities.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_ZIPCODE_HAS_SALES_LISTINGS_BY_ID = "UPDATE " + Zipcodes.TABLE_NAME + " SET "
            + Zipcodes.COLUMN_NAME_HAS_SALE_LISTINGS + " = 1 WHERE " + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_STATE_HAS_RENT_LISTINGS_BY_ID = "UPDATE " + States.TABLE_NAME + " SET "
            + States.COLUMN_NAME_HAS_RENT_LISTINGS + " = 1 WHERE " + States.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_CITY_HAS_RENT_LISTINGS_BY_ID = "UPDATE " + Cities.TABLE_NAME + " SET "
            + Cities.COLUMN_NAME_HAS_RENT_LISTINGS + " = 1 WHERE " + Cities.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_ZIPCODE_HAS_RENT_LISTINGS_BY_ID = "UPDATE " + Zipcodes.TABLE_NAME + " SET "
            + Zipcodes.COLUMN_NAME_HAS_RENT_LISTINGS + " = 1 WHERE " + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String UPDATE_STATES_AFTER_CLEARING_MARKET_RENT_DBA = "UPDATE " + States.TABLE_NAME + " SET "
            + States.COLUMN_NAME_HAS_RENT_LISTINGS + " = 0";

    private static final String UPDATE_CITIES_AFTER_CLEARING_MARKET_RENT_DBA = "UPDATE " + Cities.TABLE_NAME + " SET "
            + Cities.COLUMN_NAME_HAS_RENT_LISTINGS + " = 0";

    private static final String UPDATE_ZIPCODES_AFTER_CLEARING_MARKET_RENT_DBA = "UPDATE " + Zipcodes.TABLE_NAME
            + " SET "
            + Zipcodes.COLUMN_NAME_HAS_RENT_LISTINGS + " = 0";

    /* DELETE TABLE'S DATA */
    private static final String DELETE_LISTINGS_TABLE_DATA = "DELETE FROM " + Listings.TABLE_NAME;

    private static final String DELETE_ZIPCODES_TABLE_DATA = "DELETE FROM " + Zipcodes.TABLE_NAME;

    private static final String DELETE_CITIES_TABLE_DATA = "DELETE FROM " + Cities.TABLE_NAME;

    private static final String DELETE_STATES_TABLE_DATA = "DELETE FROM " + States.TABLE_NAME;

    private static final String DELETE_MARKET_RENT_TABLE_DATA = "DELETE FROM " + MarketRents.TABLE_NAME;

    private static final String DELETE_FAIR_RENT_TABLE_DATA = "DELETE FROM " + FairRents.TABLE_NAME;

    /* DELETE RECORDS */
    private static final String DELETE_STATE_BY_ID = "DELETE FROM " + States.TABLE_NAME
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " = ?";

    private static final String DELETE_CITY_BY_ID = "DELETE FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " = ?";

    private static final String DELETE_ZIPCODE_BY_ID = "DELETE FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " = ?";

    private static final String DELETE_LISTING_BY_ID = "DELETE FROM " + Listings.TABLE_NAME
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ID + " = ?";

    private static final String DELETE_LISTINGS_BY_ZIPCODE_NUMBER_AND_ZIPCODE_CITY = "DELETE FROM "
            + Listings.TABLE_NAME
            + " WHERE " + Listings.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ID + " FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ? AND " + Zipcodes.TABLE_NAME
            + '.' + Zipcodes.COLUMN_NAME_CITY + "=?);";

    private static final String DELETE_ZIPCODES_BY_ZIPCODE_NUMBER_AND_ZIPCODE_CITY = "DELETE FROM "
            + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " FROM " + Zipcodes.TABLE_NAME
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ? AND " + Zipcodes.TABLE_NAME
            + '.' + Zipcodes.COLUMN_NAME_CITY + "=?);";

    /* DELETING listings, zipcodes, and city by CITIES.NAME */
    private static final String DELETE_LISTINGS_BY_CITY_NAME = "DELETE FROM " + Listings.TABLE_NAME
            + " WHERE " + Listings.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ID + " FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?);";

    private static final String DELETE_ZIPCODES_BY_CITY_NAME = "DELETE FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " FROM " + Zipcodes.TABLE_NAME
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?);";

    private static final String DELETE_CITY_BY_NAME = "DELETE FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?);";

    /* DELETING listings, zipcodes, city, and state by States.name */
    private static final String DELETE_LISTINGS_BY_STATE_NAME = "DELETE FROM " + Listings.TABLE_NAME
            + " WHERE " + Listings.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ID + " FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " + "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?);";

    private static final String DELETE_ZIPCODES_BY_STATE_NAME = "DELETE FROM " + Zipcodes.TABLE_NAME
            + " WHERE " + Zipcodes.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID + " FROM " + Zipcodes.TABLE_NAME
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " + "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?);";

    private static final String DELETE_CITY_BY_STATE_NAME = "DELETE FROM " + Cities.TABLE_NAME
            + " WHERE " + Cities.COLUMN_NAME_ID + " IN ("
            + " SELECT " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID + " FROM " + Cities.TABLE_NAME
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " + "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?);";

    private static final String DELETE_STATE_BY_NAME = "DELETE FROM " + States.TABLE_NAME
            + " WHERE " + States.COLUMN_NAME_ID + " IN ("
            + " SELECT " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " FROM " + States.TABLE_NAME
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " = ?);";

    /*
     * ADD QUERY TO DELETE LISTINGS, ZIPCODE, CITIES, AND STATE FROM A CONTEXT MENU
     */
    /**
     * SELECT AVG(listings.listPrice) FROM listings WHERE listings.numBeds =3
     * AND listings.numBaths=2 AND listings.zipcode = 34104;
     */
    private static final String GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE = "SELECT AVG("
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_LIST_PRICE + ") FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BED + " = ? AND "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ?";

    private static final String GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_CITY = "SELECT AVG("
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_LIST_PRICE + ") FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BED + " = ? AND "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?";

    private static final String GET_AVERAGE_LISTINGS_PRICE_FOR_STATE_BY_ID_BEDS_AND_BATHS = "SELECT AVG("
            + Listings.COLUMN_NAME_LIST_PRICE + ") FROM " + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " = ? AND " + Listings.TABLE_NAME + '.'
            + Listings.COLUMN_NAME_NUM_BED + " = ? AND "
            + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BATH + " = ?";

    private static final String GET_AVERAGE_RENT_BY_ZIPCODE_BEDS_BATHS = "SELECT AVG("
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_RENT + ") FROM " + MarketRents.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " WHERE " + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ?";

    private static final String GET_AVERAGE_RENT_BY_CITY_BEDS_BATHS = "SELECT AVG("
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_RENT + ") FROM " + MarketRents.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?";

    private static final String GET_AVERAGE_RENT_FOR_STATE_BY_ID_BEDS_AND_BATHS = "SELECT AVG("
            + MarketRents.COLUMN_NAME_RENT + ") FROM " + MarketRents.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID + " = ? AND " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ?";

    /*
     * GET AVERAGE RENT PRICE GIVEN CITY NAME, NUM BEDS, AND BATHS
     * 
     * SELECT * FROM listings
     * INNER JOIN zipcodes ON listings.zipcode = zipcodes._id
     * INNER JOIN cities ON zipcodes.city = cities._id
     * INNER JOIN states ON cities.state = states._id
     * WHERE listings.numBeds = ? AND listings.numBaths = ? AND cities.name = ?
     * AND states.name = "FL" AND listings.listPrice <= (SELECT
     * AVG(listings.listPrice) FROM listings
     * INNER JOIN zipcodes ON listings.zipcode = zipcodes._id
     * INNER JOIN cities ON zipcodes.city = cities._id
     * INNER JOIN states ON cities.state = states._id
     * WHERE listings.numBeds = ? AND listings.numBaths = ? AND cities.name = ? AND
     * states.name = ? );
     * 
     * 
     * GET LISTING THAT ARE LESS OR EQUAL TO ITS UNDERWRITTEN VALUE
     * 
     * SELECT * FROM listings
     * INNER JOIN zipcodes ON listings.zipcode = zipcodes._id
     * INNER JOIN cities ON zipcodes.city = cities._id
     * INNER JOIN states ON cities.state = states._id
     * WHERE listings.numBeds = ? AND listings.numBaths = ? AND cities.name = ?
     * AND listings.listPrice <= ((((SELECT AVG(market_rents.rent) FROM market_rents
     * INNER JOIN zipcodes ON market_rents.zipcode = zipcodes._id
     * INNER JOIN cities ON zipcodes.city = cities._id
     * WHERE market_rents.numBeds = ? AND market_rents.numBaths = ?
     * AND cities.name = ?;) * 12) * 0.5 (less expensess estimated as 50% of the
     * rent revenues))/ CAP_RATE);
     */
    private static final String GET_LISTINGS_BY_CITY_WITH_LIST_PRICE_LESS_EQUAL_TO_UNDERWRITTEN_VALUE = "SELECT * FROM "
            + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " INNER JOIN " + States.TABLE_NAME + " ON " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_STATE + " = "
            + States.TABLE_NAME + '.' + States.COLUMN_NAME_ID
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BED + " = ? AND " + Listings.TABLE_NAME
            + '.' + Listings.COLUMN_NAME_NUM_BATH + " = ? AND " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME
            + " = ? AND " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_LIST_PRICE + " <= "
            + "((((SELECT AVG(" + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_RENT + ") FROM "
            + MarketRents.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " INNER JOIN " + Cities.TABLE_NAME + " ON " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_CITY
            + " = " + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_ID
            + " WHERE " + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Cities.TABLE_NAME + '.' + Cities.COLUMN_NAME_NAME + " = ?) * 12) * 0.5)/ ?)";

    private static final String GET_LISTINGS_BY_ZIPCODE_WITH_LIST_PRICE_LESS_EQUAL_TO_UNDERWRITTEN_VALUE = "SELECT * FROM "
            + Listings.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_ZIP_CODE
            + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " WHERE " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_NUM_BED + " = ? AND " + Listings.TABLE_NAME
            + '.' + Listings.COLUMN_NAME_NUM_BATH + " = ? AND " + Zipcodes.TABLE_NAME + '.'
            + Zipcodes.COLUMN_NAME_NUMBER
            + " = ? AND " + Listings.TABLE_NAME + '.' + Listings.COLUMN_NAME_LIST_PRICE + " <= "
            + "((((SELECT AVG(" + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_RENT + ") FROM "
            + MarketRents.TABLE_NAME
            + " INNER JOIN " + Zipcodes.TABLE_NAME + " ON " + MarketRents.TABLE_NAME + '.'
            + MarketRents.COLUMN_NAME_ZIP_CODE + " = " + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_ID
            + " WHERE " + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BED + " = ? AND "
            + MarketRents.TABLE_NAME + '.' + MarketRents.COLUMN_NAME_NUM_BATH + " = ? AND "
            + Zipcodes.TABLE_NAME + '.' + Zipcodes.COLUMN_NAME_NUMBER + " = ?) * 12) * 0.5)/ ?)";

    private Connection conn;
    private Statement statement;

    private PreparedStatement queryCityForListingView;
    private PreparedStatement insertIntoStates;
    private PreparedStatement insertIntoCities;
    private PreparedStatement insertIntoZipCodes;
    private PreparedStatement insertIntoListings;
    private PreparedStatement insertIntoMarketRents;
    private PreparedStatement insertIntoFairRents;

    private PreparedStatement queryStateId;
    private PreparedStatement queryState;
    private PreparedStatement queryStateHasSalesListingsById;
    private PreparedStatement queryCityHasSalesListingsById;
    private PreparedStatement queryZipcodeHasSalesListingsById;
    private PreparedStatement queryStateHasRentListingsById;
    private PreparedStatement queryCityHasRentListingsById;
    private PreparedStatement queryZipcodeHasRentListingsById;
    private PreparedStatement queryZipCodeByNumber;
    private PreparedStatement queryZipCodeNumberById;
    private PreparedStatement queryCityByNameAndStateId;
    private PreparedStatement queryZipCodeByNumberAndCityId;
    private PreparedStatement queryZipCodeByCityName;
    private PreparedStatement queryListing;
    private PreparedStatement queryRentRate;
    private PreparedStatement queryCityByStateId;
    private PreparedStatement queryZipCodeByCityId;
    private PreparedStatement queryListingsByZipCodeId;
    private PreparedStatement queryListingsByZipCodeNumber;
    private PreparedStatement queryListingsByCityName;
    private PreparedStatement queryCityForListing;
    private PreparedStatement queryZipcodeByZipcodeNumber;
    private PreparedStatement queryCityByName;
    private PreparedStatement queryStatesForTableView;
    private PreparedStatement queryCitiesForTableView;
    private PreparedStatement queryZipcodesForTableView;

    private PreparedStatement updateCityName;
    private PreparedStatement updateListingPriceByAddress;
    private PreparedStatement updateStateHasSalesListingsById;
    private PreparedStatement updateCityHasSalesListingsById;
    private PreparedStatement updateZipcodeHasSalesListingsById;
    private PreparedStatement updateStateHasRentListingsById;
    private PreparedStatement updateCityHasRentListingsById;
    private PreparedStatement updateZipcodeHasRentListingsById;
    private PreparedStatement updateStatesAfterClearingMarketRentsDba;
    private PreparedStatement updateCitiesAfterClearingMarketRentsDba;
    private PreparedStatement updateZipcodesAfterClearingMarketRentsDba;

    private PreparedStatement deleteListingsTableData;
    private PreparedStatement deleteZipcodesTableData;
    private PreparedStatement deleteCitiesTableData;
    private PreparedStatement deleteStatesTableData;
    private PreparedStatement deleteMarketRentTableData;
    private PreparedStatement deleteFairMarketRentTableData;
    private PreparedStatement deleteStateById;
    private PreparedStatement deleteCityById;
    private PreparedStatement deleteZipcodeById;
    private PreparedStatement deleteListingById;
    private PreparedStatement deleteListingsByZipcodeNumberAndZipcodeCity;
    private PreparedStatement deleteZipcodeByZipcodeNumberAndZipcodeCity;
    private PreparedStatement deleteListingByCityName;
    private PreparedStatement deleteZipcodesByCityName;
    private PreparedStatement deleteCityByName;
    private PreparedStatement deleteListingByStateName;
    private PreparedStatement deleteZipcodesByStateName;
    private PreparedStatement deleteCityByStateName;
    private PreparedStatement deleteStateByName;
    private PreparedStatement getAverageListPriceByBedsBathsAndZipcode;
    private PreparedStatement getAverageListPriceByBedsBathsAndCity;
    private PreparedStatement getAverageListPriceByStateIdBedsAndBaths;
    private PreparedStatement getAverageRentByBedsBathsAndZipcode;
    private PreparedStatement getAverageRentByBedsBathsAndCity;
    private PreparedStatement getAverageRentByStateIdBedsAndBaths;
    private PreparedStatement getFairMarketRentByZipcode;
    private PreparedStatement getListingByZipcodeAndUnderWrittenValue;
    private PreparedStatement getListingByCityAndUnderWrittenValue;

    private static Datasource instance = new Datasource();

    public static Datasource getInstance() {
        return instance;
    }

    private static String CONNECTION_STRING;

    private void resetDBAConnection() {
        Datasource.getInstance().close();
        // Resetting dba connection to unloak dba
        Datasource.getInstance().open(CONNECTION_STRING);
    }

    public boolean open(String CONNECTION_STRING) {
        try {
            if (CONNECTION_STRING == null) {
                return false;
            } else {
                Datasource.CONNECTION_STRING = CONNECTION_STRING;
                conn = DriverManager.getConnection("jdbc:sqlite:" + CONNECTION_STRING);
            }
            statement = conn.createStatement();

            // CREATING TABLES LISTINGS, ZIPCODES, CIIIES, STATES, MARKET_RENT IF NOT EXITS
            statement.execute("CREATE TABLE IF NOT EXISTS " + Listings.TABLE_NAME + " ("
                    + Listings.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + Listings.COLUMN_NAME_ADDRESS + " TEXT NOT NULL, "
                    + Listings.COLUMN_NAME_PROPERTY_TYPE + " TEXT NOT NULL, "
                    + Listings.COLUMN_NAME_ZIP_CODE + " INTEGER, "
                    + Listings.COLUMN_NAME_LIST_PRICE + " REAL NOT NULL, "
                    + Listings.COLUMN_NAME_NUM_BED + " INTEGER, "
                    + Listings.COLUMN_NAME_NUM_BATH + " REAL, "
                    + Listings.COLUMN_NAME_SQFT + " INTEGER, "
                    + Listings.COLUMN_NAME_YEAR_BUILT + " INTEGER, "
                    + Listings.COLUMN_NAME_LATITUDE + " REAL, "
                    + Listings.COLUMN_NAME_LONGITUDE + " REAL, "
                    + Listings.COLUMN_NAME_URL + " TEXT NOT NULL UNIQUE"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + Zipcodes.TABLE_NAME + " ("
                    + Zipcodes.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + Zipcodes.COLUMN_NAME_NUMBER + " INTEGER, "
                    + Zipcodes.COLUMN_NAME_CITY + " INTEGER, "
                    + Zipcodes.COLUMN_NAME_HAS_SALE_LISTINGS + " INTEGER, "
                    + Zipcodes.COLUMN_NAME_HAS_RENT_LISTINGS + " INTEGER"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + Cities.TABLE_NAME + " ("
                    + Cities.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + Cities.COLUMN_NAME_NAME + " TEXT NOT NULL, "
                    + Cities.COLUMN_NAME_STATE + " INTEGER, "
                    + Cities.COLUMN_NAME_HAS_SALE_LISTINGS + " INTEGER, "
                    + Cities.COLUMN_NAME_HAS_RENT_LISTINGS + " INTEGER"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + States.TABLE_NAME + " ("
                    + States.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + States.COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE, "
                    + States.COLUMN_NAME_HAS_SALES_LISTINGS + " INTEGER, "
                    + States.COLUMN_NAME_HAS_RENT_LISTINGS + " INTEGER"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + MarketRents.TABLE_NAME + " ("
                    + MarketRents.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + MarketRents.COLUMN_NAME_ADDRESS + " TEXT NOT NULL, "
                    + MarketRents.COLUMN_NAME_PROPERTY_TYPE + " TEXT NOT NULL, "
                    + MarketRents.COLUMN_NAME_LISTED_DATE + " TEXT NOT NULL, "
                    + MarketRents.COLUMN_NAME_ZIP_CODE + " INTEGER, "
                    + MarketRents.COLUMN_NAME_RENT + " REAL NOT NULL, "
                    + MarketRents.COLUMN_NAME_NUM_BED + " INTEGER, "
                    + MarketRents.COLUMN_NAME_NUM_BATH + " REAL, "
                    + MarketRents.COLUMN_NAME_SQFT + " INTEGER, "
                    + MarketRents.COLUMN_NAME_CITY + " TEXT NOT NULL, "
                    + MarketRents.COLUMN_NAME_STATE + " TEXT NOT NULL"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + FairRents.TABLE_NAME + " ("
                    + FairRents.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + FairRents.COLUMN_NAME_ZIP_CODE + " INTEGER NOT NULL UNIQUE, "
                    + FairRents.COLUMN_NAME_STUDIO + " INTEGER NOT NULL, "
                    + FairRents.COLUMN_NAME_ONE_BED + " INTEGER NOT NULL, "
                    + FairRents.COLUMN_NAME_TWO_BED + " INTEGER NOT NULL, "
                    + FairRents.COLUMN_NAME_THREE_BED + " INTEGER NOT NULL, "
                    + FairRents.COLUMN_NAME_FOUR_BED + " INTEGER NOT NULL"
                    + ")");

            createStateForListingView();

            // PREPARING STATEMENT WHEN STARTING THE APP INSTEAD OF COMPILING ON RUNNING
            // TIME EACH SQL QUERY
            // ConsoleLogger.getInstance().printMessage(INSERT_FAIR_RENT);
            queryCityForListingView = conn.prepareStatement(QUERY_CITY_FOR_LISTING_VIEW);
            insertIntoStates = conn.prepareStatement(INSERT_STATE, Statement.RETURN_GENERATED_KEYS);
            insertIntoCities = conn.prepareStatement(INSERT_CITY, Statement.RETURN_GENERATED_KEYS);
            insertIntoZipCodes = conn.prepareStatement(INSERT_ZIP_CODE, Statement.RETURN_GENERATED_KEYS);
            insertIntoListings = conn.prepareStatement(INSERT_LISTING, Statement.RETURN_GENERATED_KEYS);
            insertIntoMarketRents = conn.prepareStatement(INSERT_MARKET_RENT, Statement.RETURN_GENERATED_KEYS);
            insertIntoFairRents = conn.prepareStatement(INSERT_FAIR_RENT);
            queryStateId = conn.prepareStatement(QUERY_STATE_ID);
            queryState = conn.prepareStatement(QUERY_STATE);
            queryStateHasSalesListingsById = conn.prepareStatement(QUERY_STATE_HAS_SALES_LISTING_BY_ID);
            queryCityHasSalesListingsById = conn.prepareStatement(QUERY_CITY_HAS_SALES_LISTING_BY_ID);
            queryZipcodeHasSalesListingsById = conn.prepareStatement(QUERY_ZIPCODE_HAS_SALES_LISTING_BY_ID);
            queryStateHasRentListingsById = conn.prepareStatement(QUERY_STATE_HAS_RENT_LISTING_BY_ID);
            queryCityHasRentListingsById = conn.prepareStatement(QUERY_CITY_HAS_RENT_LISTING_BY_ID);
            queryZipcodeHasRentListingsById = conn.prepareStatement(QUERY_ZIPCODE_HAS_RENT_LISTING_BY_ID);
            queryZipCodeByNumber = conn.prepareStatement(QUERY_ZIPCODE_BY_NUMBER);
            queryZipCodeNumberById = conn.prepareStatement(QUERY_ZIPCODE_NUMBER_BY_ID);
            queryCityByNameAndStateId = conn.prepareStatement(QUERY_CITY_BY_NAME_AND_STATE_ID); // THERE CAN BE SEVERAL
                                                                                                // CITIES WITH THE SAME
                                                                                                // NAME ACROSS DIFERENET
                                                                                                // STATES; THEREFORE, WE
                                                                                                // NEED TO QUERY CITY BY
                                                                                                // NAME AND STATE ID
            queryZipCodeByNumberAndCityId = conn.prepareStatement(QUERY_ZIPCODE_BY_ZIPCODE_AND_CITY_ID);
            queryZipCodeByCityName = conn.prepareStatement(QUERY_ZIP_CODE_BY_CITY_START);
            queryListing = conn.prepareStatement(QUERY_LISTING);
            queryRentRate = conn.prepareStatement(QUERY_RENT_RATES);
            queryCityByStateId = conn.prepareStatement(QUERY_CITIES_BY_STATE_ID);
            queryZipCodeByCityId = conn.prepareStatement(QUERY_ZIP_CODES_BY_CITY_ID);
            queryListingsByZipCodeId = conn.prepareStatement(QUERY_LISTINGS_BY_ZIP_CODE_ID);
            queryListingsByZipCodeNumber = conn.prepareStatement(QUERY_LISTINGS_BY_ZIP_CODE_NUMBER);
            queryListingsByCityName = conn.prepareStatement(QUERY_LISTINGS_BY_CITY_NAME);
            queryCityForListing = conn.prepareStatement(QUERY_CITY_FOR_LISTING_START);
            queryZipcodeByZipcodeNumber = conn.prepareStatement(QUERY_ZIPCODE_BY_ZIPCODE_NUMBER);
            queryCityByName = conn.prepareStatement(QUERY_CITY_BY_CITY_NAME);
            queryStatesForTableView = conn.prepareStatement(QUERY_STATES_FOR_TABLE_VIEW);
            queryCitiesForTableView = conn.prepareStatement(QUERY_CITIES_FOR_STATE_ID_AND_TABLE_VIEW);
            queryZipcodesForTableView = conn.prepareStatement(QUERY_ZIPCODES_FOR_CITY_ID_AND_TABLE_VIEW);
            updateCityName = conn.prepareStatement(UPDATE_CITY_NAME);
            updateListingPriceByAddress = conn.prepareStatement(UPDATE_LISTING_PRICE_BY_ADDRESS);
            updateStateHasSalesListingsById = conn.prepareStatement(UPDATE_STATE_HAS_SALES_LISTINGS_BY_ID);
            updateCityHasSalesListingsById = conn.prepareStatement(UPDATE_CITY_HAS_SALES_LISTINGS_BY_ID);
            updateZipcodeHasSalesListingsById = conn.prepareStatement(UPDATE_ZIPCODE_HAS_SALES_LISTINGS_BY_ID);
            updateStateHasRentListingsById = conn.prepareStatement(UPDATE_STATE_HAS_RENT_LISTINGS_BY_ID);
            updateCityHasRentListingsById = conn.prepareStatement(UPDATE_CITY_HAS_RENT_LISTINGS_BY_ID);
            updateZipcodeHasRentListingsById = conn.prepareStatement(UPDATE_ZIPCODE_HAS_RENT_LISTINGS_BY_ID);
            updateStatesAfterClearingMarketRentsDba = conn
                    .prepareStatement(UPDATE_STATES_AFTER_CLEARING_MARKET_RENT_DBA);
            updateCitiesAfterClearingMarketRentsDba = conn
                    .prepareStatement(UPDATE_CITIES_AFTER_CLEARING_MARKET_RENT_DBA);
            updateZipcodesAfterClearingMarketRentsDba = conn
                    .prepareStatement(UPDATE_ZIPCODES_AFTER_CLEARING_MARKET_RENT_DBA);
            deleteListingsTableData = conn.prepareStatement(DELETE_LISTINGS_TABLE_DATA);
            deleteZipcodesTableData = conn.prepareStatement(DELETE_ZIPCODES_TABLE_DATA);
            deleteCitiesTableData = conn.prepareStatement(DELETE_CITIES_TABLE_DATA);
            deleteStatesTableData = conn.prepareStatement(DELETE_STATES_TABLE_DATA);
            deleteStateById = conn.prepareStatement(DELETE_STATE_BY_ID);
            deleteCityById = conn.prepareStatement(DELETE_CITY_BY_ID);
            deleteZipcodeById = conn.prepareStatement(DELETE_ZIPCODE_BY_ID);
            deleteListingById = conn.prepareStatement(DELETE_LISTING_BY_ID);
            deleteListingsByZipcodeNumberAndZipcodeCity = conn
                    .prepareStatement(DELETE_LISTINGS_BY_ZIPCODE_NUMBER_AND_ZIPCODE_CITY);
            deleteZipcodeByZipcodeNumberAndZipcodeCity = conn
                    .prepareStatement(DELETE_ZIPCODES_BY_ZIPCODE_NUMBER_AND_ZIPCODE_CITY);
            deleteListingByCityName = conn.prepareStatement(DELETE_LISTINGS_BY_CITY_NAME);
            deleteZipcodesByCityName = conn.prepareStatement(DELETE_ZIPCODES_BY_CITY_NAME);
            deleteCityByName = conn.prepareStatement(DELETE_CITY_BY_NAME);
            deleteListingByStateName = conn.prepareStatement(DELETE_LISTINGS_BY_STATE_NAME);
            deleteZipcodesByStateName = conn.prepareStatement(DELETE_ZIPCODES_BY_STATE_NAME);
            deleteCityByStateName = conn.prepareStatement(DELETE_CITY_BY_STATE_NAME);
            deleteStateByName = conn.prepareStatement(DELETE_STATE_BY_NAME);
            deleteMarketRentTableData = conn.prepareStatement(DELETE_MARKET_RENT_TABLE_DATA);
            deleteFairMarketRentTableData = conn.prepareStatement(DELETE_FAIR_RENT_TABLE_DATA);
            getAverageListPriceByBedsBathsAndZipcode = conn
                    .prepareStatement(GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
            getAverageListPriceByBedsBathsAndCity = conn
                    .prepareStatement(GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_CITY);
            getAverageListPriceByStateIdBedsAndBaths = conn.prepareStatement(GET_AVERAGE_LISTINGS_PRICE_FOR_STATE_BY_ID_BEDS_AND_BATHS);
            getAverageRentByBedsBathsAndZipcode = conn.prepareStatement(GET_AVERAGE_RENT_BY_ZIPCODE_BEDS_BATHS);
            getAverageRentByBedsBathsAndCity = conn.prepareStatement(GET_AVERAGE_RENT_BY_CITY_BEDS_BATHS);
            getAverageRentByStateIdBedsAndBaths = conn.prepareStatement(GET_AVERAGE_RENT_FOR_STATE_BY_ID_BEDS_AND_BATHS);
            getFairMarketRentByZipcode = conn.prepareStatement(QUERY_FAIR_RENTS_BY_ZIP_CODE);
            getListingByZipcodeAndUnderWrittenValue = conn
                    .prepareStatement(GET_LISTINGS_BY_ZIPCODE_WITH_LIST_PRICE_LESS_EQUAL_TO_UNDERWRITTEN_VALUE);
            getListingByCityAndUnderWrittenValue = conn
                    .prepareStatement(GET_LISTINGS_BY_CITY_WITH_LIST_PRICE_LESS_EQUAL_TO_UNDERWRITTEN_VALUE);
            return true;
        } catch (SQLException e) {
            // ConsoleLogger.getInstance().printErrorMessage("Coudn't connect to the
            // database: ", e);
            System.err.println("Coudn't connect to the database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try { // ORDER IS IMPORTANT!
            if (queryCityForListingView != null) {
                queryCityForListingView.close();
            }
            if (insertIntoCities != null) {
                insertIntoCities.close();
            }
            if (insertIntoZipCodes != null) {
                insertIntoZipCodes.close();
            }
            if (insertIntoListings != null) {
                insertIntoListings.close();
            }
            if (insertIntoMarketRents != null) {
                insertIntoMarketRents.close();
            }
            if (insertIntoFairRents != null) {
                insertIntoFairRents.close();
            }
            if (queryState != null) {
                queryState.close();
            }
            if (queryStateHasSalesListingsById != null) {
                queryStateHasSalesListingsById.close();
            }
            if (queryCityHasSalesListingsById != null) {
                queryCityHasSalesListingsById.close();
            }
            if (queryZipcodeHasSalesListingsById != null) {
                queryZipcodeHasSalesListingsById.close();
            }
            if (queryStateHasRentListingsById != null) {
                queryStateHasRentListingsById.close();
            }
            if (queryCityHasRentListingsById != null) {
                queryCityHasRentListingsById.close();
            }
            if (queryZipcodeHasRentListingsById != null) {
                queryZipcodeHasRentListingsById.close();
            }
            if (queryStateId != null) {
                queryStateId.close();
            }
            if (queryZipCodeByNumber != null) {
                queryZipCodeByNumber.close();
            }
            if (queryZipCodeNumberById != null) {
                queryZipCodeNumberById.close();
            }
            if (queryCityByNameAndStateId != null) {
                queryCityByNameAndStateId.close();
            }
            if (queryZipCodeByNumberAndCityId != null) {
                queryZipCodeByNumberAndCityId.close();
            }
            if (queryZipCodeByCityName != null) {
                queryZipCodeByCityName.close();
            }
            if (queryListing != null) {
                queryListing.close();
            }
            if (queryRentRate != null) {
                queryRentRate.close();
            }
            if (queryCityByStateId != null) {
                queryCityByStateId.close();
            }
            if (queryZipCodeByCityId != null) {
                queryZipCodeByCityId.close();
            }
            if (queryListingsByZipCodeId != null) {
                queryListingsByZipCodeId.close();
            }
            if (queryListingsByZipCodeNumber != null) {
                queryListingsByZipCodeNumber.close();
            }
            if (queryListingsByCityName != null) {
                queryListingsByCityName.close();
            }
            if (queryCityForListing != null) {
                queryCityForListing.close();
            }
            if (queryZipcodeByZipcodeNumber != null) {
                queryZipcodeByZipcodeNumber.close();
            }
            if (queryCityByName != null) {
                queryCityByName.close();
            }
            if (queryStatesForTableView != null) {
                queryStatesForTableView.close();
            }
            if (queryCitiesForTableView != null) {
                queryCitiesForTableView.close();
            }
            if (queryZipcodesForTableView != null) {
                queryZipcodesForTableView.close();
            }
            if (updateCityName != null) {
                updateCityName.close();
            }
            if (updateListingPriceByAddress != null) {
                updateListingPriceByAddress.close();
            }
            if (updateStateHasSalesListingsById != null) {
                updateStateHasSalesListingsById.close();
            }
            if (updateCityHasSalesListingsById != null) {
                updateCityHasSalesListingsById.close();
            }
            if (updateZipcodeHasSalesListingsById != null) {
                updateZipcodeHasSalesListingsById.close();
            }
            if (updateStateHasRentListingsById != null) {
                updateStateHasRentListingsById.close();
            }
            if (updateCityHasRentListingsById != null) {
                updateCityHasRentListingsById.close();
            }
            if (updateZipcodeHasRentListingsById != null) {
                updateZipcodeHasRentListingsById.close();
            }
            if (updateStatesAfterClearingMarketRentsDba != null) {
                updateStatesAfterClearingMarketRentsDba.close();
            }
            if (updateCitiesAfterClearingMarketRentsDba != null) {
                updateCitiesAfterClearingMarketRentsDba.close();
            }
            if (updateZipcodesAfterClearingMarketRentsDba != null) {
                updateZipcodesAfterClearingMarketRentsDba.close();
            }
            if (deleteListingsTableData != null) {
                deleteListingsTableData.close();
            }
            if (deleteZipcodesTableData != null) {
                deleteZipcodesTableData.close();
            }
            if (deleteCitiesTableData != null) {
                deleteCitiesTableData.close();
            }
            if (deleteStatesTableData != null) {
                deleteStatesTableData.close();
            }
            if (deleteMarketRentTableData != null) {
                deleteMarketRentTableData.close();
            }
            if (deleteFairMarketRentTableData != null) {
                deleteFairMarketRentTableData.close();
            }
            if (deleteStateById != null) {
                deleteStateById.close();
            }
            if (deleteCityById != null) {
                deleteCityById.close();
            }
            if (deleteZipcodeById != null) {
                deleteZipcodeById.close();
            }
            if (deleteListingById != null) {
                deleteListingById.close();
            }
            if (deleteListingsByZipcodeNumberAndZipcodeCity != null) {
                deleteListingsByZipcodeNumberAndZipcodeCity.close();
            }
            if (deleteZipcodeByZipcodeNumberAndZipcodeCity != null) {
                deleteZipcodeByZipcodeNumberAndZipcodeCity.close();
            }
            if (deleteListingByCityName != null) {
                deleteListingByCityName.close();
            }
            if (deleteZipcodesByCityName != null) {
                deleteZipcodesByCityName.close();
            }
            if (deleteCityByName != null) {
                deleteCityByName.close();
            }
            if (deleteListingByStateName != null) {
                deleteListingByStateName.close();
            }
            if (deleteZipcodesByStateName != null) {
                deleteZipcodesByStateName.close();
            }
            if (deleteCityByStateName != null) {
                deleteCityByStateName.close();
            }
            if (deleteStateByName != null) {
                deleteStateByName.close();
            }
            if (getAverageListPriceByBedsBathsAndZipcode != null) {
                getAverageListPriceByBedsBathsAndZipcode.close();
            }
            if (getAverageListPriceByBedsBathsAndCity != null) {
                getAverageListPriceByBedsBathsAndCity.close();
            }
            if (getAverageListPriceByStateIdBedsAndBaths != null) {
                getAverageListPriceByStateIdBedsAndBaths.close();
            }
            if (getAverageRentByBedsBathsAndZipcode != null) {
                getAverageRentByBedsBathsAndZipcode.close();
            }
            if (getAverageRentByBedsBathsAndCity != null) {
                getAverageRentByBedsBathsAndCity.close();
            }
            if (getAverageRentByStateIdBedsAndBaths != null) {
                getAverageRentByStateIdBedsAndBaths.close();
            }
            if (getFairMarketRentByZipcode != null) {
                getFairMarketRentByZipcode.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Coudn't close connection: ", e);
        }
    }

    /**
     * DATABAE QUERIES
     */
    /**
     * This function executes a SQL query to get the average list price given
     * the number of beds, baths and the zipcode
     *
     * @param numBeds
     * @param numBaths
     * @param zipcode
     * @return averagelistPrice considering the parameters (numBeds, numBaths,
     *         zipcode)
     * @throws Exception
     */
    public double findAverageListPriceByBedsBathsAndZipcode(int zipcode, int numBeds, double numBaths) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getAverageListPriceByBedsBathsAndZipcode.setInt(1, numBeds);
        getAverageListPriceByBedsBathsAndZipcode.setDouble(2, numBaths);
        getAverageListPriceByBedsBathsAndZipcode.setInt(3, zipcode);

        ResultSet resultSet = getAverageListPriceByBedsBathsAndZipcode.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public double findAverageListPriceByBedsBathsAndCity(String city, int numBeds, double numBaths) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getAverageListPriceByBedsBathsAndCity.setInt(1, numBeds);
        getAverageListPriceByBedsBathsAndCity.setDouble(2, numBaths);
        getAverageListPriceByBedsBathsAndCity.setString(3, city);
        ResultSet resultSet = getAverageListPriceByBedsBathsAndCity.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public double findAverageListPriceByStateIdBedsAndBaths(int id, int numBeds, double numBaths) throws Exception {
        //ConsoleLogger.getInstance().printMessage("DEBUGGER: " + GET_AVERAGE_LISTINGS_FOR_STATE_BY_ID_BEDS_AND_BATHS);
        //ConsoleLogger.getInstance().printMessage("id=" + id + " numBeds=" + numBeds + " numBaths=" + numBaths);
        getAverageListPriceByStateIdBedsAndBaths.setInt(1, id);
        getAverageListPriceByStateIdBedsAndBaths.setInt(2, numBeds);
        getAverageListPriceByStateIdBedsAndBaths.setDouble(3, numBaths);
        ResultSet resultSet = getAverageListPriceByStateIdBedsAndBaths.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public double findAverageRentByBedsBathsAndZipcode(int zipcode, int numBeds, double numBaths) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getAverageRentByBedsBathsAndZipcode.setInt(1, numBeds);
        getAverageRentByBedsBathsAndZipcode.setDouble(2, numBaths);
        getAverageRentByBedsBathsAndZipcode.setInt(3, zipcode);
        ResultSet resultSet = getAverageRentByBedsBathsAndZipcode.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public double findAverageRentByBedsBathsAndCity(String city, int numBeds, double numBaths) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getAverageRentByBedsBathsAndCity.setInt(1, numBeds);
        getAverageRentByBedsBathsAndCity.setDouble(2, numBaths);
        getAverageRentByBedsBathsAndCity.setString(3, city);
        ResultSet resultSet = getAverageRentByBedsBathsAndCity.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public double findAverageRentByStateIdBedsAndBaths(int id, int numBeds, double numBaths) throws Exception {
        //ConsoleLogger.getInstance().printMessage("DEBUGGER: " + GET_AVERAGE_RENT_FOR_STATE_BY_ID_BEDS_AND_BATHS);
        //ConsoleLogger.getInstance().printMessage("id=" + id + " numBeds=" + numBeds + " numBaths=" + numBaths);
        getAverageRentByStateIdBedsAndBaths.setInt(1, id);
        getAverageRentByStateIdBedsAndBaths.setInt(2, numBeds);
        getAverageRentByStateIdBedsAndBaths.setDouble(3, numBaths);
        ResultSet resultSet = getAverageRentByStateIdBedsAndBaths.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return -1;
        }
    }

    public FairRentRates findFairMarketRentByZipcode(int zipcode) throws SQLException {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getFairMarketRentByZipcode.setInt(1, zipcode);
        ResultSet resultSet = getFairMarketRentByZipcode.executeQuery();
        FairRentRates fmr = null;
        if (resultSet.next()) {
            fmr = new FairRentRates(
                    resultSet.getInt(1), // zipcode
                    resultSet.getInt(2), // studio
                    resultSet.getInt(3), // one bed
                    resultSet.getInt(4), // two bed
                    resultSet.getInt(5), // three bed
                    resultSet.getInt(6) // four bed
            );
            return fmr;
        } else {
            return fmr;
        }
    }

    public List<Listing> findListingsByZipcodeAndUnderwrittenValue(int zipcode, int numBeds, double numBaths,
            double capRate) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getListingByZipcodeAndUnderWrittenValue.setInt(1, numBeds);
        getListingByZipcodeAndUnderWrittenValue.setDouble(2, numBaths);
        getListingByZipcodeAndUnderWrittenValue.setInt(3, zipcode);
        getListingByZipcodeAndUnderWrittenValue.setInt(4, numBeds);
        getListingByZipcodeAndUnderWrittenValue.setDouble(5, numBaths);
        getListingByZipcodeAndUnderWrittenValue.setInt(6, zipcode);
        getListingByZipcodeAndUnderWrittenValue.setDouble(7, capRate);

        try {
            List<Listing> listings = new ArrayList<>();
            ResultSet results = getListingByZipcodeAndUnderWrittenValue.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(1));
                listing.setAddress(results.getString(2));
                listing.setPropertyType(results.getString(3));
                listing.setZipcode(results.getInt(4));
                listing.setListPrice(results.getInt(5));
                listing.setNumBeds(results.getInt(6));
                listing.setNumBaths(results.getDouble(7));
                listing.setSquareFootage(results.getInt(8));
                listing.setYeartBuilt(results.getInt(9));
                listing.setLatitude(results.getDouble(10));
                listing.setLongitude(results.getDouble(11));
                listing.setUrl(results.getString(12));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Listing> findListingsByCityAndTheUnderwrittenValue(String city, int numBeds, double numBaths,
            double capRate) throws Exception {
        // ConsoleLogger.getInstance().printMessage("DEBUGGER: " +
        // GET_AVERAGE_LISTING_PRICE_BY_NUM_BEDS_BATHS_ZIPCODE);
        getListingByCityAndUnderWrittenValue.setInt(1, numBeds);
        getListingByCityAndUnderWrittenValue.setDouble(2, numBaths);
        getListingByCityAndUnderWrittenValue.setString(3, city);
        getListingByCityAndUnderWrittenValue.setInt(4, numBeds);
        getListingByCityAndUnderWrittenValue.setDouble(5, numBaths);
        getListingByCityAndUnderWrittenValue.setString(6, city);
        getListingByCityAndUnderWrittenValue.setDouble(7, capRate);
        try {
            List<Listing> listings = new ArrayList<>();
            ResultSet results = getListingByCityAndUnderWrittenValue.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(1));
                listing.setAddress(results.getString(2));
                listing.setPropertyType(results.getString(3));
                listing.setZipcode(results.getInt(4));
                listing.setListPrice(results.getInt(5));
                listing.setNumBeds(results.getInt(6));
                listing.setNumBaths(results.getDouble(7));
                listing.setSquareFootage(results.getInt(8));
                listing.setYeartBuilt(results.getInt(9));
                listing.setLatitude(results.getDouble(10));
                listing.setLongitude(results.getDouble(11));
                listing.setUrl(results.getString(12));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<RealEstateState> findAllStates(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(States.TABLE_NAME);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY " + States.TABLE_NAME + '.' + States.COLUMN_NAME_NAME + " COLLATE NOCASE");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append(" DESC");
            } else {
                sb.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(sb.toString());) {
            List<RealEstateState> states = new ArrayList<>();
            while (results.next()) {
                RealEstateState state = new RealEstateState();
                state.setId(results.getInt(States.INDEX_ID));
                state.setName(results.getString(States.INDEX_NAME));
                states.add(state);
            }
            return states;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<RealEstateState> findStatesForTableView() {
        List<RealEstateState> states = new ArrayList<>();
        try {
            ResultSet results = queryStatesForTableView.executeQuery();
            while (results.next()) {
                RealEstateState state = new RealEstateState();
                state.setId(results.getInt(States.INDEX_ID));
                state.setName(results.getString(States.INDEX_NAME));
                int has_sales_listings = results.getInt(States.COLUMN_NAME_HAS_SALES_LISTINGS);
                state.setHasRentListings(results.getInt(States.COLUMN_NAME_HAS_RENT_LISTINGS));
                // Uneccesary has the query incluedes a WHERE statements including
                // has_sales_listings = 1
                if (has_sales_listings == 1) {
                    states.add(state);
                }
            }
        } catch (SQLException ex) {
            ConsoleLogger.getInstance().printMessage("findStatesForTableView() failed");
            return null;
        }
        return states;
    }

    public List<City> findCitiesByStateIdForTableView(int stateId) {
        List<City> cities = new ArrayList<>();
        try {
            queryCitiesForTableView.setInt(1, stateId);
            ResultSet results = queryCitiesForTableView.executeQuery();
            while (results.next()) {
                City city = new City();
                city.setId(results.getInt(Cities.INDEX_ID));
                city.setName(results.getString(Cities.INDEX_NAME));
                city.setStateId(results.getInt(Cities.INDEX_STATE));
                city.setHasRentListings(results.getInt(Cities.INDEX_HAS_RENT_LISTINGS));
                cities.add(city);
            }
        } catch (SQLException ex) {
            ConsoleLogger.getInstance().printMessage("findCitiesByStateIdForTableView() failed");
            return null;
        }
        return cities;
    }

    public List<ZipCode> findZipcodesByCityIdForTableView(int cityId) {
        List<ZipCode> zipcodes = new ArrayList<>();
        try {
            queryZipcodesForTableView.setInt(1, cityId);
            ResultSet results = queryZipcodesForTableView.executeQuery();
            while (results.next()) {
                ZipCode zipcode = new ZipCode();
                zipcode.setId(results.getInt(Zipcodes.INDEX_ID));
                zipcode.setZipcode(results.getInt(Zipcodes.INDEX_NUMBER));
                zipcode.setCityId(results.getInt(Zipcodes.INDEX_CITY));
                zipcode.setHasRentListings(results.getInt(Zipcodes.INDEX_HAS_RENT_LISTINGS));
                zipcodes.add(zipcode);

            }
            return zipcodes;
        } catch (SQLException ex) {
            ConsoleLogger.getInstance().printMessage("findZipcodesByCityIdForTableView() failed");
            return null;
        }
    }

    public City findCityByName(String cityName) {
        try {
            City city = new City();
            queryCityByName.setString(1, cityName);
            ResultSet results = queryCityByName.executeQuery();
            if (results.next()) {
                city.setId(results.getInt(1));
                city.setName(results.getString(2));
                city.setStateId(results.getInt(3));
            }
            return city;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public ZipCode findZipcodeByZipcodeNumber(int zipcodeNum) {
        try {
            ZipCode zipcode = new ZipCode();
            queryZipcodeByZipcodeNumber.setInt(1, zipcodeNum);
            ResultSet results = queryZipcodeByZipcodeNumber.executeQuery();
            if (results.next()) {
                zipcode.setId(results.getInt(1));
                zipcode.setZipcode(results.getInt(2));
                zipcode.setCityId(results.getInt(3));
            }
            return zipcode;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<City> findCitiesForStateId(int id) {
        try {
            List<City> cities = new ArrayList<>();
            queryCityByStateId.setInt(1, id);
            ResultSet results = queryCityByStateId.executeQuery();
            while (results.next()) {
                City city = new City();
                city.setId(results.getInt(1));
                city.setName(results.getString(2));
                city.setStateId(results.getInt(3));
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<ZipCode> findZipCodesForCityId(int id) {
        try {
            List<ZipCode> zipCodes = new ArrayList<>();
            queryZipCodeByCityId.setInt(1, id);
            ResultSet results = queryZipCodeByCityId.executeQuery();
            while (results.next()) {
                ZipCode zipCode = new ZipCode();
                zipCode.setId(results.getInt(1));
                zipCode.setZipcode(results.getInt(2));
                zipCode.setCityId(results.getInt(3));
                zipCodes.add(zipCode);
            }
            return zipCodes;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Listing> findListingsForZipCodeId(int id) {
        try {
            List<Listing> listings = new ArrayList<>();
            queryListingsByZipCodeId.setInt(1, id);
            ResultSet results = queryListingsByZipCodeId.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(1));
                listing.setAddress(results.getString(2));
                listing.setPropertyType(results.getString(3));
                listing.setZipcode(results.getInt(4));
                listing.setListPrice(results.getInt(5));
                listing.setNumBeds(results.getInt(6));
                listing.setNumBaths(results.getDouble(7));
                listing.setSquareFootage(results.getInt(8));
                listing.setYeartBuilt(results.getInt(9));
                listing.setLatitude(results.getDouble(10));
                listing.setLongitude(results.getDouble(11));
                listing.setUrl(results.getString(12));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Listing> findListingsByZipCodeNumber(int zipcode) {
        try {
            List<Listing> listings = new ArrayList<>();
            queryListingsByZipCodeNumber.setInt(1, zipcode);
            ResultSet results = queryListingsByZipCodeNumber.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(1));
                listing.setAddress(results.getString(2));
                listing.setPropertyType(results.getString(3));
                listing.setZipcode(results.getInt(4));
                listing.setListPrice(results.getInt(5));
                listing.setNumBeds(results.getInt(6));
                listing.setNumBaths(results.getDouble(7));
                listing.setSquareFootage(results.getInt(8));
                listing.setYeartBuilt(results.getInt(9));
                listing.setLatitude(results.getDouble(10));
                listing.setLongitude(results.getDouble(11));
                listing.setUrl(results.getString(12));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public boolean updateCityName(int id, String newName) {
        try {
            updateCityName.setString(1, newName);
            updateCityName.setInt(2, id);
            int affectedReccords = updateCityName.executeUpdate();
            return affectedReccords == 1;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateListingPriceByAddress(double newListPrice, String address) {
        try {
            updateListingPriceByAddress.setDouble(1, newListPrice);
            updateListingPriceByAddress.setString(2, address);
            int affectedReccords = updateListingPriceByAddress.executeUpdate();
            return affectedReccords == 1;

        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Query failed: ", e);
            return false;
        }
    }

    public void deleteListingsTableData() {
        try {
            deleteListingsTableData.execute();
            // ConsoleLogger.getInstance().printMessage("Listings table data deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteZipcodesTableData() {
        try {
            deleteZipcodesTableData.execute();
            // ConsoleLogger.getInstance().printMessage("Zipcodes table data deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteCitiesTableData() {
        try {
            deleteCitiesTableData.execute();
            ConsoleLogger.getInstance().printMessage("Cities table data deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteStatesTableData() {
        try {
            deleteStatesTableData.execute();
            ConsoleLogger.getInstance().printMessage("Cities table data deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteMarketRentTableData() {
        try {
            // Removes data from the Markets rents table
            deleteMarketRentTableData.execute();
            // Updates the has_market_rent field to zero from state table
            updateStatesAfterClearingMarketRentsDba.execute();
            // Updates the has_market_rent field to zero from cities table
            updateCitiesAfterClearingMarketRentsDba.execute();
            // Updates the has_market_rent field to zero from zipcode table
            updateZipcodesAfterClearingMarketRentsDba.execute();
            // ConsoleLogger.getInstance().printMessage("Market rent table data deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteFairMarketRentTableData() {
        try {
            deleteFairMarketRentTableData.execute();
            // ConsoleLogger.getInstance().printMessage("Fair Market rent table data deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Table data couldn't be deleted: ", e);
        }
    }

    public void deleteStateById(int stateID) {
        try {
            deleteStateById.setInt(1, stateID);
            deleteStateById.execute();
            // ConsoleLogger.getInstance().printMessage("State with id " + stateID + " was
            // deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("State couldn't be deleted: ", e);
        }
    }

    public void deleteCityById(int cityID) {
        try {
            deleteCityById.setInt(1, cityID);
            deleteCityById.execute();
            // ConsoleLogger.getInstance().printMessage("City with id " + cityID + " was
            // deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("City couldn't be deleted: ", e);
        }
    }

    public void deleteZipcodeById(int zipcodeID) {
        try {
            deleteZipcodeById.setInt(1, zipcodeID);
            deleteZipcodeById.execute();
            ConsoleLogger.getInstance().printMessage("Zipcode with id " + zipcodeID + " was deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Zipcode couldn't be deleted: ", e);
        }
    }

    public void deleteListingById(int listingID) {
        try {
            deleteListingById.setInt(1, listingID);
            deleteListingById.execute();
            ConsoleLogger.getInstance().printMessage("Listing with id " + listingID + " was deleted succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Listing couldn't be deleted: ", e);
        }
    }

    public void deleteZipcodeByZipcodeNumberAndZipcodeCity(int zipcodeNumber, int zipcodeCity) {
        try {
            /* DELETE LISTING BY CITY NAME */
            deleteListingsByZipcodeNumberAndZipcodeCity.setInt(1, zipcodeNumber);
            deleteListingsByZipcodeNumberAndZipcodeCity.setInt(2, zipcodeCity);
            /* DELETE ZIPCODE BY CITY NAME */
            deleteZipcodeByZipcodeNumberAndZipcodeCity.setInt(1, zipcodeNumber);
            deleteZipcodeByZipcodeNumberAndZipcodeCity.setInt(2, zipcodeCity);
            deleteListingsByZipcodeNumberAndZipcodeCity.execute();
            deleteZipcodeByZipcodeNumberAndZipcodeCity.execute();
            // ConsoleLogger.getInstance().printMessage(zipcodeNumber + " zipcode was
            // deleted succesfully");
        } catch (SQLException e) {
            // ConsoleLogger.getInstance().printErrorMessage("Zipcode couldn't be deleted:
            // ", e);
        }
    }

    public void deleteCityByName(String cityName) {
        try {
            /* DELETE LISTING BY CITY NAME */
            deleteListingByCityName.setString(1, cityName);
            /* DELETE ZIPCODE BY CITY NAME */
            deleteZipcodesByCityName.setString(1, cityName);
            /* DELTE CITY BY NAME */
            deleteCityByName.setString(1, cityName);
            deleteListingByCityName.execute();
            deleteZipcodesByCityName.execute();
            deleteCityByName.execute();
            // ConsoleLogger.getInstance().printMessage(cityName + " city was deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("City couldn't be deleted: ", e);
        }
    }

    public void deleteStateByName(String stateName) {
        try {
            /* DELETE LISTING BY STATE NAME */
            deleteListingByStateName.setString(1, stateName);
            /* DELETE ZIPCODE BY STATE NAME */
            deleteZipcodesByStateName.setString(1, stateName);
            /* DELETE CITY BY STATE NAME */
            deleteCityByStateName.setString(1, stateName);
            deleteStateByName.setString(1, stateName);
            deleteListingByStateName.execute();
            deleteZipcodesByStateName.execute();
            deleteCityByStateName.execute();
            deleteStateByName.execute();
            // ConsoleLogger.getInstance().printMessage(stateName + " state was deleted
            // succesfully");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("State couldn't be deleted: ", e);
        }
    }

    public List<Integer> findZipCodesByCityName(String cityName) {
        try {
            queryZipCodeByCityName.setString(1, cityName);
            ResultSet results = queryZipCodeByCityName.executeQuery();
            List<Integer> zipCodes = new ArrayList<>();
            while (results.next()) {
                zipCodes.add(results.getInt(1));
            }
            return zipCodes;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Listing> findListingsByZipcodeId(int id) {
        try {
            List<Listing> listings = new ArrayList<>();
            queryListingsByZipCodeId.setInt(1, id);
            ResultSet results = queryListingsByZipCodeId.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(Listings.INDEX_ID));
                listing.setAddress(results.getString(Listings.INDEX_ADDRESS));
                listing.setPropertyType(results.getString(Listings.INDEX_PROPERTY_TYPE));
                listing.setZipcode(results.getInt(Listings.INDEX_ZIP_CODE));
                listing.setListPrice(results.getInt(Listings.INDEX_LIST_PRICE));
                listing.setNumBeds(results.getInt(Listings.INDEX_NUM_BED));
                listing.setNumBaths(results.getDouble(Listings.INDEX_NUM_BATH));
                listing.setSquareFootage(results.getInt(Listings.INDEX_SQFT));
                listing.setYeartBuilt(results.getInt(Listings.INDEX_YEAR_BUILT));
                listing.setUrl(results.getString(Listings.INDEX_URL));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Listing> findListingsByCityName(String cityName) {
        try {
            queryListingsByCityName.setString(1, cityName);
            List<Listing> listings = new ArrayList<>();
            ResultSet results = queryListingsByCityName.executeQuery();
            while (results.next()) {
                Listing listing = new Listing();
                listing.setId(results.getInt(1));
                listing.setAddress(results.getString(2));
                listing.setPropertyType(results.getString(3));
                listing.setZipcode(results.getInt(4));
                listing.setListPrice(results.getInt(5));
                listing.setNumBeds(results.getInt(6));
                listing.setNumBaths(results.getDouble(7));
                listing.setSquareFootage(results.getInt(8));
                listing.setYeartBuilt(results.getInt(9));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<ListingCity> findCityForListing(String listingAddress) {
        try {
            queryCityForListing.setString(1, listingAddress);
            List<ListingCity> listingCities = new ArrayList<>();
            ResultSet results = queryCityForListing.executeQuery();
            while (results.next()) {
                ListingCity listingCity = new ListingCity();
                listingCity.setCityName(results.getString(1));
                listingCity.setAddress(results.getString(2));
                listingCity.setPropertyType(results.getString(3));
                listingCity.setZipcode(results.getInt(4));
                listingCity.setListPrice(results.getInt(5));
                listingCity.setNumBeds(results.getInt(6));
                listingCity.setNumBaths(results.getDouble(7));
                listingCity.setSquareFootage(results.getInt(8));
                listingCity.setYearBuilt(results.getInt(9));
                listingCities.add(listingCity);
            }
            return listingCities;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public void findListingsMetadata() {
        String sql = "SELECT * FROM " + Listings.TABLE_NAME;
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(sql)) {
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i = 1; i <= numColumns; ++i) {
                System.out.format("Column %d in the listing table is named %s\n", i, meta.getColumnName(i));
            }
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printMessage("Query failed: " + e.getMessage());
        }
    }

    public boolean createStateForListingView() {
        try (Statement statement = conn.createStatement()) {
            return statement.execute(CREATE_STATE_FOR_LISTING_VIEW);
        } catch (SQLException e) {
            System.out.println("Create View failed: " + e.getMessage());
            // System.err.println("SQL Debug: " + CREATE_ARTISTS_FOR_SONG_VIEW);
            return false;
        }
    }

    public List<ListingCity> findListingsForCityView(String address) {
        try {
            queryCityForListing.setString(1, address);
            List<ListingCity> listingCities = new ArrayList<>();
            ResultSet results = queryCityForListing.executeQuery();
            while (results.next()) {
                ListingCity listingCity = new ListingCity();
                listingCity.setCityName(results.getString(1));
                listingCity.setAddress(results.getString(2));
                listingCity.setPropertyType(results.getString(3));
                listingCity.setZipcode(results.getInt(4));
                listingCity.setListPrice(results.getInt(5));
                listingCity.setNumBeds(results.getInt(6));
                listingCity.setNumBaths(results.getDouble(7));
                listingCity.setSquareFootage(results.getInt(8));
                listingCity.setYearBuilt(results.getInt(9));
                listingCities.add(listingCity);
            }
            return listingCities;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public int findStateIdByName(String name) throws SQLException {
        queryStateId.setString(1, name);
        ResultSet results = queryStateId.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findStateHasSalesListingsById(int Id) throws SQLException {
        queryStateHasSalesListingsById.setInt(1, Id);
        ResultSet results = queryStateHasSalesListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findCityHasSalesListingsById(int Id) throws SQLException {
        queryCityHasSalesListingsById.setInt(1, Id);
        ResultSet results = queryCityHasSalesListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findZipcodeHasSalesListingsById(int Id) throws SQLException {
        queryZipcodeHasSalesListingsById.setInt(1, Id);
        ResultSet results = queryZipcodeHasSalesListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findStateHasRentListingsById(int Id) throws SQLException {
        queryStateHasRentListingsById.setInt(1, Id);
        ResultSet results = queryStateHasRentListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findCityHasRentListingsById(int Id) throws SQLException {
        queryCityHasRentListingsById.setInt(1, Id);
        ResultSet results = queryCityHasRentListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private int findZipcodeHasRentListingsById(int Id) throws SQLException {
        queryZipcodeHasRentListingsById.setInt(1, Id);
        ResultSet results = queryZipcodeHasRentListingsById.executeQuery(); // RETURNS ONLY THE ID COLUMN
        return results.getInt(1);
    }

    private boolean updateStateHasSalesListingsById(int Id) throws SQLException {
        updateStateHasSalesListingsById.setInt(1, Id);
        int rowsAffected = updateStateHasSalesListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    private boolean updateCityHasSalesListingsById(int Id) throws SQLException {
        updateCityHasSalesListingsById.setInt(1, Id);
        int rowsAffected = updateCityHasSalesListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    private boolean updateZipcodeHasSalesListingsById(int Id) throws SQLException {
        updateZipcodeHasSalesListingsById.setInt(1, Id);
        int rowsAffected = updateZipcodeHasSalesListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    private boolean updateStateHasRentListingsById(int Id) throws SQLException {
        updateStateHasRentListingsById.setInt(1, Id);
        int rowsAffected = updateStateHasRentListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    private boolean updateCityHasRentListingsById(int Id) throws SQLException {
        updateCityHasRentListingsById.setInt(1, Id);
        int rowsAffected = updateCityHasRentListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    private boolean updateZipcodeHasRentListingsById(int Id) throws SQLException {
        updateZipcodeHasRentListingsById.setInt(1, Id);
        int rowsAffected = updateZipcodeHasRentListingsById.executeUpdate(); // RETURNS ONLY THE ID COLUMN
        return rowsAffected == 1;
    }

    // INSERTS INTO STATE, CITY, ZIPCODE, LISTING METHODS
    private int insertState(String name, int updateHasSalesListings, int updateHasRentListings) throws SQLException {
        // Checking if state exists in the db
        queryState.setString(1, name);
        ResultSet results = queryState.executeQuery(); // RETURNS ALL THE COLUMNS FROM STATE TABLE (ID, NAME)
        if (results.next()) {
            int stateId = results.getInt(1);
            // Updates states has listing field since we are inserting a sales listings
            if (updateHasSalesListings == 1) {
                // Check if state has sales listing by state id. Returns 1 for true or 0 for
                // false
                int doesStateTableHasSalesListings = findStateHasSalesListingsById(stateId);
                if (doesStateTableHasSalesListings == 0) {
                    boolean wasUpdated = updateStateHasSalesListingsById(stateId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("State has sales listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't update state has sales listings");
                    }
                }
            }
            // Updates state has rent listing field since we are inserting a rent listings
            if (updateHasRentListings == 1) {
                int doesStateTableHasRentListings = findStateHasRentListingsById(stateId);
                if (doesStateTableHasRentListings == 0) {
                    boolean wasUpdated = updateStateHasRentListingsById(stateId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("State has rent listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't update state has rent listings");
                    }
                }
            }
            return stateId;
        } else {
            // state is not in the db, then insert the state
            insertIntoStates.setString(1, name.toUpperCase());
            insertIntoStates.setInt(2, updateHasSalesListings);
            insertIntoStates.setInt(3, updateHasRentListings);
            int affectedRows = insertIntoStates.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert state!");
            }
            // Get the id of the inserted state field to return it to insert sales listings
            ResultSet generatedKeys = insertIntoStates.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for state!");
            }
        }

    }

    private int insertCity(String name, int stateId, int updateHasSalesListings, int updateHasRentListings)
            throws SQLException {
        // Checking if city exists in the db
        queryCityByNameAndStateId.setString(1, name);
        queryCityByNameAndStateId.setInt(2, stateId);
        ResultSet results = queryCityByNameAndStateId.executeQuery(); // RETURNS ALL THE COLUMNS FROM CITY TABLE (ID,
                                                                      // NAME)
        if (results.next()) {
            int cityId = results.getInt(1);
            // Cheking if city has sales listings
            if (updateHasSalesListings == 1) {
                int doesCityTableHasSalesListings = findCityHasSalesListingsById(cityId);
                if (doesCityTableHasSalesListings == 0) {
                    boolean wasUpdated = updateCityHasSalesListingsById(cityId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("City has sales listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't update city has sales listings");
                    }
                }
            }
            // Checking if city has rent listings
            if (updateHasRentListings == 1) {
                int doesCityTableHasRentListings = findCityHasRentListingsById(cityId);
                if (doesCityTableHasRentListings == 0) {
                    boolean wasUpdated = updateCityHasRentListingsById(cityId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("City has rent listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't rent city has rent listings");
                    }
                }
            }
            return cityId;
        } else {
            // city is not in the db, then insert the city
            insertIntoCities.setString(1, name);
            insertIntoCities.setInt(2, stateId);
            insertIntoCities.setInt(3, updateHasSalesListings);
            insertIntoCities.setInt(4, updateHasRentListings);
            int affectedRows = insertIntoCities.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert city!");
            }

            ResultSet generatedKeys = insertIntoCities.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for city!");
            }
        }

    }

    private int insertZipCode(int zipcode, int cityId, int updateHasSalesListings, int updateHasRentListings)
            throws SQLException {
        // Checking if zipcode exists in the db
        queryZipCodeByNumberAndCityId.setInt(1, zipcode); // Replacing placeholde with provided name string to avoid SQL
                                                          // injection attacks
        queryZipCodeByNumberAndCityId.setInt(2, cityId);
        ResultSet results = queryZipCodeByNumberAndCityId.executeQuery();// RETURNS ALL THE COLUMNS OF ZIPCODE TABLE
                                                                         // (ID, ZIPCODE NUM, CITY_ID
        if (results.next()) {
            int zipcodeId = results.getInt(1);
            if (updateHasSalesListings == 1) {
                int doesZipcodeTableHasSalesListings = findZipcodeHasSalesListingsById(zipcodeId);
                if (doesZipcodeTableHasSalesListings == 0) {
                    boolean wasUpdated = updateZipcodeHasSalesListingsById(zipcodeId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("Zipcode has sales listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't update zipcode's has sales listings");
                    }
                }
            }
            if (updateHasRentListings == 1) {
                int doesZipcodeTableHasRentListings = findZipcodeHasRentListingsById(zipcodeId);
                if (doesZipcodeTableHasRentListings == 0) {
                    boolean wasUpdated = updateZipcodeHasRentListingsById(zipcodeId);
                    if (wasUpdated) {
                        ConsoleLogger.getInstance().printMessage("Zipcode has rent listings has been updated");
                    } else {
                        ConsoleLogger.getInstance().printMessage("Couldn't update zipcode's has rent listings");
                    }
                }
            }
            return zipcodeId;
        } else {
            // Zip Code is not in the db, then insert the Album
            insertIntoZipCodes.setInt(1, zipcode);
            insertIntoZipCodes.setInt(2, cityId);
            insertIntoZipCodes.setInt(3, updateHasSalesListings);
            insertIntoZipCodes.setInt(4, updateHasRentListings);
            int affectedRows = insertIntoZipCodes.executeUpdate();
            // ConsoleLogger.getInstance().printMessage("INSERT QUERY EXECUTED: affectedRows
            // " + affectedRows);
            if (affectedRows != 1) {
                ConsoleLogger.getInstance().printMessage("Couldn't insert zip code!");
                throw new SQLException("Couldn't insert zip code!");
            }
            ResultSet generatedKeys = insertIntoZipCodes.getGeneratedKeys();
            if (generatedKeys.next()) {
                // ConsoleLogger.getInstance().printMessage("HAS GENERATED KEYS!");
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for zip code!");
            }
        }
    }

    public boolean insertListing(
            String state, // 1
            String city, // 2
            String address, // 3
            String propertyType, // 4
            int zipCode, // 5
            double listPrice, // 6
            int numBed, // 7
            double numBath, // 8
            int sqft, // 9
            int yearBuilt, // 10
            double latitude, // 11
            double longitude, // 12
            String url) { // 13
        try {
            conn.setAutoCommit(false);
            // ConsoleLogger.getInstance().printMessage("INSIDE INSERT");
            int stateId = insertState(state, 1, 0); // 1 and 0 as a boolean for hasSalesListings since we are inserting
                                                    // a sale listings
            int cityId = insertCity(city, stateId, 1, 0);
            int zipCodeId = insertZipCode(zipCode, cityId, 1, 0); // FIXME HERE
            // ConsoleLogger.getInstance().printMessage("CITY ID: " + cityId + " ZIPCODE ID:
            // " + zipCodeId);
            // Checking if a listing exits in the db with the same address and zipcode
            queryListing.setString(1, address);
            queryListing.setInt(2, zipCodeId);
            ResultSet results = queryListing.executeQuery();
            if (results.next()) {
                ConsoleLogger.getInstance().printMessage("Address: " + address + " already in " + zipCode + " zipcode");
                return false;
            }
            // Replacing placeholders to avoid SQL injection attacks
            insertIntoListings.setString(1, address);
            insertIntoListings.setString(2, propertyType);
            insertIntoListings.setInt(3, zipCodeId);
            insertIntoListings.setDouble(4, listPrice);
            insertIntoListings.setInt(5, numBed);
            insertIntoListings.setDouble(6, numBath);
            insertIntoListings.setInt(7, sqft);
            insertIntoListings.setInt(8, yearBuilt);
            insertIntoListings.setDouble(9, latitude);
            insertIntoListings.setDouble(10, longitude);
            insertIntoListings.setString(11, url);

            int affectedRows = insertIntoListings.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
                ConsoleLogger.getInstance().printMessage("Listing " + url + " successfully added to the dba");
                return true;
            } else {
                throw new SQLException("Listing insert failed!");
            }
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Insert listing exception: ", e);
            try {
                ConsoleLogger.getInstance().printMessage("Performing rollback");
                conn.rollback();
                return false;
            } catch (SQLException e2) {
                ConsoleLogger.getInstance().printErrorMessage("Couldn't rollback trasaction: ", e2);
                return false;
            }
        } finally {
            try {
                ConsoleLogger.getInstance().printMessage("Resetting default commit behaviour");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                ConsoleLogger.getInstance().printMessage("Couldn't reset auto-commit! " + e.getMessage());
            }
            resetDBAConnection();
        }

    }

    public boolean insertRentRate(
            String address,
            String propertyType,
            String listedDate,
            int zipCode,
            double rent,
            int numBed,
            double numBath,
            int sqft,
            String city,
            String state) {
        try {
            conn.setAutoCommit(false);
            // ConsoleLogger.getInstance().printMessage("INSIDE INSERT");
            int stateId = insertState(state, 0, 1); // as false boolean to hasSalesListings and 1 as true
                                                    // hasRentListings since we are inserting rent listings
            int cityId = insertCity(city, stateId, 0, 1);
            int zipCodeId = insertZipCode(zipCode, cityId, 0, 1); // FIXME HERE
            // ConsoleLogger.getInstance().printMessage("CITY ID: " + cityId + " ZIPCODE ID:
            // " + zipCodeId);
            // Checking if listing exits in the db
            // FIX IT
            queryRentRate.setString(1, address);
            ResultSet results = queryRentRate.executeQuery();
            if (results.next()) {
                ConsoleLogger.getInstance().printMessage("Address: " + address + " already in market rents table");
                return false;
            }
            // Replacing placeholders to avoid SQL injection attacks
            insertIntoMarketRents.setString(1, address);
            insertIntoMarketRents.setString(2, propertyType);
            insertIntoMarketRents.setString(3, listedDate);
            insertIntoMarketRents.setInt(4, zipCodeId);
            insertIntoMarketRents.setDouble(5, rent);
            insertIntoMarketRents.setInt(6, numBed);
            insertIntoMarketRents.setDouble(7, numBath);
            insertIntoMarketRents.setInt(8, sqft);
            insertIntoMarketRents.setString(9, city);
            insertIntoMarketRents.setString(10, state);

            int affectedRows = insertIntoMarketRents.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                throw new SQLException("Listing insert failed!");
            }
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Insert listing exception: ", e);
            try {
                ConsoleLogger.getInstance().printMessage("Performing rollback");
                conn.rollback();
                return false;
            } catch (SQLException e2) {
                ConsoleLogger.getInstance().printErrorMessage("Couldn't rollback trasaction: ", e2);
                return false;
            }
        } finally {
            try {
                ConsoleLogger.getInstance().printMessage("Resetting default commit behaviour");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                ConsoleLogger.getInstance().printMessage("Couldn't reset auto-commit! " + e.getMessage());
            }
        }

    }

    public boolean insertFairRentRate(
            int zipCode,
            int studioRate,
            int oneBedRate,
            int twoBedRate,
            int threeBedRate,
            int fourBedRate) {
        try {
            conn.setAutoCommit(false);
            // Replacing placeholders to avoid SQL injection attacks
            insertIntoFairRents.setInt(1, zipCode);
            insertIntoFairRents.setInt(2, studioRate);
            insertIntoFairRents.setInt(3, oneBedRate);
            insertIntoFairRents.setInt(4, twoBedRate);
            insertIntoFairRents.setInt(5, threeBedRate);
            insertIntoFairRents.setInt(6, fourBedRate);

            int affectedRows = insertIntoFairRents.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                throw new SQLException("Fair rent insert failed!");
            }
        } catch (SQLException e) {
            ConsoleLogger.getInstance().printErrorMessage("Insert fair rent exception: ", e);
            try {
                ConsoleLogger.getInstance().printMessage("Performing rollback");
                conn.rollback();
                return false;
            } catch (SQLException e2) {
                ConsoleLogger.getInstance().printErrorMessage("Couldn't rollback trasaction: ", e2);
                return false;
            }
        } finally {
            try {
                ConsoleLogger.getInstance().printMessage("Resetting default commit behaviour");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                ConsoleLogger.getInstance().printMessage("Couldn't reset auto-commit! " + e.getMessage());
            }
            resetDBAConnection();
            // Datasource.getInstance().close();
        }

    }

}
