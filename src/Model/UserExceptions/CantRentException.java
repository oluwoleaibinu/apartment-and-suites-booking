package Model.UserExceptions;

import javafx.scene.control.Alert;

public class CantRentException extends Exception {

    public CantRentException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Rent Error");
        alert.setContentText("Cant rent property as its not available");

        alert.showAndWait();
    }
}
