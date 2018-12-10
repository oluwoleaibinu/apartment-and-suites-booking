//s3479719
//Oluwole Aibinu
package Controller;

import Model.DateTime;
import Model.RentalProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReturnController {
    @FXML
    DatePicker datefield;
    @FXML
    Button returnbutton;
    RentalProperty rentalProperty;


    public void setFields(RentalProperty rentalProperty){
        this.rentalProperty = rentalProperty;
        datefield.setValue(LocalDate.now());
    }

    public void returnProp(ActionEvent e) throws Exception {
        if(checkFields())
        {
            String[] ppt = datefield.getValue().toString().split("-");
            DateTime rentDate = new DateTime(Integer.valueOf(ppt[2]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[0]));

            rentalProperty.returnProperty(rentDate);
            close(e);
        }
    }

    private boolean checkFields(){
        boolean check = true;

        if(datefield.getValue().toString().length() ==0 ){
            return false;
        }

        return check;
    }

    //close window on operation completion
    private void close(ActionEvent event) throws Exception{
        final Node source = (Node)event.getSource();
        final Stage parent1 = (Stage)source.getScene().getWindow();

        parent1.close();

    }
}
