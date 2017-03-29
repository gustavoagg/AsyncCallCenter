package com.almundo;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.almundo.control.Dispatcher;
import com.almundo.controller.WebCallController;
import com.almundo.model.Call;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests {

	@Autowired
	Dispatcher dispatcher = new Dispatcher();
	int callCounter = 0;

	@Test(timeout = 20000)
	public void exactly10Calls() throws InterruptedException {
		dispatcher.init();

		Call call;

		for (int i = 0; i < 10; i++) {
			call = new Call();
			callCounter++;
			call.setId(callCounter);
			call.setTime(WebCallController.randTime());
			dispatcher.dispatchCall(call);
		}
		assertEquals(10, callCounter);
		ConcurrentLinkedQueue<Call> incoming = dispatcher.getIncomingCalls();
		
		while(true){
			if(incoming.size()==0){
			assertTrue(incoming.size()==0);			
			break;
			}
		}
	}

	
	@Test(timeout = 30000)
	public void moreThan10Calls() throws InterruptedException {
		dispatcher.init();

		Call call;

		for (int i = 0; i < 15; i++) {
			call = new Call();
			callCounter++;
			call.setId(callCounter);
			call.setTime(WebCallController.randTime());
			dispatcher.dispatchCall(call);
		}
		assertEquals(15, callCounter);
		ConcurrentLinkedQueue<Call> incoming = dispatcher.getIncomingCalls();
		
		while(true){
			if(incoming.size()==0){
			assertTrue(incoming.size()==0);
			break;
			}
		}
	}
	
	@Test(timeout = 20000)
	public void lessThan10Calls() throws InterruptedException {
		dispatcher.init();

		Call call;

		for (int i = 0; i < 5; i++) {
			call = new Call();
			callCounter++;
			call.setId(callCounter);
			call.setTime(WebCallController.randTime());
			dispatcher.dispatchCall(call);
		}
		assertEquals(5, callCounter);
		ConcurrentLinkedQueue<Call> incoming = dispatcher.getIncomingCalls();
		
		while(true){
			if(incoming.size()==0){
			assertTrue(incoming.size()==0);
			break;
			}
		}
	}

}
