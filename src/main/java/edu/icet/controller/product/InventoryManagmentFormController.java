package edu.icet.controller.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Product;
import edu.icet.model.inventory.InventoryTbl2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryManagmentFormController implements Initializable {

    public JFXTextField txtProductId;
    public JFXButton btnSearch;
    public TableView tblView;
    public TableColumn colProductId;
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colSize;
    public TableColumn colQty;
    public JFXButton btnAddStck;
    public JFXButton btnUpdateStock;
    public JFXTextField txtStock;

    public void btnSearchOnAction(ActionEvent actionEvent) {
        ObservableList<Product> productIdlist = ProductController.getInstance().getAllProducts();
        ObservableList<InventoryTbl2> tbl = FXCollections.observableArrayList();
        String productID = txtProductId.getText();

        productIdlist.forEach(product -> {
            if(productID.equals(product.getProductId())){
                tbl.add(
                        new InventoryTbl2(
                                product.getProductId(),
                                product.getName(),
                                product.getCategory(),
                                product.getSize(),
                                product.getQty()
                        )
                );
                tblView.setItems(tbl);
            }
        });
    }

    public void btnAddStckOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String productId = txtProductId.getText();
        Product product = ProductController.getInstance().getSelectProduct(productId);
        Integer priviousQty = product.getQty();
        Integer newQty = Integer.parseInt(txtStock.getText());
        newQty+=priviousQty;

        Boolean b = ProductController.getInstance().stockManagment(productId,newQty);
        if(b){
            showAlert(Alert.AlertType.INFORMATION,"Added","Stock Added");
        }
        loadInventoryTable();
    }

    public void btnUpdateStockOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String productId = txtProductId.getText();
        Integer Qty = Integer.parseInt(txtStock.getText());
        Boolean b = ProductController.getInstance().stockManagment(productId,Qty);

        if(b){
            showAlert(Alert.AlertType.INFORMATION,"Updated","Stock Updated");
        }
        loadInventoryTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        if(txtProductId.getText().equals("")){
            loadInventoryTable();
        }

    }

    private void loadInventoryTable() {
        ObservableList<Product> tblData = ProductController.getInstance().getAllProducts();
        ObservableList<InventoryTbl2> tbl = FXCollections.observableArrayList();

        tblData.forEach(product -> {
            tbl.add(
                    new InventoryTbl2(
                            product.getProductId(),
                            product.getName(),
                            product.getCategory(),
                            product.getSize(),
                            product.getQty()
                    )
            );
            tblView.setItems(tbl);
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
