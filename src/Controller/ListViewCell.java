
/* s3479719
Oluwole Aibinu
 * Class Name : ListViewCell
*This class is used to pass the data that is received from the database RentalProperty object to the ListViewData Class
* which will then update the lIstView cell items.

 */

package Controller;

import Model.RentalProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ListViewCell extends ListCell<RentalProperty> implements Initializable
{ @FXML
private VBox vBox;
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
    @FXML
     Label Type;
    @FXML
     Label id;
    @FXML
    ImageView Image;
    @FXML
    Button manageProperty;

    // pass the data to the ListViewData class and set the ListView Items.
    @Override
    public void updateItem(RentalProperty resultSet, boolean empty)
    {
        super.updateItem(resultSet,empty);
        if(resultSet != null)
        {
            this.loadData();
            // update the data for each Object
            this.setInfo(resultSet);
            //set the Cell Item with a VBox in each cell
            setGraphic(this.getVBox());
        }
    }

    // load the ListViewCell Fxml file and set the controller so that the Fxml variables are initialised
    public void loadData() {
        // load the ListViewCell.fxml file to bind to the controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ListViewCellView.fxml"));
        fxmlLoader.setController(this);
        try {
            // set the vbox as the fxml container
            vBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // update all the fields in the ListCellItem
    public void setInfo(RentalProperty rentalProperty) {
        File file;
        try {
            // build the address string using the propety details
            String pAddress = rentalProperty.get_streetNumber() + "," + rentalProperty.get_streetName() + "," + rentalProperty.get_suburb();

            // set the street and address labels
            Type.setText(rentalProperty.get_propertyType());
            id.setText(pAddress);

            String fileName = rentalProperty.getImage();
            Image image;
            if(fileName.isEmpty()) {
                file = new File("src/Images/" + "noimage.jpg");
            }
            else{
                if(fileName.startsWith("A_") || fileName.startsWith("S_")){
                    file = new File("src/Images/" + "noimage.jpg");
                }else{
                    file = new File("src/Images/" + fileName);
                }
            }

            // set the image file name and bind it to the Image View
            image = new Image(file.toURI().toString());
            Image.setImage(image);

            manageProperty.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        manageProperty(actionEvent, rentalProperty);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //on button click call the single window of the property
    public void manageProperty(ActionEvent event, RentalProperty rentalProperty){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SinglePropertyWindow.fxml"));
            Parent root1 =  fxmlLoader.load();
            SinglePropertyWindowController swc = fxmlLoader.getController();
            swc.setFields(rentalProperty);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e2) {
            e2.printStackTrace();
        }
    }

    // return the Vbox of List Cell
    public VBox getVBox() {
        return vBox;
    }
}
