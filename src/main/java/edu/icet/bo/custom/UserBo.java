package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.user.User;
import javafx.collections.ObservableList;

import java.util.List;

public interface UserBo extends SuperBo {

    List<String> id();
     boolean saveUser(User dto);

     ObservableList<User> getAllUsers();

     boolean updateUser(String userId,String pass);

    boolean deleteUser(String id);

}
