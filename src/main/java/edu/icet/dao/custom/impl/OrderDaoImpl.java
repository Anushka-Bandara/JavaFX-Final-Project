package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.OrderDao;
import edu.icet.entity.OrderEntity;

import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {


    @Override
    public List<String> id() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String hql = "SELECT p.orderId FROM OrderEntity p ORDER BY p.orderId DESC";
        Query<String> query = session.createQuery(hql, String.class);
        query.setMaxResults(1);
        List<String> resultList = query.list();
        return resultList;
    }

    @Override
    public boolean save(OrderEntity entity) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean load(){
        Session session = HibernateUtil.getSession();
        OrderEntity entity = session.load(OrderEntity.class,"O001");
        System.out.println(entity);
        return true;
    }

    public List<OrderEntity> getAll(){
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> rootEntry = cq.from(OrderEntity.class);
        CriteriaQuery<OrderEntity> all = cq.select(rootEntry);

        TypedQuery<OrderEntity> allQuery = session.createQuery(all);
        //System.out.println(allQuery.getResultList());

        return allQuery.getResultList();
    }

    public boolean update(OrderEntity entity){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.get(OrderEntity.class,entity.getOrderId());
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        //System.out.println(entity);
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        OrderEntity entity = session.get(OrderEntity.class,id);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
