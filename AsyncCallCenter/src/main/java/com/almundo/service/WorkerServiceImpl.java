package com.almundo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.model.Worker;
import com.almundo.repository.WorkerRepository;

@Service
public class WorkerServiceImpl implements WorkerService{
	@Autowired
	private WorkerRepository workerRepository;


	public List<Worker> findAllWorkers() {
		return (List<Worker>) workerRepository.findAll();
	}

	public Worker findById(long id) {
		return workerRepository.findOne(id);
	}

	public Worker findByName(String name) {
		return workerRepository.findByName(name);
	}

	public void saveWorker(Worker worker) {
		workerRepository.save(worker);
	}

	public void deleteWorkerById(long id) {
		workerRepository.delete(id);
	}
}
