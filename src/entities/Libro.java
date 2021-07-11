package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Libro {

	private int idLibro;
	private String isbn;
	private String titulo;
	private String nroEdicion;
	private LocalDate fechaEdicion;
	private int maxDiasPrestamo;
	protected String dateFormat ="dd/MM/yyyy";
	
	public int getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNroEdicion() {
		return nroEdicion;
	}
	public void setNroEdicion(String nroEdicion) {
		this.nroEdicion = nroEdicion;
	}
	public LocalDate getFechaEdicion() {
		return fechaEdicion;
	}
	public void setFechaEdicion(LocalDate fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}
	public int getMaxDiasPrestamo() {
		return maxDiasPrestamo;
	}
	public void setMaxDiasPrestamo(int maxDiasPrestamo) {
		this.maxDiasPrestamo = maxDiasPrestamo;
	}
	@Override
	public String toString() {
		
		DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(dateFormat);
		
		return "Libro: " + idLibro + ". Titulo=" + titulo + ", isbn=" + isbn + ", nroEdicion=" + nroEdicion
				+ ", fechaEdicion=" + (fechaEdicion==null?null:fechaEdicion.format(dFormat)) + ", maxDiasPrestamo=" + maxDiasPrestamo + ".\n";
	}
	
	
	
}
