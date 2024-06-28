package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.order.Order;

public interface OrderBo extends SuperBo {
    boolean saveOrder(Order dto);
}
