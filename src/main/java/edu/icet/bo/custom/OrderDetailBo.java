package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.orderDetail.OrderDetail;
import javafx.collections.ObservableList;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    boolean save(OrderDetail dto);
    boolean update(OrderDetail dto);
    boolean delete(String id);
    ObservableList<OrderDetail> getAll();
}
