package Model.UserExceptions;

import javafx.scene.control.Alert;

public class CantMaintainException extends Exception {

    public CantMaintainException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Rent Error");
        alert.setContentText("Cant maintain property curently");

        alert.showAndWait();
    }
}
