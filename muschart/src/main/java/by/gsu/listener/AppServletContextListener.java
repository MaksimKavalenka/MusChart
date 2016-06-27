package by.gsu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.gsu.hibernate.HibernateUtil;

@WebListener
public class AppServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        HibernateUtil.startup();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        HibernateUtil.shutdown();
    }

}
