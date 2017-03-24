package com.almundo.control;

import java.util.Queue;
import java.util.concurrent.Callable;

import com.almundo.model.Employee;
import com.almundo.model.Call;

public class CallableWorker implements Callable<CallableWorker> {

	String id;
	Employee employee;
	Call call;
	boolean ready;
	Queue<Call> incomingCalls;
	
	public CallableWorker(int id){
		ready = true; 
		
	}

	public CallableWorker(Employee empleado, Call llamada) {
		this.employee = empleado;
		this.call = llamada;

	}
	
	public CallableWorker(Employee empleado) {
		this.employee = empleado;

	}

	public CallableWorker(String id, Queue<Call> incomingCalls) {
		this.id = id;
		this.incomingCalls = incomingCalls;
	}

	@Override
	public CallableWorker call() throws Exception {
		process();
		String message = String.format("Empleado: %s Termino su llamada", employee.getName());
		return this;
	}

	private void process() throws InterruptedException {
		while(true){
			if((this.call = incomingCalls.poll())!=null){
				ready= false;
				System.out.println("hola");
				String message = String.format("Empleado: %s esta atendiendo a la llamada: %s por la linea %s", "admin", call.getId(),id);
				System.out.println(message);
				Thread.sleep(call.getTiempo());
				message = String.format("Finalizada la llamada: %s por la linea %s", call.getId(),id);				
				System.out.println(message);
				ready=true;
			}else{
				System.out.println("waiting... "+id);
				Thread.sleep(3000);
			}
		}
		
	}

}
