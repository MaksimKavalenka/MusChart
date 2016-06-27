package by.gsu.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public abstract class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static void startup() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static void shutdown() {
        sessionFactory.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
