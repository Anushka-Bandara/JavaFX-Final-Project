package edu.icet.controller.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.controller.employee.EmployeeDashboardFormController;
import edu.icet.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ProductManagmentFormController implements Initializable {


    public Label lblAddImageIcon;
    public JFXTextField txtProductId;
    public JFXTextField txtCategory;
    public JFXTextField txtname;
    public JFXTextField txtSize;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public ImageView imageView;
    public JFXButton btnViewProductCatelog;
    public JFXComboBox cmbCategory;
    public JFXComboBox cmbType;

    private String selectedImageFilePath;


    public void handleImageIconClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) lblAddImageIcon.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = new Image(fileInputStream);
                imageView.setImage(image);
                selectedImageFilePath = selectedFile.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(imageView);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {


        String productId = ProductController.getInstance().genarateId(gettype()) ;
        String name = txtname.getText();
        String size = txtSize.getText();
        String category = (String) cmbCategory.getValue();
        Double price = Double.parseDouble(txtPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());
        String imagePath = selectedImageFilePath;

        Product product = new Product(productId, name, size, category, price, qty, imagePath);

        Boolean isAdd = ProductController.getInstance().saveProduct(product);
        if(isAdd){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Success");
            alert.setHeaderText(null);
            alert.setContentText("Product is Added Successfully");
            alert.showAndWait();

            txtProductId.setText("");
            txtname.setText("");
            cmbCategory.setValue(null);
            txtPrice.setText("");
            txtSize.setText("");
            txtQty.setText("");
            imageView.setImage(null);
            EmployeeDashboardFormController.getInstance().load();


        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Error");
            alert.setHeaderText(null);
            alert.setContentText("Product Not Added");
            alert.showAndWait();
        }
    }

    private String gettype() {
        String st = (String) cmbType.getValue();
        //System.out.println(st);
        switch (st){
            case "TShirt":return "T" ;
            case "Shirt":return "S" ;
            case "Short":return "H" ;
            case "Top":return "T" ;
            case "Bottom":return "B" ;
            case "Trouser":return "R" ;
        }
        return null;
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String productId = txtProductId.getText();
        String name = txtname.getText();
        String size = txtSize.getText();
        String category = (String) cmbCategory.getValue();
        Double price = Double.parseDouble(txtPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());
        String imagePath = selectedImageFilePath;

        Product product = new Product(productId, name, size, category, price, qty, imagePath);

        Boolean isUpdated = ProductController.getInstance().updateProduct(product);
        if (isUpdated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Success");
            alert.setHeaderText(null);
            alert.setContentText("Product is Updated Successfully");
            alert.showAndWait();

            txtProductId.setText("");
            txtname.setText("");
            cmbCategory.setValue(null);
            txtPrice.setText("");
            txtSize.setText("");
            txtQty.setText("");
            imageView.setImage(null);
            EmployeeDashboardFormController.getInstance().load();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText(null);
            alert.setContentText("Product Not Updated");
            alert.showAndWait();
        }
    }
    public void btnRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String productId = txtProductId.getText();

        Boolean isDeleted = ProductController.getInstance().deleteProduct(productId);
        if (isDeleted) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Success");
            alert.setHeaderText(null);
            alert.setContentText("Product is Deleted Successfully");
            alert.showAndWait();

            txtProductId.setText("");
            txtname.setText("");
            cmbCategory.setValue(null);
            txtPrice.setText("");
            txtSize.setText("");
            txtQty.setText("");
            imageView.setImage(null);
            EmployeeDashboardFormController.getInstance().load();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(null);
            alert.setContentText("Product Not Deleted");
            alert.showAndWait();
        }
    }
    public void btnViewProductCatelogOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> types = FXCollections.observableArrayList(
                "TShirt", "Shirt", "Short","Top","Bottom","Trouser");
        cmbType.setItems(types);

        ObservableList<String> ctgry = FXCollections.observableArrayList(
                "Mens", "Womens", "Kids");
        cmbCategory.setItems(ctgry);

    }
}
