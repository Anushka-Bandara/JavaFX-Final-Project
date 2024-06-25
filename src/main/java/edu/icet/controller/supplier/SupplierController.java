package edu.icet.controller.supplier;

import edu.icet.model.supplier.Supplier;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierController {
    private static SupplierController instance;

    private SupplierController(){}

    public static SupplierController getInstance(){
        if(instance==null){
            return instance = new SupplierController();
        }
        return instance;
    }

    public boolean add(Supplier supplier) {
        try {
            return CrudUtil.execute("INSERT INTO supplier (supplierId, name, company, email) VALUES (?, ?, ?, ?)",
                    supplier.getSupplierId(), supplier.getName(), supplier.getCompany(), supplier.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Supplier> getAll(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");
            ObservableList<Supplier> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new Supplier(
                                resultSet.getString("supplierId"),
                                resultSet.getString("name"),
                                resultSet.getString("company"),
                                resultSet.getString("email")
                        )
                );
            }
            return listTable;

        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Supplier supplier) {
        try {
            return CrudUtil.execute("UPDATE supplier SET name = ?, company = ?, email = ? WHERE supplierId = ?",
                    supplier.getName(), supplier.getCompany(), supplier.getEmail(), supplier.getSupplierId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String Id){
        try {
            return CrudUtil.execute("DELETE FROM supplier WHERE supplierId = ?", Id);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
