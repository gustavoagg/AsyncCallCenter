package com.almundo.model;

public class Supervisor implements Employee {

	final int nivel = 2;

	private String name;
	
	private int totalLlamadas;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalLlamadas() {
		return totalLlamadas;
	}

	public void setTotalLlamadas(int totalLlamadas) {
		this.totalLlamadas = totalLlamadas;
	}

	public int getNivel() {
		return nivel;
	}

	public void sumarLlamada() {
		this.totalLlamadas++;
		
	}
}
