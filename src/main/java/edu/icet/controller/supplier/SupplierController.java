package edu.icet.controller.supplier;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.SupplierBo;
import edu.icet.model.supplier.Supplier;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {
    private static SupplierController instance;

    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    private SupplierController(){}

    public static SupplierController getInstance(){
        if(instance==null){
            return instance = new SupplierController();
        }
        return instance;
    }

    public boolean add(Supplier supplier) {
        return supplierBo.saveSupplier(supplier);
    }

    public ObservableList<Supplier> getAll(){
       return supplierBo.getAllSuppliers();
    }

    public boolean update(Supplier supplier) {
        return supplierBo.updateSupplier(supplier);
    }

    public boolean delete(String Id){
        return supplierBo.deleteSupplier(Id);
    }

    public String genarateId() {

        String newId = "S001"; // Default ID
        List<String> list =  supplierBo.id();

        if (!list.isEmpty()) {
            String lastId = list.get(0);
            int idNumber = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format("S%03d", idNumber);
        }
        return newId;
    }
}
