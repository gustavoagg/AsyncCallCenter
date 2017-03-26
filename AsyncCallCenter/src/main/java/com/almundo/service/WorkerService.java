package com.almundo.service;

import java.util.List;

import com.almundo.model.Worker;

public interface WorkerService {

	Worker findById(long id);

	Worker findByName(String name);

	void saveWorker(Worker worker);

	void deleteWorkerById(long id);

	List<Worker> findAllWorkers();
}
