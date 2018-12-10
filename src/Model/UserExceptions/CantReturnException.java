package Model.UserExceptions;

import javafx.scene.control.Alert;

public class CantReturnException extends Exception{

    public CantReturnException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Return Error");
        alert.setContentText("Cant return property as its not available");

        alert.showAndWait();
    }
}
