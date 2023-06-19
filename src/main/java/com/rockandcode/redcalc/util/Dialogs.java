

package com.rockandcode.redcalc.util;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class Dialogs {

    private static final String STYLE = "-fx-font-family: helvetica;";

    /*
     * The dialog object is created and initialized.
     * The title and header text for the dialog are set.
     * The OK and Cancel buttons are added to the dialog.
     */
    private Dialog dialog;
    
    private static final Dialogs instance = new Dialogs();
    
    public static Dialogs getInstance(){return instance;}
    
    public Dialog getInsertListingDialog() {
        /* To get the scene from the primary windows and set it as the owner 
        of the dialog to be created
        */
        dialog = new Dialog<>();

        dialog.setTitle("Insert New Listing");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to add a listing");

        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        return dialog;
    }
    
    public Dialog getUpdateCityDialog(){
        dialog = new Dialog<>();

        dialog.setTitle("Update City Name");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to update the city name");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }

    public Dialog getAvgListPriceByBedsBathsDialog(){
        dialog = new Dialog<>();

        dialog.setTitle("Get Average List Price");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to get the average list price");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getAvgRentByBedsBathsDialog(){
        dialog = new Dialog<>();

        dialog.setTitle("Get Average Rent");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to get the average rent");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getListingsByUnderwrittenValDialog(){
        dialog = new Dialog<>();

        dialog.setTitle("Get Listings By Underwritten Value");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to get listings");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getFairRentRateByZipcodeDialog() {
        dialog = new Dialog<>();

        dialog.setTitle("Get Fair Rent Rate");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to get fair rent");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
    public Dialog getDownloadRentListingDialog() {
        dialog = new Dialog<>();

        dialog.setTitle("Download Rent Listing");
        dialog.getDialogPane().setStyle(STYLE);
        dialog.setHeaderText("Use this dialog to download rent listings");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
    
}
