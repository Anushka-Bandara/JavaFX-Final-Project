package edu.icet.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AdminLoginFormControoler {
    public JFXTextField txtAdminName;
    public JFXButton btnLogin;
    public Label lblForgotPassword;
    public JFXButton btnIcon;

    private static Integer count = 0 ;
    public JFXPasswordField txtpassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String adminId = txtAdminName.getText();
        String password = txtpassword.getText();


        Boolean istrue = false;

        if (count == 0) {
            istrue = AdminController.getInstance().addAdmin(adminId, password);
            count++;
        }

        istrue = AdminController.getInstance().ConfirmLoginInfo(adminId,password);

        if (istrue) {
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
        } else {
            // Show an alert if the login information is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password. Please try again.");
            alert.showAndWait();
        }


    }

    public void onClickForgotlbl(MouseEvent mouseEvent) {
    }

    public void btnIconOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/employee-forms/user-login.fxml");

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
}
