package com.rockandcode.redcalc.util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author riost02
 */
public class TableViewEditor {

    private static final TableViewEditor instance = new TableViewEditor();

    public static TableViewEditor getInstance() {
        return instance;
    }

    public void setColumnsForListingsTable(TableView table) {
            table.getColumns().clear();
            TableColumn column = new TableColumn("Address");
            column.setCellValueFactory(new PropertyValueFactory("address"));
            column.setPrefWidth(300);
            table.getColumns().add(column);
            column = new TableColumn("Property Type");
            column.setCellValueFactory(new PropertyValueFactory("propertyType"));
            column.setPrefWidth(200);
            table.getColumns().add(column);
            column = new TableColumn("Beds");
            column.setCellValueFactory(new PropertyValueFactory("numBeds"));
            table.getColumns().add(column);
            column = new TableColumn("Baths");
            column.setCellValueFactory(new PropertyValueFactory("numBaths"));
            table.getColumns().add(column);
            column = new TableColumn("List Price");
            column.setCellValueFactory(new PropertyValueFactory("listPrice"));
            table.getColumns().add(column);
            column = new TableColumn("Url");
            column.setCellValueFactory(new PropertyValueFactory("url"));
            column.setPrefWidth(600);
            table.getColumns().add(column);
    }

    public void setColumnsForZipcodesTable(TableView table) {
        //setOneColumnTable(table, "Zipcode", "zipcode");
            table.getColumns().clear();
            TableColumn column = new TableColumn("Zipcode");
            column.setCellValueFactory(new PropertyValueFactory("zipcode"));
            column.setPrefWidth(300);
            table.getColumns().add(column);
            column = new TableColumn("Has Rent Listings");
            column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
            column.setPrefWidth(200);
            table.getColumns().add(column);
    }

    public void setColumnsForCitiesTable(TableView table) {
        /*Checking if the tables has more than one column meaning the apps has 
        queried to list cities after listings (which has 6 columns). 
         */
            table.getColumns().clear();
            TableColumn column = new TableColumn("Name");
            column.setCellValueFactory(new PropertyValueFactory("name"));
            column.setPrefWidth(300);
            table.getColumns().add(column);
            column = new TableColumn("Has Rent Listings");
            column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
            column.setPrefWidth(200);
            table.getColumns().add(column);

    }

    public void setColumnsForStatesTable(TableView table) {
        table.getColumns().clear();
        TableColumn column = new TableColumn("Name");
        column.setCellValueFactory(new PropertyValueFactory("name"));
        column.setPrefWidth(300);
        table.getColumns().add(column);
        column = new TableColumn("Has Rent Listings");
        column.setCellValueFactory(new PropertyValueFactory("hasRentListings"));
        column.setPrefWidth(200);
        table.getColumns().add(column);

    }
}
