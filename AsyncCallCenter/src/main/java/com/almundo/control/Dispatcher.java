package com.almundo.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.almundo.model.Call;
import com.almundo.model.Worker;

@Controller
public class Dispatcher {

	@Autowired
	ThreadPoolTaskExecutor threadPool;

	List<Future<DispatchWorker>> futureList;

	ConcurrentLinkedQueue<Call> incomingCalls = new ConcurrentLinkedQueue<Call>();

	ConcurrentLinkedQueue<Worker> workerList = new ConcurrentLinkedQueue<Worker>();

	public void init() {
		futureList = new ArrayList<>();

		for (int threadNumber = 1; threadNumber < 11; threadNumber++) {
			DispatchWorker callableTask = new DispatchWorker(String.valueOf(threadNumber), incomingCalls, workerList);
			callableTask.ready = true;
			Future<DispatchWorker> result = threadPool.submit(callableTask);
			futureList.add(result);
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

}
