package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.entity.OrderEntity;
import edu.icet.model.order.Order;
import javafx.collections.ObservableList;

import java.util.List;

public interface OrderBo extends SuperBo {

    List<String> id();
    boolean saveOrder(Order dto);
    ObservableList<Order> getOrders();
}
