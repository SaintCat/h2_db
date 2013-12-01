package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.SaleObject;

public interface SaleObjectDAO {
	public void addSaleObject(SaleObject object);  
	public void updateSaleObject(SaleObject object);
	public List<SaleObject> getSaleObjectByParam(String id, boolean onlyFree, Long v1, Long v2);	
	public List<SaleObject> getAllSaleObjects();	
	public void deleteSaleObject(SaleObject object);
}

