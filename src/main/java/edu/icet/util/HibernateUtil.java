package edu.icet.util;

import edu.icet.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory session = createSession();

    private static SessionFactory createSession() {
        StandardServiceRegistry build = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metaData = new MetadataSources(build)
                .addAnnotatedClass(OrderEntity.class)
                .addAnnotatedClass(ProductEntity.class)
                .addAnnotatedClass(SupplierEntity.class)
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(OrderDetailEntity.class)
                .addAnnotatedClass(AdminEntity.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metaData.getSessionFactoryBuilder().build();

    }
    public static Session getSession(){
        return session.openSession();
    }
}
