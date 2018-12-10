//s3479719
//Oluwole Aibinu
package Controller;

import Database.DatabaseConnect;
import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    Connection connection;
    @FXML
    ListView propertiesListView;

    @FXML
    ComboBox filterValueBox;
    @FXML
    ComboBox filterColumnBox;

    final ObservableList<RentalProperty> Properties = FXCollections.observableArrayList();
    final ObservableList<String> columns = FXCollections.observableArrayList();
    final ObservableList<String> fields = FXCollections.observableArrayList();

    PreparedStatement preparedStatement1, preparedStatement2;
    ResultSet resultSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             connection = DatabaseConnect.Databaseconnection();
            setFilteringFields();
            displayProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // loads the add property fxml and opens it in a new window
    public void addProperties(Event e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddPropertiesWindow.fxml"));
            Parent root1 =  fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Add Rental Property");
            stage.show();
        } catch(Exception e2) {
            e2.printStackTrace();
        }
    }

    public void importFile() throws SQLException, IOException {
        Import importfile = new Import();
        importfile.importData();
    }

    public void displayProperties(){

        try{
            String query = "select * from Properties";
            // connect to the database
            preparedStatement1 = connection.prepareStatement(query);
            resultSet = preparedStatement1.executeQuery();

            while(resultSet.next()){
                if(resultSet.getString("Type").startsWith("A")){
                    Properties.addAll(new Apartment(resultSet.getString("pID"),
                            resultSet.getString("sNumber"), resultSet.getString("sName"),
                            resultSet.getString("suburbs"),resultSet.getString("Type"),
                            resultSet.getString("Status"),Integer.parseInt(resultSet.getString("Bedroom")),
                            resultSet.getString("Image"),resultSet.getString("Descr")));
                }else{
                    String[] ppt = resultSet.getString("lMDate").split("/");
                    DateTime propertyMDate = new DateTime(Integer.valueOf(ppt[0]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[2]));

                    Properties.addAll(new PremiumSuite(resultSet.getString("pID"),
                            resultSet.getString("sNumber"), resultSet.getString("sName"),
                            resultSet.getString("suburbs"),resultSet.getString("Type"),
                            resultSet.getString("Status"),propertyMDate,
                            resultSet.getString("Image"),resultSet.getString("Descr")));
                }
            }

        }catch(SQLException s){

        }
        
        Collections.reverse(Properties);

        propertiesListView.setItems(Properties);

        //set each item cell of the list view , in order to display the multiple components in the list view.
        propertiesListView.setCellFactory(new Callback<ListView<RentalProperty>, ListCell<RentalProperty>>() {
            @Override
            public ListCell<RentalProperty> call(ListView<RentalProperty> listView) {
                return new ListViewCell();
            }
        });
    }

    public void export(ActionEvent e) throws SQLException{
        Export export = new Export();
        export.exportFile();
    }

    public void setFilteringFields() {
        columns.addAll("Type", "Status", "Bedroom", "suburbs");
        filterColumnBox.setItems(columns);
        filterColumnBox.getSelectionModel().selectFirst();

        fields.addAll("Apartment", "PremiumSuite");
        filterValueBox.setItems(fields);
        filterValueBox.getSelectionModel().selectFirst();

        filterColumnBox.getSelectionModel().selectedItemProperty().addListener(e -> {
            filterValueBox.getSelectionModel().selectFirst();
                String s = filterColumnBox.getSelectionModel().getSelectedItem().toString();
                if (s.equalsIgnoreCase("Bedroom")) {
                    fields.clear();
                    fields.addAll("1", "2", "3");
                    filterValueBox.setItems(fields);
                    filterValueBox.getSelectionModel().selectFirst();
                } else if (s.equalsIgnoreCase("Status")) {
                    fields.clear();
                    fields.addAll("Available", "Maintenance", "Rented");
                    filterValueBox.setItems(fields);
                    filterValueBox.getSelectionModel().selectFirst();

                } else if("Type".equalsIgnoreCase(s)) {
                fields.clear();
                fields.addAll("Apartment", "Premium Suite");
                filterValueBox.setItems(fields);
                    filterValueBox.getSelectionModel().selectFirst();
                }else{
                    fields.clear();
                    fields.addAll("Melbourne");
                    filterValueBox.setItems(fields);
                    filterValueBox.getSelectionModel().selectFirst();
                }
});
    }

    public void filtering(ActionEvent event){
        Properties.clear();

        String columnValue = this.filterColumnBox.getValue().toString();
        String fieldValue = this.filterValueBox.getValue().toString();

        try{
            String query = "select * from Properties where " + columnValue + " = " +  '"' +fieldValue +  '"';
            // connect to the database
            preparedStatement1 = connection.prepareStatement(query);
            resultSet = preparedStatement1.executeQuery();

            while(resultSet.next()){
                if(resultSet.getString("Type").startsWith("A")){
                    Properties.addAll(new Apartment(resultSet.getString("pID"),
                            resultSet.getString("sNumber"), resultSet.getString("sName"),
                            resultSet.getString("suburbs"),resultSet.getString("Type"),
                            resultSet.getString("Status"),Integer.parseInt(resultSet.getString("Bedroom")),
                            resultSet.getString("Image"),resultSet.getString("Descr")));
                }else{
                    String[] ppt = resultSet.getString("lMDate").split("/");
                    DateTime propertyMDate = new DateTime(Integer.valueOf(ppt[0]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[2]));

                    Properties.addAll(new PremiumSuite(resultSet.getString("pID"),
                            resultSet.getString("sNumber"), resultSet.getString("sName"),
                            resultSet.getString("suburbs"),resultSet.getString("Type"),
                            resultSet.getString("Status"),propertyMDate,
                            resultSet.getString("Image"),resultSet.getString("Descr")));
                }
            }

        }catch(SQLException s){

        }

        propertiesListView.setItems(Properties);

        propertiesListView.setCellFactory(new Callback<ListView<RentalProperty>, ListCell<RentalProperty>>() {
            @Override
            public ListCell<RentalProperty> call(ListView<RentalProperty> listView) {
                return new ListViewCell();
            }
        });

    }

    public void close(){
        Platform.exit();
    }
}
