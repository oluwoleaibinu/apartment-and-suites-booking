//s3479719
//Oluwole Aibinu

package Controller;

import Database.DatabaseConnect;
import Model.*;
import Model.UserExceptions.CantMaintainException;
import Model.UserExceptions.CantRentException;
import Model.UserExceptions.CantReturnException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class SinglePropertyWindowController {
    RentalProperty rentalProperty;

    @FXML
    Label pidValue;
    @FXML
    Label pTtypeValue;
    @FXML
    TextArea descrValue;
    @FXML
    ImageView imgValue;
    @FXML
    ListView detailsList;

    //set fields for rental property object
    public void setFields(RentalProperty rentalProperty){
        this.rentalProperty = rentalProperty;
        setSinglePropertyFields();
    }

    // add export code
    public void export(ActionEvent e) throws SQLException {
        Export export = new Export();
        export.exportFile();
    }

    // add importfile code
    public void importFile() throws SQLException, IOException {
        Import importfile = new Import();
        importfile.importData();
    }

    //set all the label fields
    private void setSinglePropertyFields(){
        //set fields of the property
        pidValue.setText(rentalProperty.get_propertyID());
        pTtypeValue.setText(rentalProperty.get_propertyType());
        descrValue.setText(rentalProperty.getDescription());
        String fileName = rentalProperty.getImage();

        Image image;
        File file;

        //set image value
        if(fileName.isEmpty()) {
            file = new File("src/Images/" + "noimage.jpg");
        }
        else{
            file = new File("src/Images/" + fileName);
        }

        // set the image file name and bind it to the Image View
        image = new Image(file.toURI().toString());
        imgValue.setImage(image);

        showRecords();
    }

    // loads the rent property fxml and opens it in a new window
    public void openRentWindow(Event e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/RentWindow.fxml"));
            Parent root1 =  fxmlLoader.load();
            RentController rc = fxmlLoader.getController();
            rc.setFields(rentalProperty);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e2) {
            e2.printStackTrace();
        }
    }

    //check porperty status and then open window or else show error
    public void checkRentWindow(ActionEvent e){
        try{
        if(rentalProperty.get_propertyStatus().equalsIgnoreCase("Available")){
            openRentWindow(e);

        }else{
            throw new CantRentException();
        }}
        catch(CantRentException ex){

        }
    }

    public void performMaintenance(){
        MaintenanceOperationsController rc = new MaintenanceOperationsController();
        rc.setFields(rentalProperty);
        rc.performMaintenance();
    }


    // loads the rent property fxml and opens it in a new window
    public void openReturnWindow(Event e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ReturnWindow.fxml"));
            Parent root1 =  fxmlLoader.load();
            ReturnController rc = fxmlLoader.getController();
            rc.setFields(rentalProperty);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e2) {
            e2.printStackTrace();
        }
    }

    // loads the rent property fxml and opens it in a new window
    public void completeMaintenance(Event e) {
        try {
            if (rentalProperty.get_propertyStatus().equalsIgnoreCase("Maintenance")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/CompleteMaintenance.fxml"));
            Parent root1 =  fxmlLoader.load();
            MaintenanceOperationsController rc = fxmlLoader.getController();
            rc.setFields(rentalProperty);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            }else throw new CantMaintainException();
        } catch(CantMaintainException c) {
        } catch(Exception e2) {
            e2.printStackTrace();
        }
    }

    //check porperty status and then open window or else show error
    public void checkReturnWindow(ActionEvent e){
        try{
            if(rentalProperty.get_propertyStatus().equalsIgnoreCase("Rented")){
                openReturnWindow(e);
            }else{
                throw new CantReturnException();
            }}
        catch(CantReturnException ex){

        }
    }

    // show rental recoreds for the particular property
    private void showRecords() {
        try{
        final ObservableList<RentalRecord> rec = FXCollections.observableArrayList();
        final ObservableList<String> recList = FXCollections.observableArrayList();

            Connection connection = DatabaseConnect.Databaseconnection();
        PreparedStatement preparedStatement;
        String query = "Select * FROM Record where pID ="+ "'" + rentalProperty.get_propertyID() + "'";

        preparedStatement= connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            String[] ppt = resultSet.getString("rentDate").split("/");
            DateTime rentDate = new DateTime(Integer.valueOf(ppt[0]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[2]));
            String actualReturnString = resultSet.getString("actualReturnDate");

            String[] ppt2 = resultSet.getString("estReturnDate").split("/");
            DateTime estima = new DateTime(Integer.valueOf(ppt2[0]),Integer.valueOf(ppt2[1]),Integer.valueOf(ppt2[2]));

            if(actualReturnString.equals("")){
                rec.addAll(new RentalRecord(resultSet.getString("customer"),
                    rentalProperty.get_propertyID(),
                    rentDate,estima));
            }else{
                String[] ppt1 = resultSet.getString("actualReturnDate").split("/");
                DateTime actualReturn = new DateTime(Integer.valueOf(ppt1[0]),Integer.valueOf(ppt1[1]),Integer.valueOf(ppt1[2]));

               // String[] ppt2 = resultSet.getString("estReturnDate").split("/");
                //DateTime estima = new DateTime(Integer.valueOf(ppt2[0]),Integer.valueOf(ppt2[1]),Integer.valueOf(ppt2[2]));

                rec.addAll(new RentalRecord(resultSet.getString("customer"),
                        rentalProperty.get_propertyID(),
                        rentDate,
                        estima,
                        actualReturn,
                        Double.parseDouble(resultSet.getString("rentalFee")),
                    Double.parseDouble(resultSet.getString("lateFee"))
                ));
            }
        }
        for (int i = 0; i < rec.size(); i++) {
            recList.add(rec.get(i).getDetails());
        }

        Collections.reverse(recList);

        detailsList.setItems(recList);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //exit application
    public void close(){
        Platform.exit();
    }

    // go back to main window
    public void goBack(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/FlexiRentMainWindow.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();

            Stage appStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(new Scene(root1));
        } catch(Exception e) {
        }
    }

}
