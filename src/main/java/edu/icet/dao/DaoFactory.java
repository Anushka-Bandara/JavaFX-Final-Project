package edu.icet.dao;

import edu.icet.dao.custom.impl.*;
import edu.icet.util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance!=null?instance:(instance=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case ORDER:return (T)new OrderDaoImpl();
            case PRODUCT:return (T)new ProductDaoImpl();
            case SUPPLIER:return (T)new SupplierDaoImpl();
            case USER:return (T)new UserDaoImpl();
            case ORDERDETAIL:return (T)new OrderDetailDaoImpl();
            case ADMIN:return (T)new AdminDaoImpl();
        }
        return null;
    }
}
