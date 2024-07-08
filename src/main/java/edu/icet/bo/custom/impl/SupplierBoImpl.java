package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.SupplierBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.SupplierDao;
import edu.icet.entity.ProductEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.model.Product;
import edu.icet.model.supplier.Supplier;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class SupplierBoImpl implements SupplierBo {

    private SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public List<String> id() {
        return supplierDao.id();
    }

    @Override
    public boolean saveSupplier(Supplier dto) {
        return supplierDao.save(new ModelMapper().map(dto, SupplierEntity.class));
    }

    @Override
    public ObservableList<Supplier> getAllSuppliers() {
        List<SupplierEntity> list = supplierDao.getAll();
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

        for(SupplierEntity entity : list){
            Supplier dto = new ModelMapper().map(entity, Supplier.class);
            suppliers.add(dto);
        }
        return suppliers;
    }

    @Override
    public void getSelectSupplier() {

    }

    @Override
    public boolean updateSupplier(Supplier dto) {
        return supplierDao.update(new ModelMapper().map(dto, SupplierEntity.class));
    }

    @Override
    public boolean deleteSupplier(String id) {
        return supplierDao.delete(id);
    }
}
