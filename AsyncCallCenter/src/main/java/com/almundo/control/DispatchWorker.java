package com.almundo.control;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.almundo.bean.LineStatusBean;
import com.almundo.model.Call;
import com.almundo.model.Worker;

public class DispatchWorker implements Callable<DispatchWorker> {

	int id;
	Worker worker;
	Call call = null;
	boolean stop = false;

	ConcurrentLinkedQueue<Call> incomingCalls;
	PriorityBlockingQueue<Worker> freeWorkers;
	List<LineStatusBean> status;

	Dispatcher dispatcher;

	public enum LineStatus {
		ON_HOLD("On Hold"), CONNECTING("Connecting"), READY("Ready"), CLOSED("Closed"), BUSY("Busy");

		public String text;

		LineStatus(String text) {
			this.text = text;
		}
	}

	public DispatchWorker(int threadNumber, ConcurrentLinkedQueue<Call> incomingCalls,
			PriorityBlockingQueue<Worker> workerList, List<LineStatusBean> status) {
		this.id = threadNumber;
		this.incomingCalls = incomingCalls;
		this.freeWorkers = workerList;
		this.status = status;
	}

	public DispatchWorker(int threadNumber, Dispatcher dispatcher) {
		this.id = threadNumber;
		this.incomingCalls = dispatcher.incomingCalls;
		this.freeWorkers = dispatcher.workerList;
		this.status = dispatcher.statusBeans;
		this.dispatcher = dispatcher;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public DispatchWorker call() throws Exception {
		updateStatus(LineStatus.CONNECTING.text, null);

		while (!stop) {
			// Go until app closes, or if its needed to pause the threads
			if ((this.call = incomingCalls.poll()) != null) {
				// if there is an incoming call
				updateStatus(LineStatus.ON_HOLD.text, null);
				while (this.worker == null) {
					// wait until there is a free worker to take the call
					if ((this.worker = this.freeWorkers.poll()) != null) {
						// connect an process the call
						process();
					}
				}
				// when finish the call, disconnect and free the worker
				disconnect();

			} else {
				// delay time to re-take another call if necessary
				Thread.sleep(1000);

			}
		}
		updateStatus(LineStatus.CLOSED.text, null);
		return this;
	}

	/**
	 * Its call when the call is assigned to a worker, and it simulates the time of the call
	 * also, updates the line status each second
	 * @throws InterruptedException
	 */
	private void process() throws InterruptedException {
		System.out.println("Linea " + id + " - Worker " + worker.getName() + ": atendiendo llamada:" + call.getId());

		// Place here the code for processing a call, to simulate I stop the
		// thread
		for (int i = (int) (call.getTime() / 1000); i > 0; i--) {
			updateStatus(LineStatus.BUSY.text, i);
			Thread.sleep(1000);
		}
		System.out.println("Linea " + id + " - Llamada finalizada:" + call.getId());

	}

	/**
	 * It clears the call from the line, and reassigns the worker to the queue,
	 *  and updates the line status
	 */
	private void disconnect() {
		// IF a logger was needed, this would be a good place to log the call

		// remove assigned call
		this.call = null;

		workerToQueueList();
		updateStatus(LineStatus.READY.text, null);

	}

	/**
	 * Adds a new received call to the workers registry in the DB, 
	 * reassigns it the waiting queue and clears the worker from the line
	 */
	private void workerToQueueList() {
		if (this.worker != null) {
			// add one call, to the workers received calls
			this.worker.setCalls(Integer.valueOf(this.worker.getCalls().intValue() + 1));
			// update worker on database - 
			//IMPROVEMENT this could also be done when stopping
			// the process for better performance
			
			Worker temp = dispatcher.workerService.findById(this.worker.getId());
			temp.setCalls(this.worker.getCalls());
			dispatcher.workerService.saveWorker(temp);
			
			// add worker back to the working queue
			freeWorkers.add(this.worker);
		}
		this.worker = null;
	}

	/**
	 * @param status current status given by the LineStatus Enum
	 * @param time remaining call time
	 * 
	 * Updates the status of the connection between the call and a worker 
	 */
	private void updateStatus(String status, Integer time) {
		LineStatusBean line = this.status.get(this.id);
		line.setId(String.valueOf(this.id));

		if (this.call != null) {
			line.setCall(call.getId());
			if (time != null)
				line.setTime(time + " seg");
			else
				line.setTime(String.valueOf((this.call.getTime() / 1000)) + " seg");
		} else {
			line.setCall(0);
			line.setTime("");
		}

		if (this.worker != null)
			line.setWorker(this.worker.getName() + " - Atendidas: " + this.worker.getCalls());
		else
			line.setWorker("");

		line.setStatus(status);

	}

}
