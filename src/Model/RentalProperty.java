//s3479719
//Oluwole Aibinu
package Model;



public abstract class RentalProperty {


    private String propertyID;
    private String streetNumber;
    private String streetName;
    private String suburb;
    private int noOfBedrooms;
    private String propertyType;
    protected String propertyStatus;

    private String Image;
    private String description;

    public RentalProperty (String propertyID,String streetNumber,String streetName,String suburb,
                           int noOfBedrooms,String propertyType,String propertyStatus, String Image, String description) {
        //constructor

        this.propertyID = propertyID;
        this.streetNumber= streetNumber;
        this.streetName= streetName;
        this.suburb= suburb;
        this.noOfBedrooms = noOfBedrooms;
        this.propertyType = propertyType;
        this.propertyStatus = propertyStatus;
        this.Image = Image;
        this.description = description;
    }



    public String get_propertyID() {
        return propertyID;
    }



    public void set_propertyID(String propertyID) {
        this.propertyID = propertyID;
    }



    public String get_streetNumber() {
        return streetNumber;
    }



    public void set_streetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }



    public String get_streetName() {
        return streetName;
    }



    public void set_streetName(String streetName) {
        this.streetName = streetName;
    }



    public String get_suburb() {
        return suburb;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return description;
    }


    public void set_suburb(String suburb) {
        this.suburb = suburb;
    }



    public int get_noOfBedrooms() {
        return noOfBedrooms;
    }



    public void set_noOfBedrooms(int noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }



    public String get_propertyType() {
        return propertyType;
    }



    public void set_propertyType(String propertyType) {
        this.propertyType = propertyType;
    }



    public String get_propertyStatus() {
        return propertyStatus;
    }



    public void set_propertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }


    //abstract methods
    public abstract double propertyPricePerDay();
    public abstract double totalLateFees();
    public abstract void rent(String customerId, DateTime rentDate, int numOfRentDay);
    public abstract void changeStatus(String status);
    public abstract boolean availability();
    public abstract String getDetails();
    public abstract void returnProperty(DateTime returnDate);

    public abstract void performMaintenance();
    public abstract void completeMaintenance(DateTime maintenanceDate);

}
