package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.Client;

public interface ClientDAO {
	public void addClient(Client client);  
	public void updateClient(Client client);
	public List<Client> getClientByName(String like);	
	public List<Client> getAllClients();	
	public void deleteClient(Client client);
}
