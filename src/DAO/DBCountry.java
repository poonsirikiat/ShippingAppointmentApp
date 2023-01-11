package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountry {

    /**
     * This method get only the country name list
     * @return onlyCountryList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<String> getCountry() throws SQLException{

        ObservableList<String> onlyCountryList = FXCollections.observableArrayList();

        String sql = "SELECT countries.Country FROM countries";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            onlyCountryList.add(rs.getString("Country"));
        }
        return onlyCountryList;
    }
    public static ObservableList<String> getSelectCountry(String selectCountry) throws SQLException{

        ObservableList<String> onlyCountryList = FXCollections.observableArrayList();

        String sql = "SELECT countries.Country FROM countries WHERE";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            onlyCountryList.add(rs.getString("Country"));
        }
        return onlyCountryList;
    }

    /**
     * This method get all the division list that associate with the selected country
     * @param selectCountry country selected
     * @return divisionList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<String> getCountryDivision(String selectCountry) throws SQLException{

        ObservableList<String> divisionList = FXCollections.observableArrayList();

        String sql = "SELECT countries.Country_ID, countries.Country, first_level_divisions.Division " +
                "FROM countries, first_level_divisions WHERE countries.Country = ? and countries.Country_ID = first_level_divisions.Country_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1,selectCountry);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            divisionList.add(rs.getString("Division"));
        }
        return divisionList;
    }

    /**
     * This method get division ID that associate with division name
     * @param divisionName division name
     * @return divisionID
     * @throws SQLException FXMLLoader
     */
    public static Integer getDivisionID(String divisionName) throws SQLException{

        Integer divisionID = 0;

        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1,divisionName);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }
}
