package ru.saintcat.h2.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Dialogs;

import javax.swing.JOptionPane;
import org.hibernate.Session;

import ru.saintcat.h2.dao.WorkerDAO;
import ru.saintcat.h2.model.Worker;
import ru.saintcat.h2.util.HibernateUtil;


public class WorkerDAOImpl implements WorkerDAO {
	
	public void addWorker(Worker worker) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.save(worker);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public void updateWorker(Worker worker) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.update(worker);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public List<Worker> getWorkerByName(String name) {
		    Session session = null;
		    List<Worker> worker = new ArrayList<Worker>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	worker =  session.createSQLQuery("SELECT * FROM Worker WHERE NAME LIKE '%"+name+"%' OR ID LIKE '%"+name+"%' OR TELEPHONE LIKE '%"+name+"%'").addEntity(Worker.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return worker;
	  }

	  @SuppressWarnings("unchecked")
	public List<Worker> getAllWorkers() {
		    Session session = null;
		    List<Worker> workers = new ArrayList<Worker>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	workers = session.createCriteria(Worker.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return workers;
	  }

	  public void deleteWorker(Worker worker) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.delete(worker);
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
