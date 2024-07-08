package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.OrderBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.OrderDao;
import edu.icet.entity.OrderEntity;
import edu.icet.model.order.Order;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class OrdreBoImpl implements OrderBo {

    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public List<String> id() {
        return orderDao.id();
    }

    @Override
    public boolean saveOrder(Order dto) {
        return orderDao.save(new ModelMapper().map(dto, OrderEntity.class));
    }

    @Override
    public ObservableList<Order> getOrders() {
        List<OrderEntity> list = orderDao.getAll();
        ObservableList<Order> orders = FXCollections.observableArrayList();

        for(OrderEntity entity : list) {
            Order dto = new ModelMapper().map(entity, Order.class);
            orders.add(dto);
        }

        return orders;
    }
}
