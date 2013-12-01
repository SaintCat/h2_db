package ru.saintcat.h2.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Dialogs;

import javax.swing.JOptionPane;

import org.hibernate.Session;

import ru.saintcat.h2.dao.ObjectTypeDAO;
import ru.saintcat.h2.model.ObjectType;
import ru.saintcat.h2.util.HibernateUtil;


public class ObjectTypeDAOImpl implements ObjectTypeDAO {
	
	public void addObjectType(ObjectType objectType) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.save(objectType);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public void updateObjectType(ObjectType objectType) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.update(objectType);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }
	  
	  public ObjectType getObjectTypeById(Long id) {
          Session session = null;
          ObjectType stud = new ObjectType();
          try {
              session = HibernateUtil.getSessionFactory().openSession();
            
              stud = (ObjectType) session.load(ObjectType.class, id);
          } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
              if (session != null && session.isOpen()) {
                  session.close();
              }
          }
          return stud;
	  }	

	  public List<ObjectType> getObjectTypeByType(String like) {
		  	Session session = null;
		    List<ObjectType> objectTypes = new ArrayList<ObjectType>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	objectTypes =  session.createSQLQuery("SELECT * FROM OBJECTTYPE WHERE TYPE LIKE '%"+like+"%' OR ID LIKE '%"+like+"%'").addEntity(ObjectType.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return objectTypes;
	  }

	  public List<ObjectType> getAllObjectTypes() {
		    Session session = null;
		    List<ObjectType> objectTypes = new ArrayList<ObjectType>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	objectTypes = session.createSQLQuery("SELECT * FROM OBJECTTYPE ORDER BY ID").addEntity(ObjectType.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return objectTypes;
	  }

	  public void deleteObjectType(ObjectType objectType) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.delete(objectType);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }  
	  
}
