package com.almundo.bean;

public class LineStatusBean {

	String id;
	
	int call;
	
	String worker;
	
	String status;
	
	long time;

	public int getCall() {
		return call;
	}

	public void setCall(int call) {
		this.call = call;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
