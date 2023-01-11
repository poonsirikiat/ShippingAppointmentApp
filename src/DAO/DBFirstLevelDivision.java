package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivision {
    /**
     * This method get the list of all first level division
     * @return firstLevelDivisionObservableList
     * @throws SQLException FXMLLoader
     */
    public static ObservableList<FirstLevelDivision> getAllFLD() throws SQLException {

        ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionID, divisionName, countryID);
            firstLevelDivisionObservableList.add(firstLevelDivision);
        }
        return firstLevelDivisionObservableList;
    }


}
