package com.almundo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Worker implements Comparable<Worker> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;

	private Integer calls;

	private Integer level;
	
	private boolean active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCalls() {
		return calls;
	}

	public void setCalls(Integer calls) {
		this.calls = calls;
	}

	public void sumarLlamada() {
		this.calls++;
	}

	public int compareTo(Worker e) {
		int result = this.level.compareTo(e.getLevel());
		if (result == 0) {
			result = this.getCalls().compareTo(e.getCalls());
		}
		return result;
	}
	
	public String toString(){
		
		return id+ ": "+name.toString()+" - ("+calls.toString()+"-"+level.toString()+")";
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
