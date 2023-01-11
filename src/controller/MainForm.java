package controller;

import DAO.DBAppointment;
import DAO.DBAssociateProduct;
import DAO.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.DBConnection;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIDCol;
    public TableColumn<Customer, String> customerNameCol;
    public TableColumn<Customer, String> customerAddressCol;
    public TableColumn<Customer, String> customerPostalCol;
    public TableColumn<Customer, String> customerPhoneCol;
    public TableColumn<Customer, String> customerDivision;
    public TableColumn<Customer, String> customerCountry;
    public Button addCustomer;
    public Button modifyCustomer;
    public Button deleteCustomer;
    public TableView<Appointment> appTable;
    public TableColumn<Appointment, Integer> appIDCol;
    public TableColumn<Appointment, String> appTitleCol;
    public TableColumn<Appointment, String> appDescriptionCol;
    public TableColumn<Appointment, String> appLocationCol;
    public TableColumn<Appointment, String> appContactCol;
    public TableColumn<Appointment, String> appTypeCol;
    public TableColumn<Appointment, LocalDateTime> appStartDateCol;
    public TableColumn<Appointment, LocalDateTime> appEndDateCol;
    public TableColumn<Appointment, Integer> appCustomerIDCol;
    public TableColumn<Appointment, Integer> appUserIDCol;

    public Button addApp;
    public Button modifyApp;
    public Button deleteApp;
    public RadioButton viewByMonth;
    public RadioButton viewByWeek;
    public Button report;
    public Button logout;
    public RadioButton viewAll;
    public ToggleGroup filterApp;
    public Button product;


    /**
     * Initialize the Main controller, and populate the customer table and appointment table view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Customer> allCustomer = DBCustomer.getAllCustomer();
            customerTable.setItems(allCustomer);

            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerDivision.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
            customerCountry.setCellValueFactory(new PropertyValueFactory<>("CountryName"));

            ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
            appTable.setItems(allAppointment);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
            appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
            appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
            appLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
            appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
            appStartDateCol.setCellValueFactory(new PropertyValueFactory<>("appStartDateTime"));
            appEndDateCol.setCellValueFactory(new PropertyValueFactory<>("appEndDateTime"));
            appCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method take user to the add customer page
     * @param actionEvent add new customer
     */
    public void addCustomerAction(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method get the selected customer and their information, and take the user to the modify customer page
     * @param actionEvent modify customer action
     * @throws IOException FXMLLoader
     */
    public void modifyCustomerAction(ActionEvent actionEvent) throws IOException {
        try {


            Customer customerSelect = customerTable.getSelectionModel().getSelectedItem();

            if (customerSelect != null) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ModifyCustomer.fxml"));
                Parent root = loader.load();

                ModifyCustomer modCustomer = (ModifyCustomer) loader.getController();
                modCustomer.modFromMain(customerSelect);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Customer is not selected");
                alert.showAndWait();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method get the selected customer and delete their data and an associate appointment from the database
     * <p>Lambda expression to get the result of alert if button is selected OK</p>
     * @param actionEvent delete customer
     */
    public void deleteCustomerAction(ActionEvent actionEvent) {

        Customer customerSelect = customerTable.getSelectionModel().getSelectedItem();

        if (customerSelect != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Delete the customer info and all of their appointment?");

            //Lambda expression
            alert.showAndWait().ifPresent((result -> {
                if (result == ButtonType.OK) {
                    try {
                        DBAppointment.deleteCustomerApp(customerSelect.getCustomerID());
                        DBCustomer.deleteCustomer(customerSelect.getCustomerID());

                    ObservableList<Customer> allCustomer = DBCustomer.getAllCustomer();
                    ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
                    customerTable.setItems(allCustomer);
                    appTable.setItems(allAppointment);

                    } catch (SQLException e) {
                    e.printStackTrace();
                    }
                }
            }));
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Please select the customer to delete");
            alert1.showAndWait();
        }
    }

    /**
     * This method take user to the add appointment page
     * @param actionEvent add appointment
     */
    public void addAppAction(ActionEvent actionEvent) {
       try {
           Parent parent = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
           Scene scene = new Scene(parent);
           Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
           stage.setScene(scene);
           stage.show();
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * This method get the selected appointment and its information, and take the user to the modify appointment page
     * @param actionEvent modify appointment action
     * @throws IOException FXMLLoader
     */
    public void modifyAppAction(ActionEvent actionEvent) throws IOException {
        try {
            Appointment appSelect = appTable.getSelectionModel().getSelectedItem();


            if (appSelect != null) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ModifyAppointment.fxml"));
                Parent root = loader.load();

                ModifyAppointment modifyAppointment = (ModifyAppointment) loader.getController();
                modifyAppointment.modFromMain(appSelect);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment is not selected");
                alert.showAndWait();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method get the selected appointment and delete their data from the database
     * <p>Lambda expression to get the result of alert if button is selected OK</p>
     * @param actionEvent delete appointment action
     */
    public void deleteAppAction(ActionEvent actionEvent) {

        Appointment appointmentSelect = appTable.getSelectionModel().getSelectedItem();
        int id = appointmentSelect.getAppID();
        String type = appointmentSelect.getAppType();
        if (appointmentSelect != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Delete this appointment?");

            //Lambda expression
            alert.showAndWait().ifPresent((result -> {
                if (result == ButtonType.OK) {
                    try {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Information");
                        alert1.setHeaderText("Appointment ID: " + id + ", Type: " + type + " is deleted.");
                        alert1.showAndWait();

                        DBAssociateProduct.deleteAssociateProduct(appointmentSelect.getAppID());
                        DBAppointment.deleteAppointment(appointmentSelect.getAppID());

                        ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
                        appTable.setItems(allAppointment);
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Please select the appointment to delete");
            alert1.showAndWait();
        }
    }

    /**
     * This method show all appointment
     * @param actionEvent view all appointment
     */
    public void viewByAll(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
            appTable.setItems(allAppointment);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method show all appointment from present to the next 30 days
     * @param actionEvent view by month
     */
    public void viewByMonthAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
            ObservableList<Appointment> allAppByMonth = FXCollections.observableArrayList();

            LocalDateTime startMonth = LocalDateTime.now();
            LocalDateTime endMonth = LocalDateTime.now().plusMonths(1);

            for (Appointment app : allAppointment) {
                if (app.getAppStartDateTime().isAfter(startMonth) && app.getAppStartDateTime().isBefore(endMonth)) {
                    allAppByMonth.add(app);
                }
            }
            appTable.setItems(allAppByMonth);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method show all appointment from present to next week
     * @param actionEvent view by week
     */
    public void viewByWeekAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();
            ObservableList<Appointment> allAppByWeek = FXCollections.observableArrayList();

            LocalDateTime startWeek = LocalDateTime.now();
            LocalDateTime endWeek = LocalDateTime.now().plusWeeks(1);

            for (Appointment app : allAppointment) {
                if (app.getAppStartDateTime().isAfter(startWeek) && app.getAppStartDateTime().isBefore(endWeek)) {
                    allAppByWeek.add(app);
                }
            }
            appTable.setItems(allAppByWeek);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method send user to the report page
     * @param actionEvent report action
     */
    public void reportAction(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/Report.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void productAction(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/product.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method will close the system and disconnect from database
     * @param actionEvent logout action
     */
    public void logoutAction(ActionEvent actionEvent) {
        DBConnection.closeConnection();
        System.exit(0);
    }



}
