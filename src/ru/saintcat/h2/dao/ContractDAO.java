package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.Contract;

public interface ContractDAO {
	public void addContract(Contract contract);  
	public void updateContract(Contract contract);
	public List<Contract> getContractByName(String like);	
	public List<Contract> getAllContracts();	
	public void deleteContract(Contract contract);
}
;

