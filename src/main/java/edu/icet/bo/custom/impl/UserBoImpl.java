package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.UserBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.UserDao;
import edu.icet.entity.SupplierEntity;
import edu.icet.entity.UserEntity;
import edu.icet.model.supplier.Supplier;
import edu.icet.model.user.User;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class UserBoImpl implements UserBo {

    private UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public List<String> id() {
        return userDao.id();
    }

    @Override
    public boolean saveUser(User dto) {
        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public ObservableList<User> getAllUsers() {
        List<UserEntity> list = userDao.getAll();
        ObservableList<User> users = FXCollections.observableArrayList();

        for(UserEntity entity : list){
            User dto = new ModelMapper().map(entity, User.class);
            users.add(dto);
        }
        return users;
    }


    @Override
    public boolean updateUser(String uId , String pass) {
        return userDao.updatePassword(uId, pass);
    }

    @Override
    public boolean deleteUser(String id) {
        return userDao.delete(id);
    }

}
