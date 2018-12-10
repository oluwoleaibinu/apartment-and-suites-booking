package Model.UserExceptions;

import javafx.scene.control.Alert;

public class InvalidReturnDateException extends Exception {

    public InvalidReturnDateException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Return Error");
        alert.setContentText("Cant return property as the return date is same as rent date.");

        alert.showAndWait();
    }
}
