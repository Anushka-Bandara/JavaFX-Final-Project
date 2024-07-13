package edu.icet.controller.user;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderDetailBo;
import edu.icet.bo.custom.UserBo;

import edu.icet.model.user.User;
import edu.icet.util.BoType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserController {


    private static UserController instance;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null) {
            return instance = new UserController();
        } else return instance;
    }

    public static User loginuser ;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    private ObservableList<User> getAllUserinfo() {
        return userBo.getAllUsers();
    }

    public ObservableList<User> getAllUser() {
        ObservableList<User> resultSet = getAllUserinfo();
        ObservableList<User> listTable = FXCollections.observableArrayList();
        resultSet.forEach(user -> {
            listTable.add(
                    new User(
                            user.getUserId(),
                            user.getName(),
                            user.getEmail(),
                            "",
                            user.getRole()
                    )
            );
        });
        return listTable;

    }

    public Boolean ConfirmLoginInfo(String username, String password) {
        try {
            ObservableList<User> list = getAllUserinfo();
            for (User user : list) {
                if (username.equals(user.getUserId()) && password.equals(user.getPassword())) {
                    loginuser = user;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addUser(User user) {
        return userBo.saveUser(user);
    }

    public String genarateUserId() {
        String newId = "U001"; // Default ID

        List<String> list = userBo.id();

        if (!list.isEmpty()) {
            String lastId = list.get(0);
            int idNumber = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format("U%03d", idNumber);
        }
        return newId;

    }

    public boolean MailConfirm (String UserID,String Email) {
        try {
            ObservableList<User> list = getAllUser();
            for (User user : list) {
                if (UserID.equals(user.getUserId()) && Email.equals(user.getEmail())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public User SelectUser(String uId){
        try {
            ObservableList<User> list = getAllUser();
            for (User user : list) {
                if (uId.equals(user.getUserId())){
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean passwordChange(String uId,String pass){

        return userBo.updateUser(uId,pass);
    }

    public boolean removeUser(String userId) {
        return userBo.deleteUser(userId);
    }
}
