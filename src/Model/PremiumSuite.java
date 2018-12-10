//s3479719
//Oluwole Aibinu
package Model;

import Database.DatabaseConnect;
import Model.UserExceptions.CantReturnException;
import Model.UserExceptions.InvalidReturnDateException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PremiumSuite extends RentalProperty {  //subclass

    private RentalRecord[] wole = new RentalRecord[10];
    PreparedStatement preparedStatement, preparedStatement1, preparedStatement2, preparedStatement3;
    ResultSet resultSet, resultSet1, resultSet2;

    //declaring variables
    private int customerID;
    private DateTime estimatedReturnDate;
    private DateTime rentDate;
    private DateTime actualReturnDate;
    private double rentalFee;
    private double lateFee;
    private double premiumPricePerDay = 0.00;
    private DateTime lastMaintenance;
    private DateTime checkMaintenance;
    private double totalRentFees;
    private double totalLateFees;

    //constructor
    public PremiumSuite (String propertyID,String streetNumber,String streetName,String suburb,String propertyType,
                         String propertyStatus,DateTime lastMaintenance, String image, String description)
    {

        super(propertyID, streetNumber, streetName, suburb, 3, propertyType, propertyStatus, image, description);
        this.lastMaintenance = lastMaintenance;
    }
    public double propertyPricePerDay() {
        return premiumPricePerDay = 554;
    }

    public int getCustomerID() {
        return customerID;
    }


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    public DateTime getEstimatedReturnDate() {
        return estimatedReturnDate;
    }


    public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
        this.estimatedReturnDate = estimatedReturnDate;
    }


    public DateTime getRentDate() {
        return rentDate;
    }


    public void setRentDate(DateTime rentDate) {
        this.rentDate = rentDate;
    }


    public DateTime getActualReturnDate() {
        return actualReturnDate;
    }


    public void setActualReturnDate(DateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }


    public double getRentalFee() {
        return rentalFee;
    }


    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }


    public double getLateFee() {
        return lateFee;
    }


    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }


    public double getPremiumPricePerDay() {
        return premiumPricePerDay;
    }

    public DateTime getlastMaintenance() {
        return lastMaintenance;
    }
    public void setlastMaintenance(DateTime lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }
    public DateTime getcheckMaintenance() {
        return checkMaintenance;
    }
    public double getTotalRentFees() {
        return totalRentFees;
    }

    public double getTotalLateFees() {
        return totalLateFees;
    }

    public double totalLateFees () {
        //fine
        totalLateFees = ((DateTime.diffDays(estimatedReturnDate, rentDate)) * 662 );
        return totalLateFees;
    }
    public double totalRentFees(){

        if (DateTime.diffDays(actualReturnDate, rentDate) >= DateTime.diffDays(estimatedReturnDate, rentDate)) {  //fine for late checkout
            totalRentFees = totalLateFees() + (DateTime.diffDays(estimatedReturnDate, rentDate) * premiumPricePerDay);
            return totalRentFees;
        }
        else
        { //normal rent calculation
            totalRentFees = DateTime.diffDays(estimatedReturnDate, rentDate) * premiumPricePerDay;
            return totalRentFees;
        }
    }

    public DateTime calEstimatedReturnDate(DateTime startDate, int setClockForwardInDays) {
        DateTime d = new DateTime();

        return d;
    }

    //implementation

    //rent method
    @Override
    public void rent(String customerId, DateTime rentDate, int numOfRentDay) {
        try{
            DateTime estReturnDate = new DateTime(rentDate, numOfRentDay);
            String recordId = this.get_propertyID() + '_' + customerId + '_' + rentDate.getEightDigitDate();

            Connection connection = DatabaseConnect.Databaseconnection();

            String query = "INSERT INTO Record VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, recordId);
            preparedStatement.setString(2, customerId);
            preparedStatement.setString(3, rentDate.toString());
            preparedStatement.setString(4, estReturnDate.toString());
            preparedStatement.setString(5, "");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, this.get_propertyID());

            //update the status of property to rented
            if (preparedStatement.executeUpdate() >0 ){
                String updatestatus = "Update Properties " +
                        "set Status=" + '"' + "Rented" + '"' +
                        " where pID=" + '"' + this.get_propertyID() + '"';
                preparedStatement2 = connection.prepareStatement(updatestatus);

                preparedStatement2.executeUpdate();

                //show alert messages
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rent");
                alert.setContentText("Rent Successful");
                alert.showAndWait();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    //checks availability
    @Override
    public boolean availability() {
        if (this.get_propertyStatus() == "rented" || this.get_propertyStatus() == "maintenance"){
            System.out.println("not available");
            return false;
        }
        else {
            return true;}

    }
    @Override
    public String toString() {

        return (get_propertyID() + ":" + get_streetNumber()+ ":"+ get_streetName() +":"
                + get_suburb() + ":"+ get_propertyType() +":"+ get_noOfBedrooms() +":" + get_propertyStatus() + this.getImage() + this.getDescription() + "\n" );

    }


    public RentalRecord[] getRecords() {
        return wole;

    }
    public void setRecords (RentalRecord[] wole){
        this.wole = wole;
    }

    @Override
    public void performMaintenance(){
        try {
            Connection connection = DatabaseConnect.Databaseconnection();

            String query = "Update Properties " +
                    "set Status=" + '"' + "Maintenance" + '"' +
                    " where pID=" + '"' + this.get_propertyID() + '"';

            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Perform maitenance was succesful");

            alert.showAndWait();
        }
        catch(SQLException e){

        }
    }

    @Override
    public void completeMaintenance(DateTime lastMaintenDate){
        try {
            Connection connection = DatabaseConnect.Databaseconnection();

            String query = "Update Properties " +
                    "set Status=" + '"' + "Available" + '"' +
                    ", lMDate="+ '"' + lastMaintenDate +'"' +
                    " where pID=" + '"' + this.get_propertyID() + '"';

            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Complete maitenance was succesful");

            alert.showAndWait();
        }
        catch(SQLException e){
        }
    }

    @Override
    public void changeStatus(String status) {
        // TODO Auto-generated method stub
        switch (status) {
            case ("maintenance"):
                this.set_propertyStatus(status);
                break;
            case ("free and vacant"):
                this.set_propertyStatus(status);
                break;
            case ("rented"):
                this.set_propertyStatus(status);
                break;
            default:
                System.out.println("Error apartment can be under maintenenace, rented or vacacnt statuses");
        }
    }
    @Override
    public String getDetails() {

        String details;
        details = "Property ID:     " + this.get_propertyID() + "\n";
        details += "Address:         " + super.get_streetNumber() +" "+ super.get_streetName() +" "+ super.get_suburb() + "\n";
        details += "Type" +"             "+ this.get_propertyType() + "\n";
        details += "Bedroom:         " + this.get_noOfBedrooms() + "\n";
        if(get_propertyStatus() == "rented"){
            details += "Status:         Rented\n";
            details += "Rental REORD:\n";
            details += "Record ID:      " + this.wole[0].getRecordID() + "\n";
            details += "Rent Date:      " + this.wole[0].getRentDate() + "\n";
            details += "Late Fee:       " + this.wole[0].getEstimatedReturnDate() + "\n";
            details += "*******************************";
        }
        else {
            details += "Status:          Available for rent\n";
            details += "RENTAL RECORD:   empty\n";
            details += "*******************************"; }
        return details;
    }



    // method to return property
    @Override
    public void returnProperty(DateTime returnDate) {
        try{
            Connection connection = DatabaseConnect.Databaseconnection();

            List<String> records = new ArrayList<String>();

            String query = "SELECT * FROM Record " +
                    "where pID = " +'"' + this.get_propertyID() +'"' +
                    " and actualReturnDate=''";

            preparedStatement1 = connection.prepareStatement(query);
            resultSet1 = preparedStatement1.executeQuery();

            while (resultSet1.next()) {
                records.add(resultSet1.getString("Record"));
                records.add(resultSet1.getString("rentDate"));
                records.add(resultSet1.getString("estReturnDate"));
            }

            String[] ppt = records.get(1).split("/");
            DateTime rentDate = new DateTime(Integer.valueOf(ppt[0]),Integer.valueOf(ppt[1]),Integer.valueOf(ppt[2]));

            if (DateTime.diffDays(returnDate, rentDate) < 0 ||DateTime.diffDays(returnDate, rentDate) == 0) {
                throw new InvalidReturnDateException();
            }
            else {

                String[] ppt1 = records.get(2).split("/");
                DateTime estreurndate = new DateTime(Integer.valueOf(ppt1[0]),Integer.valueOf(ppt1[1]),Integer.valueOf(ppt1[2]));

                int expectedDaysRented = DateTime.diffDays(estreurndate,rentDate);
                int actualDaysRented = DateTime.diffDays(returnDate, rentDate);

                int daysGap = expectedDaysRented - actualDaysRented;

                if (daysGap > 0) {
                    this.rentalFee = 554.00 * actualDaysRented;
                }else{
                    this.rentalFee = 554.00 * actualDaysRented;
                    this.lateFee = 662.00 * (actualDaysRented-expectedDaysRented) * 1.15;;
                }

                //update rental records
                String updateQuery = "Update Record " +
                        "set rentalFee=" + this.rentalFee + "," +
                        "lateFee=" + this.lateFee + "," +
                        "actualReturnDate=" +'"' + returnDate +'"' +
                        " where Record=" +'"' + records.get(0) +'"' ;

                preparedStatement2 = connection.prepareStatement(updateQuery);

                //update property status
                if (preparedStatement2.executeUpdate()> 0){
                    String updatestatus = "Update Properties " +
                            "set Status=" + '"' + "Available" + '"' +
                            " where pID=" + '"' + this.get_propertyID() + '"';
                    preparedStatement3 = connection.prepareStatement(updatestatus);

                    preparedStatement3.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rent");
                    alert.setContentText("Return Successful");
                    alert.showAndWait();
                }
            }
        }catch(InvalidReturnDateException ird){

        }catch (SQLException ex){
            ex.printStackTrace();
        }
         }
}





