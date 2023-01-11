package model;

import DAO.DBProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Inventory {
    private static final ObservableList<Products> allProducts = FXCollections.observableArrayList();
    private static final ObservableList<AppProduct> allAssociateProducts = FXCollections.observableArrayList();


    /**
     * @param productId the product ID to lookup
     * @return the ID of product if equal
     */
    public static Products lookupProduct(int productId) throws SQLException {
        ObservableList<Products> allProduct = DBProduct.getProductList();
        for(Products pr : allProduct){
            if(pr.getId() == productId){
                return pr;
            }
        }
        return null;
    }

    /**
     * @param productName the product name to lookup
     * @return the name of the product data that match
     */
    public static ObservableList<Products> lookupProduct(String productName) throws SQLException {
        ObservableList<Products> allProduct = DBProduct.getProductList();
        ObservableList<Products> productLookup = FXCollections.observableArrayList();

        for(Products pr : allProduct){
            if(pr.getName().toLowerCase().contains(productName)){
                productLookup.add(pr);
            }
        }
        return productLookup;
    }

    /**
     * @param index the index of update product
     * @param newProduct the selected product of update product
     */
    public static void updateProduct(int index, Products newProduct){
        allProducts.set(index,newProduct);
    }


    /**
     *
     * @param selectedProduct the selected product of delete product
     * @return true deletes the selected product from the Products TableView
     */
    public static boolean deleteProduct(Products selectedProduct){
        for(Products pr : allProducts){
            if(pr.getId() == selectedProduct.getId()){
                allProducts.remove(pr);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the product list to return
     */
    public static ObservableList<Products> getAllProducts(){
        return allProducts;
    }

    public static ObservableList<AppProduct> getAllAsProducts(){
        return allAssociateProducts;
    }

}
