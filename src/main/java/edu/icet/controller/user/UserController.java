package edu.icet.controller.user;

import edu.icet.db.DBConnection;
import edu.icet.model.user.User;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserController {


    private static UserController instance;

    private UserController(){}

    public static UserController getInstance(){
        if(instance==null){
            return instance=new UserController();
        }
        else return instance;
    }

    public static User loginuser;

    private ObservableList<User> getAllUserinfo(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
            ObservableList<User> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new User(
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("role")
                        )
                );
            }
            return listTable;

        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<User> getAllUser(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
            ObservableList<User> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new User(
                                resultSet.getString("userId"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                "",
                                resultSet.getString("role")
                        )
                );
            }
            return listTable;

        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean ConfirmLoginInfo(String username,String password){
        ObservableList<User> list = getAllUserinfo();
        for (User user : list) {
            if (username.equals(user.getUserId()) && password.equals(user.getPassword())) {
                loginuser = user;
                return true;
            }
        }
        return false;
    }

    public boolean addUser(User user)  {
        try {
            return CrudUtil.execute(
                    "INSERT INTO user (userId, name, email, password ,role) VALUES (?, ?, ?, ?, ?)",
                    user.getUserId(),user.getName(),user.getEmail(),user.getPassword(),user.getRole());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String genarateUserId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT userId FROM user ORDER BY userId DESC LIMIT 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString("userId");
                int newId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("U%03d", newId);
            } else {
                return "U001"; // Default ID if no order exist
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "U001"; // Fallback ID
        }
    }

    public User getSelecttUser(String productId){
        ObservableList<User> list = getAllUserinfo();
        for(User user : list){
            if(productId.equals(user.getUserId())){
                return user;
            }
        }
        return null;
    }

    public boolean removeUser(String userId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM user WHERE userId = ?";
        return CrudUtil.execute(sql, userId);
    }
}
