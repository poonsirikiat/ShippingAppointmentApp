package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUser {

    private static User userlogin;

    /**
     * this method return all of the user information
     * @return userList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<User> getAllUsers() throws SQLException {

        ObservableList<User> userList = FXCollections.observableArrayList();

        String sql = "SELECT * from users";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            User C = new User(userID, userName, userPassword);
            userList.add(C);
        }
        return userList;
    }

    /**
     * This method check if login username and password are valid
     * @param userName user name
     * @param password user password
     * @return true if the username and password are correct
     */
    public static Boolean loginValidation(String userName, String password) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = '" + userName + "' AND Password = '" + password + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userlogin = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
                return Boolean.TRUE;
            }
            else {
                return Boolean.FALSE;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method get all of user ID
     * @return userIDList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<Integer> getAllUserID() throws SQLException {
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();

        String sql = "SELECT users.User_ID FROM users ORDER by User_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            userIDList.add(rs.getInt("User_ID"));
        }
        return userIDList;
    }

    /**
     * This method user login information
     * @return userlogin
     */
    public static User loginUser(){
        return userlogin;
    }
}
