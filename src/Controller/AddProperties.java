//s3479719
//Oluwole Aibinu
package Controller;

import Database.DatabaseConnect;
import Model.DateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProperties implements Initializable {
    @FXML
    ComboBox propertytypebox;
    @FXML
    ComboBox bedrrombox;
    @FXML
    TextField streetnoarea;
    @FXML
    TextField suburbarea;
    @FXML
    TextField streetnamearea;
    @FXML
    TextArea descriptionarea;
    @FXML
    DatePicker datechooser;

    ObservableList<Integer> bedrooms = FXCollections.observableArrayList();
    ObservableList<String> propertyType = FXCollections.observableArrayList();
    DateTime dateTime = new DateTime();

    Connection connection;

    //initialixe the controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DatabaseConnect.Databaseconnection();
            setViewValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // set the bedroom dropdown values for the form.
    public void setViewValues(){
        propertyType.addAll("Apartment", "PremiumSuite");
        propertytypebox.setItems(propertyType);

        bedrooms.addAll(1, 2, 3);
        bedrrombox.setItems(bedrooms);

        setBedrooms();
    }

    // use listerner to get the various field values
    public void setBedrooms() {
        propertytypebox.getSelectionModel().selectedItemProperty().addListener((e -> {
            String propertyType = propertytypebox.getSelectionModel().getSelectedItem().toString();

            if (propertyType.equalsIgnoreCase("PremiumSuite")) {
                bedrooms.removeAll(1, 2);
                bedrrombox.setItems(bedrooms);
                datechooser.setDisable(false);
            }else{
                bedrooms.clear();
                bedrooms.addAll(1, 2, 3);
                bedrrombox.setItems(bedrooms);
                bedrrombox.getSelectionModel().selectFirst();
                datechooser.setDisable(true);
            }
        }));
    }

    //get all filed values  and then insert in the rental table
    public void addProperty(ActionEvent event) throws SQLException{
        PreparedStatement preparedStatement;
        if(checkFields()){
        try {
            String propertyID;

            if(propertytypebox.getSelectionModel().getSelectedItem().toString().startsWith("A")){
                propertyID = "A" + streetnoarea.getText().substring(0,2) + streetnamearea.getText().substring(0,2) +suburbarea.getText().substring(0,2) ;
            }else{
                propertyID = "S" + streetnoarea.getText().substring(0,2) + streetnamearea.getText().substring(0,2) +suburbarea.getText().substring(0,2) ;
            }

            String query = "INSERT INTO Properties VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, propertyID);
            preparedStatement.setString(2, streetnoarea.getText());
            preparedStatement.setString(3, streetnamearea.getText());
            preparedStatement.setString(4, suburbarea.getText());
            preparedStatement.setString(5, propertytypebox.getSelectionModel().getSelectedItem().toString());
            preparedStatement.setString(6, bedrrombox.getSelectionModel().getSelectedItem().toString());
            preparedStatement.setString(7, "Available");
            preparedStatement.setString(8, dateTime.getFormattedDate());
            preparedStatement.setString(9, descriptionarea.getText());
            preparedStatement.setString(10, "A1.jpg" );

            int update = preparedStatement.executeUpdate();

            if(update > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!!!");
                alert.setContentText("Property Added, please restart application to see added property.");
                alert.showAndWait();
                close(event);
            }

        } catch (SQLException SqlEx) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Sql error while adding proeprety");
            alert.showAndWait();
        }
        catch (Exception ex) {
        	
        }
        
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter all fields");

            alert.showAndWait();
        }
    }

    //check if any fields are empty
    private boolean checkFields(){
        if(streetnoarea.getText().length() ==0 ||
                streetnamearea.getText().length() ==0 ||
                bedrrombox.getSelectionModel().getSelectedItem().toString().length() == 0 ||
                suburbarea.getText().length() ==0 ||
                descriptionarea.getText().length() ==0 ||
                bedrrombox.getSelectionModel().getSelectedItem().toString().length() ==0){

            return false;
        }
        return true;
    }
    
    //close window on operation completion
    private void close(ActionEvent event) throws Exception{
        final Node source = (Node)event.getSource();
        final Stage parent1 = (Stage)source.getScene().getWindow();

        parent1.close();

    }
    
}
