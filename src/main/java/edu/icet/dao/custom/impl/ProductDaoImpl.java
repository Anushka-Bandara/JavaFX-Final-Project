package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.ProductDao;
import edu.icet.entity.OrderEntity;
import edu.icet.entity.ProductEntity;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<String> id() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String hql = "SELECT p.productId FROM ProductEntity p ORDER BY p.productId DESC";
        Query<String> query = session.createQuery(hql, String.class);
        query.setMaxResults(1);
        List<String> resultList = query.list();
        return resultList;
    }

    @Override
    public boolean save(ProductEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<ProductEntity> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> rootEntry = cq.from(ProductEntity.class);
        CriteriaQuery<ProductEntity> all = cq.select(rootEntry);

        TypedQuery<ProductEntity> allQuery = session.createQuery(all);
        //System.out.println(allQuery.getResultList());

        return allQuery.getResultList();
    }

    @Override
    public boolean update(ProductEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.get(ProductEntity.class,entity.getProductId());
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        ProductEntity entity = session.get(ProductEntity.class,id);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean updateStock(String productId, Integer qty) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        ProductEntity entity = session.get(ProductEntity.class,productId);
        entity.setQty(qty);
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
