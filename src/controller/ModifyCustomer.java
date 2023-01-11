package controller;

import DAO.DBCountry;
import DAO.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.DBConnection;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyCustomer implements Initializable {
    public TextField modifyCustomerID;
    public TextField modifyCustomerName;
    public TextField modifyCustomerAddress;
    public TextField modifyCustomerPostal;
    public TextField modifyCustomerPhone;
    public ComboBox<String> modifyCustomerCountry;
    public ComboBox<String> modifyCustomerDivision;
    public Button modifyCustomerSave;
    public Button modifyCustomerCancel;

    /**
     * Initialize from the Main controller, and populate combo box with country and division selection
     *
     * <p>Lambda expression to set division selection choice for when the country is selected</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            modifyCustomerCountry.setItems(DBCountry.getCountry());
            modifyCustomerCountry.setPromptText("Select Country...");

            //Lambda expression
            modifyCustomerCountry.valueProperty().addListener((obs, oldSelection, newSelection) ->{
                if(newSelection != null){
                    modifyCustomerDivision.getItems().clear();

                    try {
                        modifyCustomerDivision.setItems(DBCountry.getCountryDivision(modifyCustomerCountry.getValue()));
                        System.out.println(modifyCustomerCountry.getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the selected customer data from main controller to the modifyCustomer controller and set each text-fields and items to match the current data.
     * @param customerSelect customer selected
     * @throws SQLException FXMLLoader
     */
    public void modFromMain(Customer customerSelect) throws SQLException {
        try {
            modifyCustomerID.setText(Integer.toString(customerSelect.getCustomerID()));
            modifyCustomerName.setText(customerSelect.getCustomerName());
            modifyCustomerAddress.setText(customerSelect.getCustomerAddress());
            modifyCustomerPostal.setText(customerSelect.getCustomerPostal());
            modifyCustomerPhone.setText(customerSelect.getCustomerPhone());

            modifyCustomerCountry.setItems(DBCountry.getCountry());
            modifyCustomerCountry.getSelectionModel().select(customerSelect.getCustomerCountry());
            modifyCustomerDivision.setItems(DBCountry.getCountryDivision(customerSelect.getCustomerCountry()));
            modifyCustomerDivision.getSelectionModel().select(customerSelect.getDivisionName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method save the modified customer information
     * @param actionEvent save modify customer action
     * @throws IOException FXMLLoader
     */
    public void modifyCustomerSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            String customerName = modifyCustomerName.getText();
            String customerAddress = modifyCustomerAddress.getText();
            String customerPostal = modifyCustomerPostal.getText();
            String customerPhone = modifyCustomerPhone.getText();
            int customerDivID = DBCountry.getDivisionID(modifyCustomerDivision.getValue());
            int customerID = Integer.parseInt(modifyCustomerID.getText());
            String customerCountry = modifyCustomerCountry.getValue();
            String customerDivision = modifyCustomerDivision.getValue();

            if (customerName.isEmpty() || customerAddress.isEmpty() || customerPostal.isEmpty() || customerPhone.isEmpty() || customerCountry == null || customerDivision == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One of the fields are empty");
                alert.showAndWait();
            } else {

                String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                        "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ps.setString(1, customerName);
                ps.setString(2, customerAddress);
                ps.setString(3, customerPostal);
                ps.setString(4, customerPhone);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, DBUser.loginUser().getUserName());
                ps.setInt(7, customerDivID);
                ps.setInt(8, customerID);

                ps.executeUpdate();


                Parent parent = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method cancel any change to customer information and return to main page
     * @param actionEvent cancel and back to main page action
     */
    public void modifyCustomerCancelAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Cancel?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Parent parent = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



}
