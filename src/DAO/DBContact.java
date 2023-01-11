package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {

    /**
     * This method get only contact name from contact list
     * @return contactNameList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<String> getAllContactName() throws SQLException {
        ObservableList<String> contactNameList = FXCollections.observableArrayList();

        String sql = "SELECT contacts.Contact_Name FROM contacts";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            contactNameList.add(rs.getString("Contact_Name"));
        }
        return contactNameList;
    }

    /**
     * This method get contact ID that associate with contact name
     * @param contactName contact name
     * @return contactID
     * @throws SQLException FXMLLoader
     */
    public static int getContactID(String contactName) throws SQLException {
        int contactID = 0;

        String sql = "SELECT contacts.Contact_ID, contacts.Contact_Name FROM contacts WHERE Contact_Name = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            contactID = rs.getInt("Contact_ID");
        }
        return contactID;
    }
}
