package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.OrderDetailDao;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.entity.OrderEntity;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public List<String> id() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String hql = "SELECT p.orderId FROM OrderDetailEntity p ORDER BY p.orderId DESC";
        Query<String> query = session.createQuery(hql, String.class);
        query.setMaxResults(1);
        List<String> resultList = query.list();
        return resultList;
    }

    @Override
    public boolean save(OrderDetailEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetailEntity> cq = cb.createQuery(OrderDetailEntity.class);
        Root<OrderDetailEntity> rootEntry = cq.from(OrderDetailEntity.class);
        CriteriaQuery<OrderDetailEntity> all = cq.select(rootEntry);

        TypedQuery<OrderDetailEntity> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public boolean update(OrderDetailEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.get(OrderEntity.class,entity.getOrderId());
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        OrderDetailEntity entity = session.get(OrderDetailEntity.class,id);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}