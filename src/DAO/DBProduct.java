package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.Appointment;
import model.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBProduct {

    public static ObservableList<Products> getProductList() throws SQLException {

        ObservableList<Products> productsList = FXCollections.observableArrayList();

        String sql = "SELECT * from products";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int productID = rs.getInt("Product_ID");
            String productTitle = rs.getString("Name");
            Double productPrice = rs.getDouble("Price");
            int productStock = rs.getInt("Stock");
            int productMin = rs.getInt("Min");
            int productMax = rs.getInt("Max");

            Products products = new Products(productID, productTitle, productPrice, productStock, productMin, productMax);
            productsList.add(products);
        }
        return productsList;
    }

    public static ObservableList<Products> getProductSearch(String search) throws SQLException {

        ObservableList<Products> productsList = FXCollections.observableArrayList();

        String sql = "SELECT * from products";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int productID = rs.getInt("Product_ID");
            String productTitle = rs.getString("Name");
            Double productPrice = rs.getDouble("Price");
            int productStock = rs.getInt("Stock");
            int productMin = rs.getInt("Min");
            int productMax = rs.getInt("Max");

            Products products = new Products(productID, productTitle, productPrice, productStock, productMin, productMax);
            productsList.add(products);
        }
        return productsList;
    }
}
