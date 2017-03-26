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

@Controller
public class Dispatcher {

	@Autowired
	ThreadPoolTaskExecutor threadPool;

	ConcurrentLinkedQueue<Call> incomingCalls = new ConcurrentLinkedQueue<Call>();

	ConcurrentLinkedQueue<Worker> workerList = new ConcurrentLinkedQueue<Worker>();

	List<LineStatusBean> status = new ArrayList<LineStatusBean>();

	public void init() {

		for (int threadNumber = 0; threadNumber < 10; threadNumber++) {
			status.add(new LineStatusBean());
			DispatchWorker callableTask = new DispatchWorker(threadNumber, incomingCalls, workerList, status);
			callableTask.ready = true;
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

	public List<LineStatusBean> getStatus() {
		return this.status;
	}

}
