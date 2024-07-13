package edu.icet.controller.user;

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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class UserLoginFormController {
    public JFXTextField txtUserName;
    public JFXButton btnLogin;
    public Label lblForgotPassword;
    public Label lblContactAdmin;
    public JFXButton btnIcon;
    public JFXPasswordField txtPasword;

    public void btnLoginOnAction(ActionEvent actionEvent) {

        String username = txtUserName.getText();
        String password = txtPasword.getText();

        Boolean isTrue = UserController.getInstance().ConfirmLoginInfo(username,password);

        if(isTrue){
            try {
                URL resource = getClass().getResource("/view/employee-forms/EmployeeDashboard.fxml");

                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Parent root = fxmlLoader.load();

                // Assuming you want to replace the current scene
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // Show an alert if the login information is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password. Please try again.");
            alert.showAndWait();
        }
    }

    public void onClickForgotlbl(MouseEvent mouseEvent) {
        try {
            URL resource = getClass().getResource("/view/forgot-password.fxml");
            //how to set the resourses url to last opened fxml file path

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();

            // Assuming you want to replace the current scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

//            stage.setOnHidden((WindowEvent event) -> count = 0);
//            stage.setOnCloseRequest((WindowEvent event) -> count = 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickContactAdmin(MouseEvent mouseEvent) {
    }

    public void btnIconOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/admin-forms/Admin-login.fxml");

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

    public void txtPasswordFieldOnAction(ActionEvent actionEvent) {
        btnLoginOnAction(actionEvent);
    }
}
