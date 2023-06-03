module com.mycompany.realestatemarketanalyzerui {
    requires javafx.media;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    opens com.rockandcode.redcalc.ui to javafx.fxml;
    opens com.rockandcode.redcalc.controller to javafx.fxml;

            
    //To allows GUI access model classes (RealEstate, City, etc) from the database
    opens com.rockandcode.redcalc.model to javafx.base;
    exports com.rockandcode.redcalc.ui;
    exports com.rockandcode.redcalc.controller;
}
