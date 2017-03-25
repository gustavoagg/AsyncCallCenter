package com.almundo.model;

public class Worker implements Comparable<Worker> {

	private String name;

	private int totalLlamadas;

	private int nivel;

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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

	public void setTotalLlamadas(int totalLlamadas) {
		this.totalLlamadas = totalLlamadas;
	}

	public void sumarLlamada() {
		this.totalLlamadas++;
	}

	public int compareTo(Worker e) {
		int result = this.nivel - e.getNivel();
		if (result == 0) {
			result = this.getTotalLlamadas() - e.getTotalLlamadas();
		}
		return result;
	}
}
