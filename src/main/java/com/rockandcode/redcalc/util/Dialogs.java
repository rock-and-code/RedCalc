

package com.rockandcode.redcalc.util;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 *
 * @author riost02
 */
public class Dialogs {
    
    private static final String STYLE = "-fx-font-family: helvetica;";
    
    private Dialog dialog;
    
    private static final Dialogs instance = new Dialogs();
    
    public static Dialogs getInstance(){return instance;}
    
    public Dialog getInsertListingDialog() {
        /* To get the scene from the primary windows and set it as the owner 
        of the dialog to be created
        */
        dialog = new Dialog<>();
    
        /* to change font to helvetica, this affects button's, title, and header text fonts */
        //dialog.getDialogPane().setStyle("-fx-font-family: helvetica;");         //Check how to set the dialog stylesheet
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Insert New Listing");
        dialog.setHeaderText("Use this dialog to add a listing");

        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        return dialog;
    }
    
    public Dialog getUpdateCityDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Update City Name");
        dialog.setHeaderText("Use this dialog to update the city name");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getAvgListPriceByZipcodeBedsBathsDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Average List Price");
        dialog.setHeaderText("Use this dialog to get the average list price");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getAvgListPriceByCityBedsBathsDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Average List Price");
        dialog.setHeaderText("Use this dialog to get the average list price");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getAvgRentByZipcodeBedsBathsDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Average Rent");
        dialog.setHeaderText("Use this dialog to get the average rent");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getAvgRentByCityBedsBathsDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Average Rent");
        dialog.setHeaderText("Use this dialog to get the average rent");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getListingsByZipcodeandUnderwrittenValDialog(){
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Listings By Underwritten Value");
        dialog.setHeaderText("Use this dialog to get listings");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getFairRentRateByZipcodeDialog() {
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Get Fair Rent Rate");
        dialog.setHeaderText("Use this dialog to get fair rent");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getDownloadRentListingDialog() {
        dialog = new Dialog<>();
        /* to change font to helvetica, this affects buttons fonts */
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setTitle("Download Rent Listing");
        dialog.setHeaderText("Use this dialog to download rent listings");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
}
