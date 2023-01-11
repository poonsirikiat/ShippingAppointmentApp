package controller;

import DAO.*;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.DBConnection;
import model.AppProduct;
import model.Appointment;
import model.Inventory;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;
import static model.Inventory.lookupProduct;

public class AddAppointment implements Initializable {
    public TextField addAppID;
    public TextField addAppTitle;
    public TextField addAppDescription;
    public TextField addAppLocation;
    public Button addAppSave;
    public Button addAppCancel;
    public TextField addAppType;
    public ComboBox<String> addAppContact;
    public DatePicker addAppStartDate;
    public ComboBox<String> addAppStartTime;
    public ComboBox<String> addAppEndTime;
    public ComboBox<Integer> addAppCustomerID;
    public ComboBox<Integer> addAppUserID;
    public TextField allPartSearch;
    public TableView<Products> partTable;
    public TableColumn<Products, Integer> partIdCol;
    public TableColumn<Products, String> partNameCol;
    public TableColumn<Products, Integer> partInventoryCol;
    public TableColumn<Products, Double> partPCCol;
    public TableView<Products> associatePartTable;
    public TableColumn<Products, Integer> associatePartIdCol;
    public TableColumn<Products, String> associatePartNameCol;
    public TableColumn<Products, Integer> associatePartInventoryCol;
    public TableColumn<Products, Double> associatePartPCCol;
    public Button addProductAddButton;
    public Button addProductRemoveButton;
    public ObservableList<Products> associateProductList = FXCollections.observableArrayList();


    /**
     * Initialize from the Main controller, and populate combo box with contact name,
     * customer ID, userID, start and end list of time, and calender to pick date
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            partTable.setItems(DBProduct.getProductList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPCCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatePartTable.setItems(associateProductList);
        associatePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatePartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatePartPCCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {

            addAppContact.setItems(DBContact.getAllContactName());
            addAppCustomerID.setItems(DBCustomer.getAllCustomerID());
            addAppUserID.setItems(DBUser.getAllUserID());


            ObservableList<String> appTime = FXCollections.observableArrayList();

            LocalTime startAppTime = LocalTime.MIN.plusHours(8);
            LocalTime finishAppTime = LocalTime.MAX.minusHours(2);

            if (!startAppTime.equals(0) || !finishAppTime.equals(0))
                while (startAppTime.isBefore(finishAppTime)) {
                    appTime.add(String.valueOf(startAppTime));
                    startAppTime = startAppTime.plusMinutes(15);
                }

            addAppStartTime.setItems(appTime);
            addAppEndTime.setItems(appTime);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the input appointment information and create new appointment in the database
     *
     * @param actionEvent save new appointment action
     * @throws IOException FXMLLoader
     */
    public void addAppSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            String appTitle = addAppTitle.getText();
            String appDescription = addAppDescription.getText();
            String appLocation = addAppLocation.getText();
            String appType = addAppType.getText();
            String appContact = addAppContact.getValue();
            Integer customerID = addAppCustomerID.getValue();
            Integer userID = addAppUserID.getValue();
            Integer contactID = DBContact.getContactID(appContact);


            LocalDate appDate = addAppStartDate.getValue();

