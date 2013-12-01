package ru.saintcat.h2.dao;

import java.util.List;

import ru.saintcat.h2.model.Worker;

public interface WorkerDAO {
	public void addWorker(Worker worker);  
	public void updateWorker(Worker worker);
	public List<Worker> getWorkerByName(String like);	
	public List<Worker> getAllWorkers();	
	public void deleteWorker(Worker worker);
}
