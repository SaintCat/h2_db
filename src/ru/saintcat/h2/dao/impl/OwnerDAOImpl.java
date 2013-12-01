package ru.saintcat.h2.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Dialogs;

import javax.swing.JOptionPane;

import org.hibernate.Session;

import ru.saintcat.h2.dao.OwnerDAO;
import ru.saintcat.h2.model.Owner;
import ru.saintcat.h2.util.HibernateUtil;

public class OwnerDAOImpl implements OwnerDAO {

	public void addOwner(Owner owner)  {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(owner);
			session.getTransaction().commit();
		} catch (Exception e) {
	    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
	    } finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void updateOwner(Owner owner)  {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(owner);
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
	public List<Owner> getOwnerByName(String name)  {
		Session session = null;
		List<Owner> owner = new ArrayList<Owner>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			owner = session
					.createSQLQuery(
							"SELECT * FROM OWNER WHERE NAME LIKE '%" + name
									+ "%' OR EMAIL LIKE '%" + name
									+ "%' OR ID LIKE '%" + name + "%'")
					.addEntity(Owner.class).list();
		} catch (Exception e) {
	    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
	    } finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return owner;
	}

	public Owner getOwnerById(Long id)  {
		Session session = null;
		Owner owner = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			owner = (Owner) session.load(Owner.class, id);
		} catch (Exception e) {
	    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
	    } finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return owner;
	}

	@SuppressWarnings("unchecked")
	public List<Owner> getAllOwners()  {
		Session session = null;
		List<Owner> owners = new ArrayList<Owner>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			owners = session.createSQLQuery("SELECT * FROM OWNER ORDER BY ID")
					.addEntity(Owner.class).list();
		} catch (Exception e) {
	    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
	    } finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return owners;
	}

	public void deleteOwner(Owner owner)  {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(owner);
			session.getTransaction().commit();
		} catch (Exception e) {
	    	Dialogs.showErrorDialog(null, e.getMessage(), "Ошибка I/O");
	    }finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