            if (appTitle.isEmpty() || appDescription.isEmpty() || appLocation.isEmpty() || appType.isEmpty() ||
                    addAppStartDate.getValue() == null || addAppStartTime.getValue() == null || addAppEndTime.getValue() == null ||
                    customerID == null || appContact == null || userID == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One of the fields are empty");
                alert.showAndWait();
            } else {

                DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String appStartDate = appDate.format(formatterDate);
                String appStartTime = addAppStartTime.getValue();
                String appEndTime = addAppEndTime.getValue();

                Timestamp startLocalTimestamp = Timestamp.valueOf(appStartDate + " " + appStartTime + ":00");
                Timestamp endLocalTimestamp = Timestamp.valueOf(appStartDate + " " + appEndTime + ":00");

                LocalDateTime currentDateTime = LocalDateTime.now();

                LocalDateTime startDateTime = startLocalTimestamp.toLocalDateTime();
                ZonedDateTime startZoneLocal = startDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                ZonedDateTime startZoneUTC = startZoneLocal.withZoneSameInstant(ZoneId.of("UTC"));
                LocalDateTime startZoneLLDT = startZoneLocal.toLocalDateTime();

                LocalDateTime endDateTime = endLocalTimestamp.toLocalDateTime();
                ZonedDateTime endZoneLocal = endDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                ZonedDateTime endZoneUTC = endZoneLocal.withZoneSameInstant(ZoneId.of("UTC"));
                LocalDateTime endZoneLLDT = endZoneLocal.toLocalDateTime();


                String startUTC = startZoneUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String endUTC = endZoneUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                //Business hour
                ZonedDateTime startZoneEST = startZoneLocal.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime endZoneEST = endZoneLocal.withZoneSameInstant(ZoneId.of("America/New_York"));

                DayOfWeek startDayOfWeek = startZoneEST.toLocalDate().getDayOfWeek();
                DayOfWeek endDayOfWeek = endZoneEST.toLocalDate().getDayOfWeek();

                int startDayOfWeekInt = startDayOfWeek.getValue();
                int endDayOfWeekInt = endDayOfWeek.getValue();
                int startWeek = DayOfWeek.MONDAY.getValue();
                int endWeek = DayOfWeek.FRIDAY.getValue();

                System.out.println("Local time now: " + LocalDateTime.now() + "   " + "Business hr Zone: " + startZoneEST + "   Local Zone: " + startZoneLocal + "   " + startUTC + "   " + startDayOfWeek);

                if (startDateTime.isBefore(currentDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment day is before the current date");
                    alert.showAndWait();
                    return;
                }
                if (startDayOfWeekInt < startWeek || startDayOfWeekInt > endWeek || endDayOfWeekInt < startWeek || endDayOfWeekInt > endWeek) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment day is out of business Date of Mon-Fri EST time");
                    alert.showAndWait();
                    return;
                }
                if (startDateTime.isEqual(endDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment cannot have same start and end time");
                    alert.showAndWait();
                    return;
                }
                if (startDateTime.isAfter(endDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment end time is before start time");
                    alert.showAndWait();
                    return;
                }

                Boolean checkForOverlap = checkOverlap(customerID, startZoneLLDT, endZoneLLDT);
                if (!checkForOverlap) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment is overlapping with other schedule appointment");
                    alert.showAndWait();

                } else {

                    String sql = "INSERT INTO appointments VALUE(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, appTitle);
                    ps.setString(2, appDescription);
                    ps.setString(3, appLocation);
                    ps.setString(4, appType);
                    ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(startLocalTimestamp)));
                    ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(endLocalTimestamp)));
                    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(8, DBUser.loginUser().getUserName());
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(10, DBUser.loginUser().getUserName());
                    ps.setInt(11, customerID);
                    ps.setInt(12, userID);
                    ps.setInt(13, contactID);

                    ps.execute();

                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    String getAssociateId = rs.getString(1);

                    for(Products products : associateProductList){

                        int productNumber = products.getId();
                        String sql2 = "INSERT INTO associate_product VALUE(NULL, ?,?)";

                        PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

                        ps2.setInt(1, productNumber);
                        ps2.setInt(2, Integer.parseInt(getAssociateId));

                        ps2.execute();
                        ResultSet rs2 = ps2.getGeneratedKeys();
                        rs2.next();
                    }

                    Parent parent = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method check for overlapping appointment with the current new appointment and return true if there are no overlap
     *
     * @param customerID customer ID
     * @param start      local start date and time
     * @param end        local end date and time
     * @return true if no overlapping appointment
     * @throws SQLException FXMLLoader
     */
    public Boolean checkOverlap(Integer customerID, LocalDateTime start, LocalDateTime end) throws SQLException {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAppointmentList();

            for (Appointment app : allAppointment) {

                LocalDateTime startDateTime = app.getAppStartDateTime();
                LocalDateTime endDateTime = app.getAppEndDateTime();

                System.out.println(startDateTime + " " + endDateTime + "--" + start + " " + end);

                if (customerID == app.getCustomerID() && ((end.isAfter(endDateTime)) || end.isEqual(endDateTime)) && ((start.isBefore(endDateTime)))) {
                    return false;
                }
                if (customerID == app.getCustomerID() && ((start.isAfter(startDateTime)) || start.isEqual(startDateTime)) && ((end.isBefore(endDateTime)) || end.isEqual(endDateTime))) {
                    return false;
                }
                if (customerID == app.getCustomerID() && ((end.isAfter(startDateTime)) && ((start.isBefore(startDateTime)) || start.isEqual(startDateTime)))) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method cancel adding new appointment and return user to the main page
     *
     * @param actionEvent cancel action
     */
    public void addAppCancelAction(ActionEvent actionEvent) {
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


    /**
     * Search for ID or name of the part in product table.
     *
     * @param keyEvent the key ENTER to search for product.
     */
    public void productOnKey(KeyEvent keyEvent) throws SQLException {
        if(keyEvent.getCode() == KeyCode.ENTER){

            String productToSearch = allPartSearch.getText();

            ObservableList<Products> listOfProduct = FXCollections.observableArrayList();
            ObservableList<Products> allProduct = DBProduct.getProductList();

            if(productToSearch.equals("")){
                partTable.setItems(allProduct);
            }
            else {
                try {
                    Products lookForProduct = lookupProduct(Integer.parseInt(productToSearch));
                    if (lookForProduct != null) {
                        listOfProduct.add(lookForProduct);
                        partTable.setItems(listOfProduct);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Product ID not found");
                        alert.showAndWait();
                        partTable.setItems(DBProduct.getProductList());
                    }
                }
                catch (NumberFormatException e) {

                    if(allProduct.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Product name not found");
                        alert.showAndWait();
                        partTable.setItems(DBProduct.getProductList());
                    }
                    else{
                        for (int i = 0; i < allProduct.size(); i++) {
                            partTable.setItems(lookupProduct(productToSearch.toLowerCase()));
                        }
                        if(lookupProduct(productToSearch).size() == 0){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("The product search term cannot be found");
                            alert.showAndWait();
                            partTable.setItems(DBProduct.getProductList());
                        }
                    }
                }
            }
        }
    }

    public void addProductAddAction(ActionEvent actionEvent) {
        Products partToAssociate = partTable.getSelectionModel().getSelectedItem();

        if(partToAssociate != null) {
            associateProductList.add(partToAssociate);
            associatePartTable.setItems(associateProductList);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No data is selected to add");
            alert.showAndWait();
        }
    }

    public void addProductRemoveAction(ActionEvent actionEvent) {
        Products associatePartRemove = associatePartTable.getSelectionModel().getSelectedItem();

        if(associatePartRemove != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Remove?");
            Optional<ButtonType> result =alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associateProductList.remove(associatePartRemove);
            }
        }
    }
}
