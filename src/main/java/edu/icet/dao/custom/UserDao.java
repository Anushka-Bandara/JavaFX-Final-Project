package edu.icet.dao.custom;

import edu.icet.dao.CrudDao;
import edu.icet.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity> {
    boolean updatePassword(String userId, String Password);
}
