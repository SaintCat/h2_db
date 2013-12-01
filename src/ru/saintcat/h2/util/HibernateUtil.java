package ru.saintcat.h2.util;

 import org.hibernate.SessionFactory;
 import org.hibernate.cfg.Configuration;
 import org.hibernate.service.ServiceRegistry;
 import org.hibernate.service.ServiceRegistryBuilder;

 public class HibernateUtil {
     private static SessionFactory sessionFactory = null;
     private static ServiceRegistry serviceRegistry;  
      
     static {
         try {
             Configuration configuration = new Configuration();
             configuration.configure();
                 //session factory from hibernate.cfg.xml
             serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
             sessionFactory = configuration.buildSessionFactory(serviceRegistry);
         } catch (Exception e) {
               e.printStackTrace();
         }
     }

     public static SessionFactory getSessionFactory() {
         return sessionFactory;
     }
 }