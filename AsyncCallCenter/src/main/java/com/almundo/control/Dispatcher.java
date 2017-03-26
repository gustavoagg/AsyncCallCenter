package com.almundo.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.almundo.bean.LineStatusBean;
import com.almundo.model.Call;
import com.almundo.model.Worker;
import com.almundo.service.WorkerService;

@Controller
public class Dispatcher {

	@Autowired
	ThreadPoolTaskExecutor threadPool;
	
	@Autowired
	WorkerService workerService;

	ConcurrentLinkedQueue<Call> incomingCalls = new ConcurrentLinkedQueue<Call>();

	ConcurrentLinkedQueue<Worker> workerList;

	List<LineStatusBean> statusBeans = new ArrayList<LineStatusBean>();

	public void init() {

		workerList = new ConcurrentLinkedQueue<Worker>(workerService.findAllWorkers());
		for (int threadNumber = 0; threadNumber < 10; threadNumber++) {
			statusBeans.add(new LineStatusBean());
			DispatchWorker callableTask = new DispatchWorker(threadNumber, incomingCalls, workerList, statusBeans);
			//callableTask.ready = true;
			// Future<DispatchWorker> result =
			threadPool.submit(callableTask);
			
			System.out.println("Created Line: " + threadNumber + " ..waiting for calls");
		}

	}

	public void dispatchCall(Call call) {
		System.out.println("New call incoming: " + call.getId() + " ..on hold");
		incomingCalls.add(call);
	}

	public synchronized Worker getNextInLine() {

		return workerList.poll();
	}

	public List<LineStatusBean> getStatusBeans() {
		return this.statusBeans;
	}

}
