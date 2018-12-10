//s3479719
//Oluwole Aibinu
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

    //connect to the database
    public static Connection Databaseconnection()
    {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");

            //database path
            connection = DriverManager.getConnection("jdbc:sqlite:src/Database/FlexiRentproperties.sqlite");

        } catch (ClassNotFoundException e) {
        }catch(SQLException ex){}

        return connection;
    }
}
