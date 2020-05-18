package model;


public class Veiculo {

	// ATTRIBUTES
	private int id;
	private String placa;
	private Proprietario proprietario;
	private String modelo;
	private String cor;
	private TipoVeiculo tipoVeiculo;
	
	
	// CONSTRUCTORS
	public Veiculo(String placa, TipoVeiculo tipoVeiculo) {
		this(placa, new Proprietario("DESCONHECIDO", -1l, "N/A"), "N/A", "N/A", tipoVeiculo);
	}

	public Veiculo(String placa, Proprietario proprietario, String modelo, String cor, TipoVeiculo tipoVeiculo) {
		this(-1, placa, proprietario, modelo, cor, tipoVeiculo);
	}

	public Veiculo(int id, String placa, Proprietario proprietario, String modelo, String cor, TipoVeiculo tipoVeiculo) {
		this.id = id;
		this.placa = placa;
		this.proprietario = proprietario;
		this.modelo = modelo;
		this.cor = cor;
		this.tipoVeiculo = tipoVeiculo;
	}


	// GETTERS AND SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
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
