package ru.saintcat.h2.dao;

import ru.saintcat.h2.dao.impl.ClientDAOImpl;
import ru.saintcat.h2.dao.impl.ContractDAOImpl;
import ru.saintcat.h2.dao.impl.ObjectTypeDAOImpl;
import ru.saintcat.h2.dao.impl.OwnerDAOImpl;
import ru.saintcat.h2.dao.impl.SaleObjectDAOImpl;
import ru.saintcat.h2.dao.impl.WorkerDAOImpl;

public class Factory {
	  
	  private static OwnerDAO ownerDAO = null;
	  private static SaleObjectDAO objectDAO = null;
	  private static ObjectTypeDAO objectTypeDAO = null;
	  private static ClientDAO clientDAO = null;
	  private static WorkerDAO workerDAO = null;
	  private static ContractDAO contractDAO = null;

	  private static Factory instance = null;

	  public static synchronized Factory getInstance(){
		    if (instance == null){
		      instance = new Factory();
		    }
		    return instance;
	  }

	  public OwnerDAO getOwnerDAO(){
		    if (ownerDAO == null){
		      ownerDAO = new OwnerDAOImpl();
		    }
		    return ownerDAO;
	  }	  
	  
	  public SaleObjectDAO getSaleObjectDAO(){
		    if (objectDAO == null){
		      objectDAO = new SaleObjectDAOImpl();
		    }
		    return objectDAO;
	  }	 
	  
	  public ObjectTypeDAO getObjectTypeDAO(){
		    if (objectTypeDAO == null){
		      objectTypeDAO = new ObjectTypeDAOImpl();
		    }
		    return objectTypeDAO;
	  }	 
	  
	  public ClientDAO getClientDAO(){
		    if (clientDAO == null){
		      clientDAO = new ClientDAOImpl();
		    }
		    return clientDAO;
	  }	 
	  
	  public WorkerDAO getWorkerDAO(){
		    if (workerDAO == null){
		      workerDAO = new WorkerDAOImpl();
		    }
		    return workerDAO;
	  }	
	  
	  public ContractDAO getContractDAO(){
		    if (contractDAO == null){
		      contractDAO = new ContractDAOImpl();
		    }
		    return contractDAO;
	  }	
}
