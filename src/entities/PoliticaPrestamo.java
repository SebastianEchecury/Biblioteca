package entities;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class PoliticaPrestamo {

	private int idPolitica;
	private LocalDate fecha;
	private int cantMaxLibrosPend;
	protected String dateFormat ="dd/MM/yyyy";
	
	public int getIdPolitica() {
		return idPolitica;
	}
	public void setIdPolitica(int idPolitica) {
		this.idPolitica = idPolitica;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public int getCantMaxLibrosPend() {
		return cantMaxLibrosPend;
	}
	public void setCantMaxLibrosPend(int cantDiasMaxPendiente) {
		this.cantMaxLibrosPend = cantDiasMaxPendiente;
	}
	@Override
	public String toString() {
		
		DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(dateFormat);
		
		return "ID Politica: " + idPolitica + ". Fecha = " + (fecha==null?null:fecha.format(dFormat)) + ", cantidad de libros= " + cantMaxLibrosPend + ".\n";
	}
}
