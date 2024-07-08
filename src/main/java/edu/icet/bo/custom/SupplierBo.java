package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.entity.SupplierEntity;
import edu.icet.model.supplier.Supplier;
import javafx.collections.ObservableList;

import java.util.List;

public interface SupplierBo extends SuperBo {

     List<String> id();
     boolean saveSupplier(Supplier dto);

     ObservableList<Supplier> getAllSuppliers();

     void getSelectSupplier();

     boolean updateSupplier(Supplier dto);

     boolean deleteSupplier(String id);
}
