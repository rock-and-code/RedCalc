package com.rockandcode.redcalc.ui;

import java.io.File;
import java.io.IOException;

import com.rockandcode.redcalc.controller.MainScreenController;
import com.rockandcode.redcalc.database.Datasource;
import com.rockandcode.redcalc.util.OsUtils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String MAIN_SCREEN_FXML = "main_screen";
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(MAIN_SCREEN_FXML), 940, 580);
        //Creating the scroll pane
        stage.setTitle("Real Estate Listings Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        if (fxml.equalsIgnoreCase(MAIN_SCREEN_FXML)) {
            //to populate table from database query
            Parent root = fxmlLoader.load();
            MainScreenController controller = fxmlLoader.getController();
            controller.listStates();
            return root;
        }
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().close();
    }

    @Override
    public void init() throws Exception {
        super.init();
        String CONNECTION_STRING = getConnectionString(); //YOU CAN SPECIFY THE DESIRE PATH OF THE SQLITE DATABASE FILE HERE!

        if (!Datasource.getInstance().open(CONNECTION_STRING)) {
            System.out.println("Fatal Error: Couldn't connect to database");
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String getConnectionString() {
        String CONNECTION_STRING = null;
        String userName = System.getProperty("user.name");
        String dirName = "RedCalcDatabase";
        String fileName = "database.db";
        if (null != OsUtils.getInstance().getOS()) {
            switch (OsUtils.getInstance().getOS()) {
                case MAC: {
                    System.out.println("Operating system is MacOs");
                    //Creates a new folder in the user's Desktop to store the database file
                    new File("/Users/" + userName + "/Desktop/" + dirName).mkdir();
                    //Creates the database file inside the new folder
                    try {
                        new File("/Users/" + userName + "/Desktop/" + dirName + "/" + fileName).createNewFile();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                    
                    CONNECTION_STRING = "/Users/" + userName + "/Desktop/" + dirName + "/" + fileName;

                    return CONNECTION_STRING;

                }
                case WINDOWS: {
                    System.out.println("Operating system is Windows");
                    //Creates a new folder in the user's Desktop to store the database file
                    new File("C:\\Users\\" + userName + "\\Desktop\\" + dirName).mkdir();
                    //Creates the database file inside the new folder
                    try {
                        new File("C:\\Users\\" + userName + "\\Desktop\\" + dirName + "\\" + fileName).createNewFile();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }

                    CONNECTION_STRING = "C:\\Users\\" + userName + "\\Desktop\\" + dirName + "\\" + fileName;

                    return CONNECTION_STRING;
                }
                case LINUX: {
                    System.out.println("Operating system is Linux");
                    //Creates a new folder in the user's Desktop to store the database file
                    new File("/Users/" + userName + "/Desktop/" + dirName).mkdir();
                    //Creates the database file inside the new folder
                    try {
                        new File("/Users/" + userName + "/Desktop/" + dirName + "/" + fileName).createNewFile();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }

                    return CONNECTION_STRING;

                }
                default:
                    break;
            }
        }
        return CONNECTION_STRING;
    }

}
