package com.almundo.model;

public class Director implements Employee{

	final int nivel = 3;
	
	private String name;
	
	private int totalLlamadas = 0;

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

	public void sumarLlamada() {
		totalLlamadas++;		
	}

	public void setTotalLlamadas(int totalLlamadas) {
		this.totalLlamadas = totalLlamadas;
	}

}
