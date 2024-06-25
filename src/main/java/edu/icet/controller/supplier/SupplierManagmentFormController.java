package edu.icet.controller.supplier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.controller.supplier.SupplierController;
import edu.icet.db.DBConnection;
import edu.icet.model.supplier.Supplier;
import edu.icet.model.supplier.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SupplierManagmentFormController implements Initializable {

    public TableView tblSupplierManagment;
    public JFXTextField txtId;
    public JFXTextField txtcompany;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnremove;

    private String selectedSupplierId;

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            // Generate a new supplier ID
            String newSupplierId = generateNewSupplierId();

            // Collect input from text fields
            String name = txtName.getText();
            String company = txtcompany.getText();
            String email = txtEmail.getText();

            // Validate input (optional but recommended)
            if (name.isEmpty() || company.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields.");
                return;
            }

            // Create a new Supplier object
            Supplier newSupplier = new Supplier(newSupplierId, name, company, email);

            // Add the new supplier to the database
            boolean isAdded = SupplierController.getInstance().add(newSupplier);

            if (isAdded) {
                loadTable(); // Refresh the table after adding
                clearFields(); // Clear the fields after adding
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the supplier.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private String generateNewSupplierId() {
        // Implement logic to generate a new supplier ID
        // For example, fetch the current maximum ID from the database and increment it
        // Here is a simple example assuming IDs are in the format "S001", "S002", etc.
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString("supplierId");
                int newId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("S%03d", newId);
            } else {
                return "S001"; // Default ID if no suppliers exist
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "S001"; // Fallback ID
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            // Collect input from text fields
            String supplierId = txtId.getText();
            String name = txtName.getText();
            String company = txtcompany.getText();
            String email = txtEmail.getText();

            // Validate input (optional but recommended)
            if (supplierId.isEmpty() || name.isEmpty() || company.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields.");
                return;
            }

            // Create a new Supplier object
            Supplier updatedSupplier = new Supplier(supplierId, name, company, email);

            // Update the supplier in the database
            boolean isUpdated = SupplierController.getInstance().update(updatedSupplier);

            if (isUpdated) {
                loadTable(); // Refresh the table after updating
                clearFields(); // Clear the fields after updating
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the supplier.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTable();

        tblSupplierManagment.setOnMouseClicked(this::onClickRow);

    }

    private void loadTable() {
        ObservableList<Supplier> tblData = SupplierController.getInstance().getAll();
        ObservableList<Table> tbl = FXCollections.observableArrayList();

        tblData.forEach(supplier -> {
            tbl.add(
                    new Table(
                            supplier.getSupplierId(),
                            supplier.getName(),
                            supplier.getCompany(),
                            supplier.getEmail()
                    )
            );
            tblSupplierManagment.setItems(tbl);
        });
    }

//    public boolean onClickRow(MouseEvent mouseEvent) {
//        onClickRow(mouseEvent.)//how to get selected row data from table
//    }

    public void btnremoveOnAction(ActionEvent actionEvent) {
        if (selectedSupplierId != null) {
            Boolean isDeleted = SupplierController.getInstance().delete(selectedSupplierId);
            if (isDeleted) {
                loadTable(); // Refresh the table after deletion
                clearFields(); // Clear the fields after deletion
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the supplier.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No supplier selected.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void onClickRow(MouseEvent mouseEvent) {
        if (tblSupplierManagment.getSelectionModel().getSelectedItem() != null) {
            Table selectedSupplier = (Table) tblSupplierManagment.getSelectionModel().getSelectedItem();
            selectedSupplierId = selectedSupplier.getSupplierId();
            txtId.setText(selectedSupplier.getSupplierId());
            txtName.setText(selectedSupplier.getName());
            txtcompany.setText(selectedSupplier.getCompany());
            txtEmail.setText(selectedSupplier.getEmail());
        }
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtcompany.clear();
        txtEmail.clear();
    }

}
