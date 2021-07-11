package entities;

public class PoliticaSancion {

	private int idPolitica;
	private int diasDesde;
	private int diasHasta;
	private int diasSancion;
	
	public int getIdPolitica() {
		return idPolitica;
	}
	public void setIdPolitica(int idPolitica) {
		this.idPolitica = idPolitica;
	}
	public int getDiasDesde() {
		return diasDesde;
	}
	public void setDiasDesde(int diasDesde) {
		this.diasDesde = diasDesde;
	}
	public int getDiasHasta() {
		return diasHasta;
	}
	public void setDiasHasta(int diasHasta) {
		this.diasHasta = diasHasta;
	}
	public int getDiasSancion() {
		return diasSancion;
	}
	public void setDiasSancion(int diasSancion) {
		this.diasSancion = diasSancion;
	}
	@Override
	public String toString() {
		return "ID Politica: " + idPolitica + ". Dias desde= " + diasDesde + ", dias hasta= " + diasHasta
				+ ", dias de sancion= " + diasSancion + ".\n";
	}
	
	
}
