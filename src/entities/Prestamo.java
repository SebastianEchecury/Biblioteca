package entities;

import java.time.*;

public class Prestamo {

	private int idPrestamo;
	private LocalDate fechaPrestamo;
	private LocalTime horaPrestamo;
	private int dias;
	
	public int getIdPrestamo() {
		return idPrestamo;
	}
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public LocalTime getHoraPrestamo() {
		return horaPrestamo;
	}
	public void setHoraPrestamo(LocalTime horaPrestamo) {
		this.horaPrestamo = horaPrestamo;
	}
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
}
