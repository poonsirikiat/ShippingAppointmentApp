package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBConnection;
import model.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAssociateProduct {

    public static void deleteAssociateProduct(int appointmentID) throws SQLException {

        String sql = "Delete FROM associate_product WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1,appointmentID);
        ps.execute();
    }



    public static ObservableList<Products> getAllAssociateList(int appID) throws SQLException {
        ObservableList<Products> associateProductList = FXCollections.observableArrayList();

        String sql = "SELECT products.Product_ID, products.Name, products.Price, products.Stock, products.Min, products.Max" +
                " FROM products, associate_product, appointments WHERE appointments.Appointment_ID = ? and associate_product.Product_ID = products.Product_ID and associate_product.Appointment_ID = appointments.Appointment_ID";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1,appID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int productID = rs.getInt("Product_ID");
            String productTitle = rs.getString("Name");
            Double productPrice = rs.getDouble("Price");
            int productStock = rs.getInt("Stock");
            int productMin = rs.getInt("Min");
            int productMax = rs.getInt("Max");

            Products products = new Products(productID, productTitle, productPrice, productStock, productMin, productMax);
            associateProductList.add(products);
        }
        return associateProductList;
    }

}
