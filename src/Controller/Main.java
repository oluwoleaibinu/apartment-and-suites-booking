//s3479719
//Oluwole Aibinu
package Controller;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.EventListener;

public class Main extends Application {
    @Override
    //load the fxml and set the scene of the main stage
    public void start(Stage stage) throws Exception{
        //set file path
        Parent root = FXMLLoader.load(getClass().getResource("/View/StartupWindow.fxml"));
        // set title
        stage.setTitle("Flexi Rent Properties");

        stage.setScene(new Scene(root));
        // display the stage
        stage.show();
    }

    // call main function to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    public void openMainWindow(Event event) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/FlexiRentMainWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage appStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(new Scene(root1));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
