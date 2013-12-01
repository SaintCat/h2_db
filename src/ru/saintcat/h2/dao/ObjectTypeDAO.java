package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.ObjectType;

public interface ObjectTypeDAO {
	public void addObjectType(ObjectType object);  
	public void updateObjectType(ObjectType object);
	public List<ObjectType> getObjectTypeByType(String like);	
	public ObjectType getObjectTypeById(Long id);	
	public List<ObjectType> getAllObjectTypes();	
	public void deleteObjectType(ObjectType object);
}
