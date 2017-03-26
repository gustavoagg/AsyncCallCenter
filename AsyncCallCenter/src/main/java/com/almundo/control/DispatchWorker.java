package com.almundo.control;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

import com.almundo.bean.LineStatusBean;
import com.almundo.model.Call;
import com.almundo.model.Worker;

public class DispatchWorker implements Callable<DispatchWorker> {

	int id;
	Worker worker;
	Call call;
	boolean ready = true;
	boolean stop = false;
	Queue<Call> incomingCalls;
	Queue<Worker> freeWorkers;
	List<LineStatusBean> status;

	public DispatchWorker(int threadNumber, Queue<Call> incomingCalls, Queue<Worker> workerList,
			List<LineStatusBean> status) {
		this.id = threadNumber;
		this.incomingCalls = incomingCalls;
		this.freeWorkers = workerList;
		this.status = status;
	}

	@Override
	public DispatchWorker call() throws Exception {
		updateStatus();
		while (!stop) {
			if ((this.call = incomingCalls.poll()) != null) {
				process();
			} else {
				System.out.println("waiting... " + id);
				Thread.sleep(3000);
			}
		}
		// String message = String.format("worker: %s Termino su llamada",
		// employee.getName());
		return this;
	}



	private void process() throws InterruptedException {

		ready = false;
		String message = String.format("worker: %s esta atendiendo a la llamada: %s por la linea %s", "admin",
				call.getId(), id);
		System.out.println(message);
		Thread.sleep(call.getTime());
		message = String.format("Finalizada la llamada: %s por la linea %s", call.getId(), id);
		System.out.println(message);
		ready = true;

	}
	
	private void updateStatus() {
		LineStatusBean line = this.status.get(this.id);
		line.setId(String.valueOf(this.id));

		if (this.call != null) {
			line.setCall(call.getId());
			line.setTime(this.call.getTime());
		} else {
			line.setCall(0);
			line.setTime(0);
		}

		if (this.worker != null)
			line.setWorker(this.worker.getName());
		else
			line.setWorker("");

		line.setStatus(String.valueOf(ready));

	}

}
