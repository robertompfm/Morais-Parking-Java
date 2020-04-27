package com.larissacsf.model;


public class Veiculo {

	// ATTRIBUTES
	private String placa;
	private Proprietario proprietario;
	private String modelo;
	private String cor;
	private String area;
	
	
	// CONSTRUCTORS
	public Veiculo(String placa, String modelo, String cor) {
		this(placa, new Proprietario("Desconhecido", 0l, "N/A"), modelo, cor, "Comum");
	}
	

	public Veiculo(String placa, Proprietario proprietario, String modelo, String cor, String area) {
		this.placa = placa;
		this.proprietario = proprietario;
		this.modelo = modelo;
		this.cor = cor;
		this.area = area;
	}


	// GETTERS AND SETTERS
	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public Proprietario getProprietario() {
		return proprietario;
	}


	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}
	

	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getCor() {
		return cor;
	}


	public void setCor(String cor) {
		this.cor = cor;
	}


	// TO STRING
	@Override
	public String toString() {
		return "Veiculo: " + modelo + " " + cor + " (" + placa + ") ; Proprietario: " + proprietario;
	}


	// EQUALS
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Veiculo)) {
			return false;
		}
		Veiculo veiculo = (Veiculo) obj;
		return placa.equalsIgnoreCase(veiculo.getPlaca());
	}
	
	
	
	
	
	
	
}
