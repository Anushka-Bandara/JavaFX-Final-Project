package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.OrderBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.OrderDao;
import edu.icet.entity.OrderEntity;
import edu.icet.model.order.Order;
import edu.icet.util.DaoType;
import org.modelmapper.ModelMapper;

public class OrdreBoImpl implements OrderBo {

    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public boolean saveOrder(Order dto) {
        return orderDao.save(new ModelMapper().map(dto, OrderEntity.class));
    }
}
