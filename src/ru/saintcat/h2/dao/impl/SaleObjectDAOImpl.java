package ru.saintcat.h2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Dialogs;

import org.hibernate.Session;

import ru.saintcat.h2.dao.SaleObjectDAO;
import ru.saintcat.h2.model.SaleObject;
import ru.saintcat.h2.util.HibernateUtil;


public class SaleObjectDAOImpl implements SaleObjectDAO {
	
	public void addSaleObject(SaleObject saleObject)  {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.save(saleObject);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public void updateSaleObject(SaleObject saleObject) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.update(saleObject);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public List<SaleObject> getSaleObjectByParam(String name, boolean onlyFree, Long lowP, Long highP) {
		    Session session = null;
		    List<SaleObject> objects = new ArrayList<SaleObject>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	String operation = "SELECT * FROM OBJECT WHERE (ID LIKE '%"+name+"%' OR PRICE LIKE '%"+name+"%' OR NAME LIKE '%"+name+"%')";
		    	if(onlyFree){
		    		operation+="AND (ID_CONTRACT IS NULL)";
		    	}
		    	operation+="AND (PRICE BETWEEN "+lowP+" AND "+highP+")";
		    	operation+="ORDER BY ID";
		    	objects =  session.createSQLQuery(operation).addEntity(SaleObject.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return objects;
	  }

	  public List<SaleObject> getAllSaleObjects() {
		    Session session = null;
		    List<SaleObject> saleObjects = new ArrayList<SaleObject>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	saleObjects = session.createCriteria(SaleObject.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return saleObjects;
	  }

	  public void deleteSaleObject(SaleObject saleObject) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.delete(saleObject);
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
