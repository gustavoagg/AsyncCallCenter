package com.almundo.repository;

import org.springframework.data.repository.CrudRepository;

import com.almundo.model.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Long> {

	Worker findByName(String name);

}
