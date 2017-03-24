package com.almundo.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.almundo.model.Call;
import com.almundo.model.Operator;

@Controller
public class Dispatcher {

    @Autowired	
    ThreadPoolTaskExecutor threadPool;
    ConcurrentLinkedQueue<Call> incomingCalls = new ConcurrentLinkedQueue<Call>();
    List<Future<CallableWorker>> futureList;
    
    public void init(){
    	futureList = new ArrayList<>();
		Call call = new Call();
		call.setId(1);
		call.setTiempo(5000);
    	incomingCalls.add(call);
        for(int threadNumber = 1; threadNumber < 11; threadNumber ++){
            CallableWorker callableTask = new CallableWorker(String.valueOf(threadNumber),incomingCalls);
            callableTask.ready = true;
            Future<CallableWorker> result = threadPool.submit(callableTask);
            futureList.add(result);
            System.out.println("Created Line: "+threadNumber+" ..waiting for calls");
        }
        
    }
    
    public void dispatchCall(Call call){
    	  System.out.println("New call incoming: "+call.getId()+" ..on hold");          
    	incomingCalls.add(call);
    }
    
    public String dispatchAll(){
        String msg = "";
        List<Future<CallableWorker>> futureList = new ArrayList<>();
        for(int threadNumber = 0; threadNumber < 12; threadNumber ++){
            CallableWorker callableTask = new CallableWorker(new Operator(String.valueOf(threadNumber)));
            Future<CallableWorker> result = threadPool.submit(callableTask);
            futureList.add(result);
        }
          
        for(Future<CallableWorker> future: futureList){
            try {
                msg += future.get().employee.getName() + "#####";
            } catch (Exception e){}
        }
          
        return msg;
    }
}
