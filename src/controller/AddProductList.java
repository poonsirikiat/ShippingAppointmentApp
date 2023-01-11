package controller;

import DAO.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.DBConnection;
import model.Inventory;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductList implements Initializable {
    public TextField AddPartID;
    public TextField addPartName;
    public TextField addPartInventory;
    public TextField addPartPC;
    public TextField addPartMax;
    public TextField addPartMin;
    public Button addPartSaveButton;
    public Button addPartCancelButton;

    private static int addPartID;

    /**
     * increase ID by one for every new part created.
     * @return the auto generated ID for new part
     */
    public static int incrementPartID(){
        return ++addPartID;
    }

    /**
     * initialize the AddPart controller, and set In-house to true.
     * @param url the location for the root object
     * @param resourceBundle the resource use
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Save and add new part to the inventory, and return to main controller.
     * @param actionEvent save new part action
     * @throws IOException FXMLLoader
     */
    public void addProductSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            String name = addPartName.getText();
            double price = Double.parseDouble(addPartPC.getText());
            int stock = Integer.parseInt(addPartInventory.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            if (min <= 0 || min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "min value cannot be less than 0, and not greater than max");
                alert.showAndWait();
            }
            else if(stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.ERROR, "stock value need to be between min and max");
                alert.showAndWait();
            }
            else {

                String sql = "INSERT INTO products VALUE(NULL,?,?,?,?,?)";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, name);
                ps.setDouble(2, price);
                ps.setInt(3, stock);
                ps.setInt(4, min);
                ps.setInt(5, max);

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();

                Parent parent = FXMLLoader.load(getClass().getResource("../view/product.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The value in one or more fields are invalid");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * cancel to adding a new part, and return to main controller.
     * @param actionEvent cancel the action
     * @throws IOException FXMLLoader
     */
    public void addProductCancelAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Cancel?");
        Optional<ButtonType> result =alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/Product.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


}
