package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {
    /**
     * This method get all of the customer information list
     * @return customerList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<Customer> getAllCustomer() throws SQLException {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, " +
                    "customers.Postal_Code, customers.Phone, customers.Division_ID, " +
                    "first_level_divisions.Division, countries.Country FROM customers, first_level_divisions, countries " +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID and first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, divisionID, divisionName, countryName);
                customerList.add(customer);
            }

        return customerList;
    }

    /**
     * This method delete the selected customer ID
     * @param selectCustomerID customer ID
     * @throws SQLException FXMLLoader
     */
    public static void deleteCustomer(int selectCustomerID) throws SQLException{

        String sql = "Delete FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1,selectCustomerID);
        ps.execute();

    }

    /**
     * This method get all customer ID
     * @return customerIDList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<Integer> getAllCustomerID() throws SQLException {
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();

        String sql = "SELECT customers.Customer_ID FROM customers ORDER by Customer_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            customerIDList.add(rs.getInt("Customer_ID"));
        }
        return customerIDList;
    }

}