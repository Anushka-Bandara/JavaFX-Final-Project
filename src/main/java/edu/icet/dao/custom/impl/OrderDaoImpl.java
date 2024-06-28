package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.OrderDao;
import edu.icet.entity.OrderEntity;

import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

public class OrderDaoImpl implements OrderDao {


    @Override
    public boolean save(OrderEntity entity) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
