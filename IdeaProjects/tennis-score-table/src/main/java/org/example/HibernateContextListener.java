package org.example;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.service.OngoingMatchesService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
@WebListener
public class HibernateContextListener implements ServletContextListener {
    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        sce.getServletContext().setAttribute("sessionFactory", sessionFactory);
        sce.getServletContext().setAttribute("ongoingMatchesService", new OngoingMatchesService());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
