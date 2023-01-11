package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBAppointment {

    /**
     * This method get all the list of appointments and its information
     * @return appointmentList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<Appointment> getAppointmentList() throws SQLException {

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * from appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

            while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String appTitle = rs.getString("Title");
            String appDescription = rs.getString("Description");
            String appLocation = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String appType = rs.getString("Type");
            LocalDateTime appStartDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appEndDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            Appointment appointment = new Appointment(appID, appTitle, appDescription, appLocation, contactID, appType, appStartDateTime, appEndDateTime, customerID, userID);
            appointmentList.add(appointment);
        }
            return appointmentList;
        }

    /**
     * This method delete the selected customer ID
     * @param selectCustomerID customer ID selected
     * @throws SQLException FXMLLoader
     */
        public static void deleteCustomerApp(int selectCustomerID) throws SQLException{

            String sql = "Delete FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1,selectCustomerID);
            ps.execute();
    }

    /**
     * This method delete the selected appointment ID
     * @param selectAppointmentID appointment ID selected
     * @throws SQLException FXMLLoader
     */
    public static void deleteAppointment(int selectAppointmentID) throws SQLException{

        String sql = "Delete FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1,selectAppointmentID);
        ps.execute();

    }

    /**
     * This method get all the contact name from contact list
     * @return contactList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<String> getAllContact() throws SQLException{

        ObservableList<String> contactList = FXCollections.observableArrayList();

        String sql = "SELECT contacts.Contact_Name FROM contacts";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            contactList.add(rs.getString("Contact_Name"));
        }
        return contactList;
    }

    /**
     * This method get all appointment type
     * @return typeList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<String> getAllType() throws SQLException{

        ObservableList<String> typeList = FXCollections.observableArrayList();

        String sql = "SELECT appointments.Type FROM appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            typeList.add(rs.getString("Type"));
        }
        return typeList;
    }

}
