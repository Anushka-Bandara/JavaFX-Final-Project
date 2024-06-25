package edu.icet.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.user.User;
import edu.icet.model.user.UserTbl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserViewFormController implements Initializable {
    public JFXButton backToHomebtn;
    public JFXTextField txtUserId;
    public JFXButton btnSearch;
    public TableView tblUserView;
    public TableColumn coluserId;
    public TableColumn colname;
    public TableColumn colemail;
    public TableColumn colrole;
    public JFXButton btnRemove;

    String selectUserId;
    public void backToHomebtnOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/admin-forms/AdminDashboard.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();

            // Assuming you want to replace the current scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (selectUserId != null) {
            Boolean isDeleted = UserController.getInstance().removeUser(selectUserId);
            if (isDeleted) {
                loadUsetTable();
                txtUserId.setText("");
                showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the user.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No user selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        coluserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsetTable();
        tblUserView.setOnMouseClicked(this::onClicked);
    }

    private void loadUsetTable() {
        ObservableList<User> tblData = UserController.getInstance().getAllUser();
        ObservableList<UserTbl> tbl = FXCollections.observableArrayList();

        tblData.forEach(user -> {
            tbl.add(
                    new UserTbl(
                            user.getUserId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole()
                    )
            );
            tblUserView.setItems(tbl);
        });
    }

    public void onClicked(MouseEvent mouseEvent) {
        if (tblUserView.getSelectionModel().getSelectedItem() != null) {
            UserTbl selectedSupplier = (UserTbl) tblUserView.getSelectionModel().getSelectedItem();
            selectUserId = selectedSupplier.getUserId();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
