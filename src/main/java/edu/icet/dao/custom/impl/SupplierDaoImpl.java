package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.SupplierDao;
import edu.icet.entity.ProductEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public List<String> id() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String hql = "SELECT p.supplierId FROM SupplierEntity p ORDER BY p.supplierId DESC";
        Query<String> query = session.createQuery(hql, String.class);
        query.setMaxResults(1);
        List<String> resultList = query.list();
        return resultList;
    }

    @Override
    public boolean save(SupplierEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<SupplierEntity> cq = cb.createQuery(SupplierEntity.class);
        Root<SupplierEntity> rootEntry = cq.from(SupplierEntity.class);
        CriteriaQuery<SupplierEntity> all = cq.select(rootEntry);

        TypedQuery<SupplierEntity> allQuery = session.createQuery(all);
        System.out.println(allQuery.getResultList());

        return allQuery.getResultList();
    }

    @Override
    public boolean update(SupplierEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.get(SupplierEntity.class,entity.getSupplierId());
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        SupplierEntity entity = session.get(SupplierEntity.class,id);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
