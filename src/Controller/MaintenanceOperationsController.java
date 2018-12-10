//s3479719
//Oluwole Aibinu
package Controller;

import Model.DateTime;
import Model.RentalProperty;
import Model.UserExceptions.CantMaintainException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MaintenanceOperationsController implements Initializable {
    RentalProperty rentalProperty;

    @FXML
    DatePicker lmdpicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //set the date fields and the renta property object
    public void setFields(RentalProperty rentalProperty){
        this.rentalProperty = rentalProperty;
        if(lmdpicker!= null){
        lmdpicker.setValue(LocalDate.now());
        }
    }

    //perform maintennace of the partciular porperty
    public void performMaintenance(){
        try {
        if (rentalProperty.get_propertyStatus().equalsIgnoreCase("Available")) {
            rentalProperty.performMaintenance();

        }else{
            throw new CantMaintainException();
        }}
        catch(CantMaintainException cmx){
        }
    }

    //call complete maintenance of the particualr poreprty
    public void completeMaintain(javafx.event.ActionEvent e) throws Exception{
        try {

                String[] ppt = lmdpicker.getValue().toString().split("-");
                DateTime lmDate = new DateTime(Integer.valueOf(ppt[2]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[0]));

                rentalProperty.completeMaintenance(lmDate);
                close(e);

            }
        catch(CantMaintainException cmx){
        }
    }

    //close wndow after performing operations
    private void close(ActionEvent event) throws Exception{
        final Node source = (Node)event.getSource();
        final Stage parent1 = (Stage)source.getScene().getWindow();

        parent1.close();

    }
}
