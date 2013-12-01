package ru.saintcat.h2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Dialogs;

import org.hibernate.Session;

import ru.saintcat.h2.dao.ClientDAO;
import ru.saintcat.h2.model.Client;
import ru.saintcat.h2.util.HibernateUtil;


public class ClientDAOImpl implements ClientDAO {
	
	public void addClient(Client client){
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.save(client);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  public void updateClient(Client client) {
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.update(client);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }

	  
	public List<Client> getClientByName(String name) {
		    Session session = null;
		    List<Client> clients = new ArrayList<Client>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	clients =  session.createSQLQuery("SELECT * FROM Client WHERE NAME LIKE '%"+name+"%' OR ID LIKE '%"+name+"%' OR TELEPHONE LIKE '%"+name+"%'").addEntity(Client.class).<Client>list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return clients;
	  }

	  @SuppressWarnings("unchecked")
	public List<Client> getAllClients(){
		    Session session = null;
		    List<Client> clients = new ArrayList<Client>();
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	clients = session.createCriteria(Client.class).list();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
		    return clients;
	  }

	  public void deleteClient(Client client){
		    Session session = null;
		    try {
		    	session = HibernateUtil.getSessionFactory().openSession();
		    	session.beginTransaction();
		    	session.delete(client);
		    	session.getTransaction().commit();
		    } catch (Exception e) {
		    	Dialogs.showErrorDialog(null, "You cant delete "+client+" from table.",e.getMessage().toUpperCase() );
		    } finally {
		    	if (session != null && session.isOpen()) {
		    		session.close();
		    	}
		    }
	  }
  
	  
}
