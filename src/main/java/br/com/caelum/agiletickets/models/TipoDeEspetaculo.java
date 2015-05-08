package br.com.caelum.agiletickets.models;

public enum TipoDeEspetaculo {
	
	CINEMA(0.05, 0.1),
	SHOW(0.05, 0.1), 
	TEATRO(0, 0), 
	BALLET(0.5, 0.2), 
	ORQUESTRA(0.5, 0.2);
	
	private double percentualAumentoPorOcupacao;
	private double percentualAumento;

	private TipoDeEspetaculo(double percentualAumentoPorOcupacao, double percentualAumento) {
		this.percentualAumentoPorOcupacao = percentualAumentoPorOcupacao;
		this.percentualAumento = percentualAumento;
	}
	
	public double getPercentualAumentoPorOcupacao() {
		return percentualAumentoPorOcupacao;
	}
	
	public double getPercentualAumento() {
		return percentualAumento;
	}
}
