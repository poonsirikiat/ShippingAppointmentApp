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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    public TextField addCustomerID;
    public TextField addCustomerName;
    public TextField addCustomerAddress;
    public TextField addCustomerPostal;
    public TextField addCustomerPhone;
    public ComboBox<String> addCustomerCountry;
    public ComboBox<String> addCustomerDivision;
    public Button addCustomerSave;
    public Button addCustomerCancel;

    /**
     * Initialize from the Main controller, and populate combo box with country and division selection
     *
     * <p>Lambda expression to set division selection choice for when the country is selected</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            addCustomerCountry.setItems(DBCountry.getCountry());
            addCustomerCountry.setPromptText("Select Country...");

            // Lambda expression
            addCustomerCountry.valueProperty().addListener((obs, oldSelection, newSelection) ->{
               if(newSelection != null){
                   addCustomerDivision.getItems().clear();

                   try {
                       addCustomerDivision.setItems(DBCountry.getCountryDivision(addCustomerCountry.getValue()));

                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the input customer information and save new customer information into the database
     * @param actionEvent save customer action
     * @throws IOException FXMLLoader
     */
    public void addCustomerSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            String customerName = addCustomerName.getText();
            String customerAddress = addCustomerAddress.getText();
            String customerPostal = addCustomerPostal.getText();
            String customerPhone = addCustomerPhone.getText();
            String customerCountry = addCustomerCountry.getValue();
            String customerDivision = addCustomerDivision.getValue();
            int customerDivID = DBCountry.getDivisionID(addCustomerDivision.getValue());

            if (customerName.isEmpty() || customerAddress.isEmpty() || customerPostal.isEmpty() || customerPhone.isEmpty() || customerCountry == null || customerDivision == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One of the fields are empty");
                alert.showAndWait();
            } else {
                String sql = "INSERT INTO customers VALUE(NULL,?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, customerName);
                ps.setString(2, customerAddress);
                ps.setString(3, customerPostal);
                ps.setString(4, customerPhone);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, DBUser.loginUser().getUserName());
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, DBUser.loginUser().getUserName());
                ps.setInt(9, customerDivID);

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();

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
     * This method cancel add customer action and return to the main page
     * @param actionEvent FXMLLoader
     */
    public void addCustomerCancelAction(ActionEvent actionEvent) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
