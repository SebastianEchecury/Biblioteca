package entities;

public class Socio {

	private int idSocio;
	private String apellido;
	private String nombre;
	private String mail;
	private String domicilio;
	private String telefono;
	
	public int getIdSocio() {
		return idSocio;
	}
	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public String toString() {
		return "Socio: " + idSocio + ". Apellido= " + apellido + ", nombre= " + nombre + ", mail= " + mail
				+ ", domicilio=" + domicilio + ", telefono=" + telefono + "\n";
	}
	
	
	
}
