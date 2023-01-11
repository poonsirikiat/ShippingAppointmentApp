package controller;

import DAO.DBCustomer;
import DAO.DBProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Customer;
import model.Inventory;
import javafx.scene.Node;

import javafx.event.ActionEvent;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.Inventory.getAllProducts;
import static model.Inventory.lookupProduct;

public class Product implements Initializable {
    public TableView<Products> productTable;
    public TableColumn<Products, Integer> productIdCol;
    public TableColumn<Products, String> productNameCol;
    public TableColumn<Products, Integer> productInventoryCol;
    public TableColumn<Products, Double> productPCCol;
    public TextField productSearch;
    public Button addProductId;
    public Button deleteProductId;
    public Button updateProductId;
    public Button backProductId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Products> allProduct = DBProduct.getProductList();

            productTable.setItems(allProduct);

            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPCCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }catch (SQLException e) {
                e.printStackTrace();
            }

    }
    /**
     * Load to addProduct controller.
     *
     * @param actionEvent add product action
     * @throws IOException FXMLLoader
     */
    public void addProductAction(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProductList.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Get selected item to modify and load to modifyProduct controller.
     * @param actionEvent modify product action
     * @throws IOException FXMLLoader
     */
    public void updateProductAction(ActionEvent actionEvent) throws IOException {

        Products productToUpdate = (Products) productTable.getSelectionModel().getSelectedItem();
        if(productToUpdate != null) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifyProductList.fxml"));
            Parent root = loader.load();
            ModifyProductList control = loader.getController();
            control.modifyFromMain(productToUpdate);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("The modify product is not selected");
            alert1.showAndWait();
        }
    }

    /**
     * Get selected item to delete from product table.
     * If selected item contain associate part, the product cannot be deleted.
     */
    public void deleteProductAction() {
        Products productToDelete = (Products) productTable.getSelectionModel().getSelectedItem();
        if(productToDelete != null) {
                    Inventory.deleteProduct(productToDelete);

        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("The delete product is not selected");
            alert1.showAndWait();
        }
    }
    /**
     * Search for ID or name of the part in product table.
     * @param keyEvent the key ENTER to search for product.
     */
    public void productOnKey(KeyEvent keyEvent) throws SQLException {
        if(keyEvent.getCode() == KeyCode.ENTER){

            String productToSearch = productSearch.getText();

            ObservableList<Products> listOfProduct = FXCollections.observableArrayList();
            ObservableList<Products> allProduct = DBProduct.getProductList();

            if(productToSearch.equals("")){
                productTable.setItems(allProduct);
            }
            else {
                try {
                    Products lookForProduct = lookupProduct(Integer.parseInt(productToSearch));
                    if (lookForProduct != null) {
                        listOfProduct.add(lookForProduct);
                        productTable.setItems(listOfProduct);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Product ID not found");
                        alert.showAndWait();
                        productTable.setItems(DBProduct.getProductList());
                    }
                }
                catch (NumberFormatException e) {

                    if(allProduct.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Product name not found");
                        alert.showAndWait();
                        productTable.setItems(DBProduct.getProductList());
                    }
                    else{
                        for (int i = 0; i < allProduct.size(); i++) {
                            productTable.setItems(lookupProduct(productToSearch.toLowerCase()));
                        }
                        if(lookupProduct(productToSearch).size() == 0){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("The product search term cannot be found");
                            alert.showAndWait();
                            productTable.setItems(DBProduct.getProductList());
                        }
                    }
                }
            }
        }
    }

    public void backProductAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
