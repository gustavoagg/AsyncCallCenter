package com.almundo.model;

public class Operator implements Employee {

	final int nivel = 1;

	private String name;
	
	private int totalLlamadas;

	public Operator(String valueOf) {
		this.name = valueOf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalLlamadas() {
		return totalLlamadas;
	}

	public int getNivel() {
		return nivel;
	}

	public void setTotalLlamadas(int totalLlamadas) {
		this.totalLlamadas = totalLlamadas;
	}

	public void sumarLlamada() {
		totalLlamadas++;		
	}

}
