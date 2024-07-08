package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.AdminBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.AdminDao;
import edu.icet.entity.AdminEntity;
import edu.icet.model.Admin;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class AdminBoImpl implements AdminBo {

    private AdminDao adminDao = DaoFactory.getInstance().getDao(DaoType.ADMIN);
    @Override
    public boolean save(Admin dto) {
        return adminDao.save(new ModelMapper().map(dto, AdminEntity.class));
    }

    @Override
    public ObservableList<Admin> getinfo() {
        List<AdminEntity> list = adminDao.getAll();
        ObservableList<Admin> admin = FXCollections.observableArrayList();

        for(AdminEntity entity : list) {
            Admin dto = new ModelMapper().map(entity, Admin.class);
            admin.add(dto);
        }
        return admin;
    }
}
