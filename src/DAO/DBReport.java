package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.Report;
import model.ReportTotalApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBReport {

    /**
     * This method get all of the appointment for the report table
     * @return appointmentList
     * @throws SQLException FXMLLoader
     */
     public static ObservableList<Report> getAppointmentListReport() throws SQLException {

        ObservableList<Report> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT appointments.Appointment_ID, appointments.Type, appointments.Title, appointments.Description, " +
                "appointments.Start, appointments.End, appointments.Customer_ID, contacts.Contact_ID, contacts.Contact_Name from appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appID = rs.getInt("Appointment_ID");
            String appTitle = rs.getString("Title");
            String appDescription = rs.getString("Description");
            String appContact = rs.getString("Contact_Name");
            String appType = rs.getString("Type");
            LocalDateTime appStartDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appEndDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int contactID = rs.getInt("Contact_ID");

            Report report = new Report(appID, appTitle, appType, appContact, appDescription, appStartDateTime, appEndDateTime, customerID, contactID);
            appointmentList.add(report);
        }
        return appointmentList;
    }

    /**
     * This method return customer ID that associate with country name
     * @param countryName country name
     * @return customerIDList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<Integer> getCountryID(String countryName) throws SQLException{

        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();

        String sql = "SELECT countries.Country, countries.Country_ID, first_level_divisions.Division_ID, customers.Customer_ID " +
                "FROM countries, first_level_divisions, customers WHERE countries.Country = ? and " +
                "countries.Country_ID = first_level_divisions.Country_ID and first_level_divisions.Division_ID = customers.Division_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1,countryName);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
             customerIDList.add(rs.getInt("Customer_ID"));
        }
        return customerIDList;
    }

    /**
     * This method get the total number of customer appointments by type and month
     * @return contactList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<ReportTotalApp> getFirstReport() throws SQLException{

        ObservableList<ReportTotalApp> contactList = FXCollections.observableArrayList();

        String sql = "SELECT appointments.Type, monthname(Start) as \"Month\", count(*) as \"Count\" FROM client_schedule.appointments group by Type, Month;";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
           String appMonth = rs.getString("Month");
           String appType = rs.getString("Type");
           int appCount = rs.getInt("count");

           ReportTotalApp reportTotalApp = new ReportTotalApp(appMonth, appType, appCount);
           contactList.add(reportTotalApp);
        }
        return contactList;
    }
}
