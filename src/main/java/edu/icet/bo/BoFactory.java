package edu.icet.bo;

import edu.icet.bo.custom.impl.*;
import edu.icet.util.BoType;

public class BoFactory implements SuperBo{

    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        if(instance==null){
            return instance= new BoFactory();
        }
        return instance;
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case ORDER:return (T) new OrdreBoImpl();
            case PRODUCT:return (T) new ProductBoImpl();
            case SUPPLIER:return (T) new SupplierBoImpl();
            case USER:return (T) new UserBoImpl();
            case ORDERDETAIL:return (T) new OrderDetailBoImpl();
            case ADMIN:return (T) new AdminBoImpl();
        }
        System.out.println("hi");
        return null;

    }
}
