package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.Admin;
import edu.icet.model.order.Order;
import javafx.collections.ObservableList;

public interface AdminBo extends SuperBo {

    boolean save(Admin dto);
    ObservableList<Admin> getinfo();
}
