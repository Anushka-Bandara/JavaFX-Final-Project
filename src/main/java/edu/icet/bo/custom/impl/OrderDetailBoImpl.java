package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.OrderDetailBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.OrderDetailDao;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.model.orderDetail.OrderDetail;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {

    OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAIL);
    @Override
    public boolean save(OrderDetail dto) {
        return orderDetailDao.save(new ModelMapper().map(dto, OrderDetailEntity.class));
    }

    @Override
    public boolean update(OrderDetail dto) {
        return orderDetailDao.update(new ModelMapper().map(dto, OrderDetailEntity.class));
    }

    @Override
    public boolean delete(String id) {
        return orderDetailDao.delete(id);
    }

    @Override
    public ObservableList<OrderDetail> getAll() {
        List<OrderDetailEntity> list = orderDetailDao.getAll();
        ObservableList<OrderDetail> orderdetails = FXCollections.observableArrayList();

        for(OrderDetailEntity entity : list) {
            OrderDetail dto = new ModelMapper().map(entity, OrderDetail.class);
            orderdetails.add(dto);
        }

        return orderdetails;
    }
}
