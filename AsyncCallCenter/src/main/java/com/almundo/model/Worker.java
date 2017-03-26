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

	private int calls;

	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalls() {
		return calls;
	}

	public void setCalls(int calls) {
		this.calls = calls;
	}

	public void sumarLlamada() {
		this.calls++;
	}

	public int compareTo(Worker e) {
		int result = this.level - e.getLevel();
		if (result == 0) {
			result = this.getCalls() - e.getCalls();
		}
		return result;
	}
}
