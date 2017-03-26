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

	public enum LineStatus {
		ON_HOLD("On Hold"), CONNECTING("Connecting"), READY("Ready"), CLOSED("Closed"), BUSY("Busy");

		public String text;

		LineStatus(String text) {
			this.text = text;
		}
	}

	public DispatchWorker(int threadNumber, ConcurrentLinkedQueue<Call> incomingCalls, PriorityBlockingQueue<Worker> workerList,
			List<LineStatusBean> status) {
		this.id = threadNumber;
		this.incomingCalls = incomingCalls;
		this.freeWorkers = workerList;
		this.status = status;
	}

	@Override
	public DispatchWorker call() throws Exception {
		updateStatus(LineStatus.CONNECTING.text);

		while (!stop) {
			// Go until app closes, or if its needed to pause the threads
			if ((this.call = incomingCalls.poll()) != null) {
				// if there is an incoming call
				updateStatus(LineStatus.ON_HOLD.text);
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
				Thread.sleep(1000);// delay time to retake another call if
									// necessary
			}
		}
		updateStatus(LineStatus.CLOSED.text);
		return this;
	}

	private void process() throws InterruptedException {

		// ready = false;
		updateStatus(LineStatus.BUSY.text);
		System.out.println("Linea " + id + " - Worker " + worker.getName() + ": atendiendo llamada:" + call.getId());
		Thread.sleep(call.getTime());
		System.out.println("Linea " + id + " - Llamada finalizada:" + call.getId());

	}

	private void disconnect() {
		this.call = null;
		workerToQueueList();
		// ready = true;
		updateStatus(LineStatus.READY.text);

	}

	private void workerToQueueList() {
		if (this.worker != null){
			this.worker.setCalls(Integer.valueOf(this.worker.getCalls().intValue()+1));
			freeWorkers.add(this.worker);			
		}
		this.worker = null;
	}

	private void updateStatus(String status) {
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

		line.setStatus(status);

	}

}
