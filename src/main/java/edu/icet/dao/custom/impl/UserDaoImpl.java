package edu.icet.dao.custom.impl;

import edu.icet.dao.custom.UserDao;
import edu.icet.entity.OrderEntity;
import edu.icet.entity.ProductEntity;
import edu.icet.entity.UserEntity;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<String> id() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String hql = "SELECT p.userId FROM UserEntity p ORDER BY p.userId DESC";
        Query<String> query = session.createQuery(hql, String.class);
        query.setMaxResults(1);
        List<String> resultList = query.list();
        return resultList;
    }

    @Override
    public boolean save(UserEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<UserEntity> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> all = cq.select(rootEntry);

        TypedQuery<UserEntity> allQuery = session.createQuery(all);
        //System.out.println(allQuery.getResultList());

        return allQuery.getResultList();
    }

    @Override
    public boolean update(UserEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.get(UserEntity.class,entity.getUserId());
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        UserEntity entity = session.get(UserEntity.class,id);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        UserEntity entity = session.get(UserEntity.class,userId);
        entity.setPassword(password);
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
