package ru.saintcat.h2.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Dialogs;

import org.hibernate.Session;

import ru.saintcat.h2.dao.ContractDAO;
import ru.saintcat.h2.model.Contract;
import ru.saintcat.h2.util.HibernateUtil;


public class ContractDAOImpl implements ContractDAO {
	
	public void addContract(Contract contract){
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.save(contract);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public void updateContract(Contract contract){
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.update(contract);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  @SuppressWarnings("unchecked")
	public List<Contract> getContractByName(String name) {
		    Session session = null;
		    List<Contract> contracts = new ArrayList<Contract>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	contracts =  session.createSQLQuery("SELECT * FROM Contract WHERE iD LIKE '%"+name+"%' OR SUM LIKE '%"+name+"%' OR DATE LIKE '%"+name+"%'").addEntity(Contract.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return contracts;
	  }

	  @SuppressWarnings("unchecked")
	public List<Contract> getAllContracts() {
		    Session session = null;
		    List<Contract> contracts = new ArrayList<Contract>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	contracts = session.createCriteria(Contract.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return contracts;
	  }

	  public void deleteContract(Contract contract) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.delete(contract);
		    	session.getTransaction().commit();
		    }catch (Exception e) {
		    	Dialogs.showErrorDialog(null, "You cant delete "+contract+" from table.",e.getMessage().toUpperCase() );
		    }  finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }
  
	  
}

