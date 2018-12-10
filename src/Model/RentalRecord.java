//s3479719
//Oluwole Aibinu


package Model;

public class RentalRecord {

    private String recordID;
    private DateTime estimatedReturnDate;
    private DateTime rentDate;
    private DateTime actualReturnDate;
    private double rentalFee;
    private double lateFee;
    private String propertyStatus;
    private String propertyId;


    public RentalRecord(String propertyID , String customerID, DateTime rentDate, DateTime estimatedReturnDate ) {
        recordID = propertyID + "_" + customerID + "_" + rentDate.getFormattedDate();
        this.estimatedReturnDate = estimatedReturnDate;
        this.setRentDate(rentDate);
    }
    //constructor overloading
    public RentalRecord(String customerId, String propertyId, DateTime rentDate, DateTime estimatedReturnDate,DateTime actualRentalDate, double rentalFee, double lateFee) {
        recordID = propertyId + '_' + customerId + '_' + rentDate.getEightDigitDate();
        this.rentDate = rentDate;
        this.estimatedReturnDate = estimatedReturnDate;
        this.propertyId = propertyId;
        this.lateFee = lateFee;
        this.rentalFee = rentalFee;
        this.actualReturnDate = actualRentalDate;
    }

    public String get_propertyStatus() {
        return propertyStatus;
    }



    public void set_propertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getRecordID() {
        return recordID;
    }


    public void setRecordID(String recordID) {
        this.recordID = recordID;
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

    //implementation
    public String toString() {

        if ( get_propertyStatus() == "rented") {
            return (recordID + ":" + rentDate + ":"+ estimatedReturnDate +":" +
                    "none:none:none");
        }
        else
        {
            return (recordID + ":" + rentDate + ":"+ estimatedReturnDate +":" +
                    actualReturnDate + ":"+rentalFee+ ":"+ lateFee);
        }
    }
    public String getDetails(){
        String details;
        details = "Record ID:                  " + recordID + "\n";
        details += "Rent Date:                  " + rentDate + "\n";
        details += "Estimated Return Date:   " + estimatedReturnDate + "\n";
        if(get_propertyStatus() != "rented"){
            details += "Actual Return Date:       " + actualReturnDate + "\n";
            details += "Rental Fee:                 " + String.format("%1.2f", rentalFee) + "\n";
            details += "Late Fee:                   " +String.format("%1.2f", lateFee)  + "\n";
        }
        return details;
    }

}
//public String getDetails()}


