package edu.icet.controller.admin;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.AdminBo;
import edu.icet.model.Admin;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    private static AdminController instance;

    private AdminBo adminBo = BoFactory.getInstance().getBo(BoType.ADMIN);

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
                adminBo.save(new Admin(id,password));
            }
        }
        return false;
    }


    private ObservableList<Admin> getAdminInfo(){
        return adminBo.getinfo();
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
