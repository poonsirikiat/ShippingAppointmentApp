package controller;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductList implements Initializable {

    public TextField modifyPartID;
    public TextField modifyPartName;
    public TextField modifyPartInventory;
    public TextField modifyPartPC;
    public TextField modifyPartMax;
    public TextField modifyPartMin;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;

    Products productToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * save and update the select modifying part to the inventory, and return to main controller.
     * @param actionEvent save the selected modify part.
     * @throws IOException FXMLLoader
     */
    public void modifyPartSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            int productID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            double price = Double.parseDouble(modifyPartPC.getText());
            int stock = Integer.parseInt(modifyPartInventory.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            if (min <= 0 || min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "min value cannot be less than 0, and not greater than max");
                alert.showAndWait();
            }
            else if(stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.ERROR, "stock value need to be between min and max");
                alert.showAndWait();
            }
            else {
                String sql = "UPDATE products SET Name = ?, Price = ?, Stock = ?, Min = ?, " +
                        "Max = ? WHERE Product_ID = ?";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ps.setString(1, name);
                ps.setDouble(2, price);
                ps.setInt(3, stock);
                ps.setInt(4, min);
                ps.setInt(5, max);
                ps.setInt(6, productID);

                ps.executeUpdate();


            }
            Parent parent = FXMLLoader.load(getClass().getResource("../view/product.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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
     * cancel to modify a new part, and return to main controller.
     * @param actionEvent cancel the action
     * @throws IOException FXMLLoader
     */
    public void modifyPartCancelAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/product.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * get the selected part data from main controller to the modifyPart controller and set each text-field to match the current data.
     * @param products the selected part data to modify
     */
    public void modifyFromMain(Products products) {

        this.productToUpdate = products;

        modifyPartID.setText(Integer.toString(productToUpdate.getId()));
        modifyPartName.setText(productToUpdate.getName());
        modifyPartInventory.setText(Integer.toString(productToUpdate.getStock()));
        modifyPartPC.setText(Double.toString(productToUpdate.getPrice()));
        modifyPartMax.setText(Integer.toString(productToUpdate.getMax()));
        modifyPartMin.setText(Integer.toString(productToUpdate.getMin()));
    }
}
