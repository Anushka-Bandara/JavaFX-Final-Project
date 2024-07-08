package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.AdminDao;
import edu.icet.entity.AdminEntity;
import edu.icet.entity.OrderEntity;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.List;

public class AdminDaoImpl implements AdminDao
{
    @Override
    public List<String> id() {
        return null;
    }

    @Override
    public boolean save(AdminEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<AdminEntity> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AdminEntity> cq = cb.createQuery(AdminEntity.class);
        Root<AdminEntity> rootEntry = cq.from(AdminEntity.class);
        CriteriaQuery<AdminEntity> all = cq.select(rootEntry);
        TypedQuery<AdminEntity> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public boolean update(AdminEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
