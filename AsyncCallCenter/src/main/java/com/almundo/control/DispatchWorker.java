package com.almundo.control;

import java.util.Queue;
import java.util.concurrent.Callable;

import com.almundo.model.Call;
import com.almundo.model.Worker;

public class DispatchWorker implements Callable<DispatchWorker> {

	String id;
	Worker worker;
	Call call;
	boolean ready;
	Queue<Call> incomingCalls;
	Queue<Worker> freeWorkers;

	public DispatchWorker(int id) {
		ready = true;

	}

	public DispatchWorker(Worker worker, Call llamada) {
		this.worker = worker;
		this.call = llamada;

	}

	public DispatchWorker(Worker worker) {
		this.worker = worker;

	}

	public DispatchWorker(String id, Queue<Call> incomingCalls, Queue<Worker> workerList) {
		this.id = id;
		this.incomingCalls = incomingCalls;
		this.freeWorkers = workerList;
	}

	@Override
	public DispatchWorker call() throws Exception {
		process();
		// String message = String.format("worker: %s Termino su llamada",
		// employee.getName());
		return this;
	}

	private void process() throws InterruptedException {
		while (true) {
			if ((this.call = incomingCalls.poll()) != null) {
				ready = false;
				String message = String.format("worker: %s esta atendiendo a la llamada: %s por la linea %s", "admin",
						call.getId(), id);
				System.out.println(message);
				Thread.sleep(call.getTiempo());
				message = String.format("Finalizada la llamada: %s por la linea %s", call.getId(), id);
				System.out.println(message);
				ready = true;
			} else {
				// System.out.println("waiting... "+id);
				Thread.sleep(3000);
			}
		}

	}

}
