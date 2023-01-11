package controller;


import DAO.DBContact;
import DAO.DBCountry;
import DAO.DBReport;
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
import model.Report;
import model.ReportTotalApp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportForm implements Initializable {
    public TableView<Report> reportTable;
    public TableColumn<Report, Integer> appIDCol;
    public TableColumn<Report, String> appTypeCol;
    public TableColumn<Report, Integer> appContactIDCol;
    public TableColumn<Report, String> appTitleCol;
    public TableColumn<Report, String> appDescriptionCol;
    public TableColumn<Report, String> appStartCol;
    public TableColumn<Report, String> appEndCol;
    public TableColumn<Report, Integer> appCustomerIDCol;
    public Button returnButton;
    public ComboBox<String> filterContact;
    public ComboBox<String> filterCountry;
    public Button appByContact;
    public Button appByCountry;
    public TableView<ReportTotalApp> totalNumReportTable;
    public TableColumn<ReportTotalApp, String> totalMonthReportCol;
    public TableColumn<ReportTotalApp, String> totalTypeReportCol;
    public TableColumn<ReportTotalApp, Integer> totalCountReportCol;
    /**
     * Initialize the Main controller, and populate the report table and appointment table view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Report> allAppointment = DBReport.getAppointmentListReport();
            reportTable.setItems(allAppointment);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
            appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
            appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
            appContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
            appStartCol.setCellValueFactory(new PropertyValueFactory<>("appStartDateTime"));
            appEndCol.setCellValueFactory(new PropertyValueFactory<>("appEndDateTime"));
            appCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            filterContact.setItems(DBContact.getAllContactName());
            filterCountry.setItems(DBCountry.getCountry());
            filterCountry.setPromptText("Select Country...");

            ObservableList<ReportTotalApp> allApp = DBReport.getFirstReport();
            totalNumReportTable.setItems(allApp);
            totalMonthReportCol.setCellValueFactory(new PropertyValueFactory<>("appMonth"));
            totalTypeReportCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
            totalCountReportCol.setCellValueFactory(new PropertyValueFactory<>("appCount"));

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method return user to the main page if button OK is click
     * @param actionEvent return action
     */
    public void returnAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Return to main page?");
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
     * This method filter the appointment table by country name selected
     * @param actionEvent filter country action
     * @throws SQLException FXMLLoader
     */
    public void appByCountryButton(ActionEvent actionEvent) throws SQLException {

        String countrySelect = filterCountry.getValue();

        ObservableList<Report> allReport = DBReport.getAppointmentListReport();
        ObservableList<Integer> allCustomerID = DBReport.getCountryID(countrySelect);
        ObservableList<Report> reportByCountry = FXCollections.observableArrayList();
        for (Report report : allReport) {
            for (Integer idList : allCustomerID) {
                 if(report.getCustomerID() == idList){
                     reportByCountry.add(report);
                 }
            }
        }
        reportTable.setItems(reportByCountry);
    }

    /**
     * This method filter the appointment table by contact name selected
     * @param actionEvent filter contact action
     * @throws SQLException FXMLLoader
     */
    public void appByContactButton(ActionEvent actionEvent) throws SQLException {

        String contactSelect = filterContact.getValue();

        ObservableList<Report> allReport = DBReport.getAppointmentListReport();
        ObservableList<Report> reportByContact = FXCollections.observableArrayList();
        for(Report report: allReport){
            if(report.getAppContact().equals(contactSelect)){
                reportByContact.add(report);

            }
        }
        reportTable.setItems(reportByContact);

    }
}
