package edu.icet.dao;

import edu.icet.dao.custom.impl.OrderDaoImpl;
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
        }
        return null;
    }
}
