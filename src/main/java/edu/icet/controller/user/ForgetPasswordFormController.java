package edu.icet.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ForgetPasswordFormController {
    public JFXTextField txtUserId;
    public JFXTextField txtEmail;
    public JFXButton btnForgot;
    public JFXTextField txtForgotCode;
    public JFXPasswordField txtnewPassword;
    public JFXPasswordField txtRnewPassword;
    public JFXButton btnConfirmPass;
    public JFXButton btnConfirmCode;
    public Pane pane2;
    public Pane pane1;
    public VBox box1;

    String userId;
    String code;
    public void btnForgotOnAction(ActionEvent actionEvent) {
        userId = txtUserId.getText();
        String email = txtEmail.getText();
        boolean istrue = UserController.getInstance().MailConfirm(userId,email);

        if (istrue){
            box1.setVisible(false);
            pane1.setVisible(true);
            code = EmailController.getInstance().sendMail(email);
        }else{
            showAlert(Alert.AlertType.WARNING,"Invalid Email","Enter Correct Email");
        }
    }

    public void btnConfirmCodeOnAction(ActionEvent actionEvent) {
        String enterdCode = txtForgotCode.getText();
        if(enterdCode.equals(code)){
            pane1.setVisible(false);
            pane2.setVisible(true);
        }else{
            showAlert(Alert.AlertType.WARNING,"Incorrect Code","Check your Email & Enter Correct Code");
        }
    }

    public void btnConfirmPassOnActtion(ActionEvent actionEvent) {
        String pass1 = txtnewPassword.getText();
        String pass2 = txtRnewPassword.getText();

        if(pass1.equals(pass2)){
            boolean isAdd = UserController.getInstance().passwordChange(userId,pass1);
            if(isAdd){
                showAlert(Alert.AlertType.CONFIRMATION,"Update Succesfully","Changed Your Password Login in Login Page");
            }
            btnConfirmPass.setDisable(true);
        }else{
            showAlert(Alert.AlertType.ERROR,"Password Mismatch","Your Password mismatched enter Correctly");
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
