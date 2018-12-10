//s3479719
//Oluwole Aibinu
package Controller;

import Model.DateTime;
import Model.RentalProperty;
import Model.UserExceptions.CantRentException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RentController implements Initializable {
    RentalProperty rentalProperty;

    @FXML
    TextField customeridfield;
    @FXML
    TextField daysfield;
    @FXML
    DatePicker datefield;
    @FXML
    Button rentbutton;
    @FXML
    Label dayslabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //set the rental property object
    public void setFields(RentalProperty rentalProperty){
        this.rentalProperty = rentalProperty;

        // force the field to be numeric only
        daysfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    dayslabel.setVisible(true);
                    daysfield.setText(newValue.replaceAll("[^\\d]", ""));
                }else{
            }}
        });
    }

    //call the rent method of the respective rental property
    public void rent(ActionEvent e)  {
        if(checkFields())
        {
        try {
            if (rentalProperty.get_propertyStatus().equalsIgnoreCase("Available")) {
                String[] ppt = datefield.getValue().toString().split("-");
                DateTime rentDate = new DateTime(Integer.valueOf(ppt[2]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[0]));

                rentalProperty.rent(customeridfield.getText(), rentDate,Integer.parseInt(daysfield.getText()));
                close(e);
            } else {
                throw new CantRentException();
            }
        }catch(CantRentException c){

        }catch (Exception i){

        }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Import");
            alert.setContentText("Pleas enter all fields");

            alert.showAndWait();
        }
    }

    //check if any fieklds are empty, and show alert message
    private boolean checkFields(){
        boolean check = true;

        if(customeridfield.getText().length() ==0 ||
                daysfield.getText().length() ==0 ||
                datefield.getValue().toString().length() == 0 ){

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
