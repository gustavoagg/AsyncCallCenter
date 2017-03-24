package com.almundo.model;

public interface Employee {

	public int getNivel();

	public String getName();

	public void setName(String name);

	public int getTotalLlamadas();

	public void setTotalLlamadas(int llamadas);

	public void sumarLlamada();

}
