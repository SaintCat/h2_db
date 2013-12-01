package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.Owner;

public interface OwnerDAO {
	public void addOwner(Owner owner);  
	public void updateOwner(Owner owner);
	public List<Owner> getOwnerByName(String like);	
	public Owner getOwnerById(Long id);	
	public List<Owner> getAllOwners();	
	public void deleteOwner(Owner owner);
}
