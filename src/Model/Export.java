package Model;

import Database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Export {
    final ObservableList<RentalProperty> Properties = FXCollections.observableArrayList();
    PreparedStatement preparedStatement1, preparedStatement2;
    ResultSet resultSet, resultSet1;
    Connection connection = DatabaseConnect.Databaseconnection();

    public void exportFile() throws SQLException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose location To Save Report");
        File chosenFile = null;

        chosenFile = chooser.showDialog(null);

        File file = new File(chosenFile + "export_data.txt");
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

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


        ObservableList<RentalRecord> recordList = FXCollections.observableArrayList();

        for (int i = 0; i < Properties.size(); i++) {
            //recordList.clear();
            String pid = Properties.get(i).get_propertyID();

            try{
                String query1 = "select * from Record where pID="+'"' + pid +'"';
                // connect to the database
                preparedStatement2 = connection.prepareStatement(query);
                resultSet1 = preparedStatement2.executeQuery();

                String[] ppt = resultSet1.getString("rentDate").split("/");
                DateTime rentDate = new DateTime(Integer.valueOf(ppt[0]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[2]));

                String[] ppt1 = resultSet1.getString("rentDate").split("/");
                DateTime estDate = new DateTime(Integer.valueOf(ppt1[0]),Integer.valueOf(ppt1[1]),Integer.valueOf(ppt1[2]));

                recordList.add(new RentalRecord(resultSet1.getString("customer"),
                        pid,
                        rentDate,
                        estDate
                ));

            }catch(SQLException e){

            }

            outFile.print(Properties.get(i).toString());
            for (int j = 0; j <recordList.size(); j++) {
                    outFile.println(recordList.get(j).toString());
                }
            }
        outFile.println();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Exported SUCCESSFULLY");
        alert.show();

        outFile.close();
    }catch (SQLException EX){
            EX.printStackTrace();
        }
    }
}
