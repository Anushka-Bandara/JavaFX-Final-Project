package edu.icet.controller.admin;

import edu.icet.model.Admin;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    private static AdminController instance;

    private AdminController(){}

    public static AdminController getInstance(){
        if(instance==null){
            return instance = new AdminController();
        }
        return instance;
    }

    Boolean addAdmin(String id ,String password) throws SQLException, ClassNotFoundException {
        if(id!=null && password!= null){
            ObservableList<Admin> list = getAdminInfo();
            if(list.isEmpty()){
                return CrudUtil.execute(
                        "INSERT INTO admin (userId, password) VALUES (?, ?)",
                        id,password);
            }

        }
        return false;
    }


    private ObservableList<Admin> getAdminInfo(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM admin");
            ObservableList<Admin> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new Admin(
                                resultSet.getString("userId"),
                                resultSet.getString("password")
                        )
                );
            }
            return listTable;

        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean ConfirmLoginInfo(String username,String password){
        ObservableList<Admin> list = getAdminInfo();
        for (Admin admin : list) {
            if (username.equals(admin.getUserId()) && password.equals(admin.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
