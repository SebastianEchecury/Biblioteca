package entities;

import java.time.LocalDate;

public class Sancion {
	
	private int idSancion;
	private LocalDate fechaSancion;
	private int diasSancion;
	public int getIdSancion() {
		return idSancion;
	}
	public void setIdSancion(int idSancion) {
		this.idSancion = idSancion;
	}
	public LocalDate getFechaSancion() {
		return fechaSancion;
	}
	public void setFechaSancion(LocalDate fechaSancion) {
		this.fechaSancion = fechaSancion;
	}
	public int getDiasSancion() {
		return diasSancion;
	}
	public void setDiasSancion(int diasSancion) {
		this.diasSancion = diasSancion;
	}
	
	
}
