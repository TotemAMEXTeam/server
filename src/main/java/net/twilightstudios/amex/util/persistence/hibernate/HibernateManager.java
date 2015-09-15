package net.twilightstudios.amex.util.persistence.hibernate;

import net.twilightstudios.amex.util.persistence.SessionManager;
import net.twilightstudios.amex.util.persistence.TransactionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateManager implements SessionManager, TransactionManager{
	
	private static HibernateManager instance;
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

    public HibernateManager() {
        try {
        	Configuration configuration = new Configuration().configure();
        	serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static HibernateManager getSessionManager() {
    	if (instance == null) {
    		instance = new HibernateManager();
    	}
    	return instance;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    public void beginTransactionOnCurrentSession() {
    	sessionFactory.getCurrentSession().beginTransaction();
    }
    
    public void commitOnCurrentSession() {
    	sessionFactory.getCurrentSession().getTransaction().commit();
    }
    
    public void rollbackOnCurrentSession() {
    	sessionFactory.getCurrentSession().getTransaction().rollback();
    }


}
