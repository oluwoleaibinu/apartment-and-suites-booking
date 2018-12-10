package Model;

import Database.DatabaseConnect;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Import {
    PreparedStatement preparedStatement1, preparedStatement2;
    Connection connection = DatabaseConnect.Databaseconnection();
    int count = 0;

    public void importData( ) throws IOException, SQLException {
        FileChooser chooser = new FileChooser();
        File selectedFile = null;

        while (selectedFile == null) {
            selectedFile = chooser.showOpenDialog(null);
        }

        File file = new File(selectedFile.getAbsolutePath());
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(file));
            String line = null;
            String[] Properties = null;

            while ((line = bReader.readLine()) != null) {
                Properties = line.split(":");
                DateTime dt = new DateTime();

                if(Properties.length == 10 ){
                    boolean Apartment = Properties[0].startsWith("A");

                    String query = "INSERT INTO Properties VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement1 = connection.prepareStatement(query);

                    preparedStatement1.setString(1, Properties[0]);
                    preparedStatement1.setString(2, Properties[1]);
                    preparedStatement1.setString(3, Properties[2]);
                    preparedStatement1.setString(4, Properties[3]);
                    preparedStatement1.setString(5, Properties[4]);
                    preparedStatement1.setString(6, Properties[5]);
                    preparedStatement1.setString(7, Properties[6]);
                    preparedStatement1.setString(8, Properties[7]);
                    preparedStatement1.setString(9, Properties[9]);
                    preparedStatement1.setString(10, Properties[8]);

                    count = preparedStatement1.executeUpdate();

                }else if(Properties.length == 9) {
                    String query = "INSERT INTO Properties VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement1 = connection.prepareStatement(query);

                    preparedStatement1.setString(1, Properties[0]);
                    preparedStatement1.setString(2, Properties[1]);
                    preparedStatement1.setString(3, Properties[2]);
                    preparedStatement1.setString(4, Properties[3]);
                    preparedStatement1.setString(5, Properties[4]);
                    preparedStatement1.setString(6, Properties[5]);
                    preparedStatement1.setString(7, Properties[6]);
                    preparedStatement1.setString(8, dt.getFormattedDate());
                    preparedStatement1.setString(9, Properties[8]);
                    preparedStatement1.setString(10, Properties[7] );

                    count = preparedStatement1.executeUpdate();
                }else{
                    String query = "INSERT INTO Record VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement2 = connection.prepareStatement(query);

                    String record[] = Properties[0].split("_");

                    preparedStatement2.setString(1, Properties[0]);
                    preparedStatement2.setString(2, record[0] + record[1]);
                    preparedStatement2.setString(3, Properties[1]);
                    preparedStatement2.setString(4, Properties[2]);
                    preparedStatement2.setString(5, Properties[3]);
                    preparedStatement2.setString(6, Properties[4]);
                    preparedStatement2.setString(7, Properties[5]);
                    preparedStatement2.setString(8,record[2] + record[3]);

                    preparedStatement2.executeUpdate();
                }
            }
            if(count > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import");
                alert.setContentText("IMPORTED SUCCESSFULLY");

                alert.showAndWait();
            }

        } finally {
            try {
                bReader.close();
            } catch (IOException x) {
            }
        }
    }
}
