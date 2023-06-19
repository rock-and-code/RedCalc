package com.rockandcode.redcalc.util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * A utility class for creating and configuring tables.
 */
public class TableViewEditor {

    private static final TableViewEditor instance = new TableViewEditor();
    /**
     * Gets the singleton instance of this class.
     * @return The singleton instance of this class.
     */
    public static TableViewEditor getInstance() {
        return instance;
    }

    /**
     * Sets the columns for the given table to display the properties of a listing.
     * @param table The table to configure.
     */
    public void setColumnsForListingsTable(TableView table) {
            // Clear the existing columns.
            table.getColumns().clear();

            // Create a column for the address property.
            TableColumn column = new TableColumn("Address");
            column.setCellValueFactory(new PropertyValueFactory("address"));
            column.setPrefWidth(300);
            table.getColumns().add(column);

            // Create a column for the property type property.
            column = new TableColumn("Property Type");
            column.setCellValueFactory(new PropertyValueFactory("propertyType"));
            column.setPrefWidth(200);
            table.getColumns().add(column);

            // Create a column for the number of beds property.
            column = new TableColumn("Beds");
            column.setCellValueFactory(new PropertyValueFactory("numBeds"));
            table.getColumns().add(column);

            // Create a column for the number of baths property.
            column = new TableColumn("Baths");
            column.setCellValueFactory(new PropertyValueFactory("numBaths"));
            table.getColumns().add(column);

            // Create a column for the list price property.
            column = new TableColumn("List Price");
            column.setCellValueFactory(new PropertyValueFactory("listPrice"));
            table.getColumns().add(column);

            // Create a column for the URL property.
            column = new TableColumn("Url");
            column.setCellValueFactory(new PropertyValueFactory("url"));
            column.setPrefWidth(600);
            table.getColumns().add(column);
    }

    /**
     * Sets the columns for the given table to display the properties of a zip code.
     * @param table The table to configure.
     */
    public void setColumnsForZipcodesTable(TableView table) {
            // Clear the existing columns.
            table.getColumns().clear();

            // Create a column for the zip code property.
            TableColumn column = new TableColumn("Zipcode");
            column.setCellValueFactory(new PropertyValueFactory("zipcode"));
            column.setPrefWidth(300);

            // Create a column for the "has rent listings" property.
            table.getColumns().add(column);
            column = new TableColumn("Has Rent Listings");
            column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
            column.setPrefWidth(200);
            table.getColumns().add(column);
    }

    /**
     * Sets the columns for the given table to display the properties of a city.
     * @param table The table to configure. */
    public void setColumnsForCitiesTable(TableView table) {
            // Clear the existing columns.
            table.getColumns().clear();

            // Create a column for the city name property.
            TableColumn column = new TableColumn("Name");
            column.setCellValueFactory(new PropertyValueFactory("name"));
            column.setPrefWidth(300);
            table.getColumns().add(column);

            // Create a column for the "has rent listings" property.
            column = new TableColumn("Has Rent Listings");
            column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
            column.setPrefWidth(200);
            table.getColumns().add(column);

    }

    public void setColumnsForStatesTable(TableView table) {
        // Clear the existing columns.
        table.getColumns().clear();

        // Create a column for the State name property.
        TableColumn column = new TableColumn("Name");
        column.setCellValueFactory(new PropertyValueFactory("name"));
        column.setPrefWidth(300);
        table.getColumns().add(column);

        // Create a column for the "has rent listings" property.
        column = new TableColumn("Has Rent Listings");
        column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
        column.setPrefWidth(200);
        table.getColumns().add(column);

    }
}
