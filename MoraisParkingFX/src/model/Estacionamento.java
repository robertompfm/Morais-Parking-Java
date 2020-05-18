package model;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
	
	// ATRIBUTES
	private List<Proprietario> propCadastrados;
	private List<Veiculo> veiculosCadastrados;
	private List<AreaEstacionamento> areasEspeciais;
	private AreaEstacionamento areaComum;
	private int capacidadeMaxima;
	
	
	// CONSTRUCTOR
	public Estacionamento(int capacidade) {
		this.propCadastrados = new ArrayList<>();
		this.veiculosCadastrados = new ArrayList<>();
		this.areasEspeciais = new ArrayList<>();
		this.capacidadeMaxima = capacidade;
//		this.areaComum = new AreaEstacionamento("Comum", capacidade);
	}
	
	
	// FIND METHODS
//	private AreaEstacionamento identificarArea(String nome) {
//		for (AreaEstacionamento area : areasEspeciais) {
//			if (area.getNome().equalsIgnoreCase(nome)) {
//				return area;
//			}
//		}
//		return null;
//	}
	
	
	private Proprietario identificarProprietario(String nome) {
		for (Proprietario proprietario : propCadastrados) {
			if (proprietario.getNome().equalsIgnoreCase(nome)) {
				return proprietario;
			}
		}
		return null;
	}
	
	
	private Veiculo identificarVeiculo(String placa) {
		for (Veiculo veiculo : veiculosCadastrados) {
			if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
				return veiculo;
			}
		}
		return null;
	}
	
	
	// REGISTER METHODS
	private boolean cadastrarProprietario(String nome, long matricula, String curso) {
		Proprietario proprietario = identificarProprietario(nome);
		if (proprietario == null) {
			proprietario = new Proprietario(nome, matricula, curso);
			propCadastrados.add(proprietario);
			return true;
		}
		return false;
	}
	
	
//	public boolean cadastrarArea(String nome, int capacidade) {
//		AreaEstacionamento area = identificarArea(nome);
//		int vagasRestantes = areaComum.getCapacidade() - capacidade;
//		if (area == null && vagasRestantes >= 0) {
//			area = new AreaEstacionamento(nome, capacidade);
//			areasEspeciais.add(area);
//			areaComum.setCapacidade(vagasRestantes);
//			return true;
//		}
//		return false;
//	}
//
//
//	public boolean cadastrarVeiculo(String nomeProp, long matricula, String curso, String placa, String modelo, String cor, String nomeArea) {
//		Veiculo veiculo = identificarVeiculo(placa);
//		AreaEstacionamento area = identificarArea(nomeArea);
//		if (veiculo == null && area != null) {
//			cadastrarProprietario(nomeProp, matricula, curso);
//			Proprietario proprietario = identificarProprietario(nomeProp);
//			veiculo = new Veiculo(placa, proprietario, modelo, cor, nomeArea);
//			veiculosCadastrados.add(veiculo);
//			return true;
//		}
//		return false;
//	}
	
	
	// REMOVE METHODS
	private boolean excluirProprietario(String nome) {
		Proprietario proprietario = identificarProprietario(nome);
		if (proprietario != null) {
			propCadastrados.remove(proprietario);
			return true;
		}
		return false;
	}
	
	
//	public boolean excluirArea(String nome) {
//		AreaEstacionamento area = identificarArea(nome);
//		if (area != null) {
//			areasEspeciais.remove(area);
//			areaComum.setCapacidade(areaComum.getCapacidade() + area.getCapacidade());
//			return true;
//		}
//		return false;
//	}
	
	
	public boolean excluirVeiculo(String placa) {
		Veiculo veiculo = identificarVeiculo(placa);
		if (veiculo != null) {
			veiculosCadastrados.remove(veiculo);
			return true;
		}
		return false;
	}
	
	
	// MONITORING METHODS
//	public boolean autorizarEntrada(String placa, String modelo, String cor) {
//		Veiculo veiculo = identificarVeiculo(placa);
//		if (veiculo == null) {
//			veiculo = new Veiculo(placa, modelo, cor);
//		}
//		String nomeArea = veiculo.getArea();
//		AreaEstacionamento area = identificarArea(nomeArea);
//		if (area != null && area.getTaxaOcupacao() < 1) {
//			return area.adicionarVeiculo(veiculo);
//		} else {
//			return areaComum.adicionarVeiculo(veiculo);
//		}
//	}
	
	
//	public boolean autorizarSaida(String placa, String modelo, String cor) {
//		Veiculo veiculo = identificarVeiculo(placa);
//		if (veiculo == null) {
//			veiculo = new Veiculo(placa, modelo, cor);
//		}
//		if (areaComum.removerVeiculo(veiculo)) {
//			return true;
//		}
//		for (AreaEstacionamento area : areasEspeciais) {
//			if (area.removerVeiculo(veiculo)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//
//	// REPORT METHOD
//	public String getStatus() {
//		StringBuilder sb = new StringBuilder("MORAIS PARKING STATUS\n\n");
//		sb.append(areaComum + "\n");
//		int ocupacao = areaComum.getOcupacao();
//		for (AreaEstacionamento area : areasEspeciais) {
//			sb.append(area + "\n");
//			ocupacao += area.getOcupacao();
//		}
//		sb.append("\n");
//		double txOcupacao = ((double) ocupacao) / capacidadeMaxima;
//		String txOcupacaoPercent = String.format("%.2f", txOcupacao);
//		sb.append("Ocupacao total: "  + ocupacao + " / " + capacidadeMaxima
//				+ " (" + txOcupacaoPercent + "%)");
//		return sb.toString();
//	}

	
	// GETTERS AND SETTERS
	public List<Proprietario> getPropCadastrados() {
		return propCadastrados;
	}


	public void setPropCadastrados(List<Proprietario> propCadastrados) {
		this.propCadastrados = propCadastrados;
	}


	public List<Veiculo> getVeiculosCadastrados() {
		return veiculosCadastrados;
	}


	public void setVeiculosCadastrados(List<Veiculo> veiculosCadastrados) {
		this.veiculosCadastrados = veiculosCadastrados;
	}


//	public List<AreaEstacionamento> getAreasEspeciais() {
//		return areasEspeciais;
//	}


//	public void setAreasEspeciais(List<AreaEstacionamento> areasEspeciais) {
//		this.areasEspeciais = areasEspeciais;
//	}


//	public AreaEstacionamento getAreaComum() {
//		return areaComum;
//	}


//	public void setAreaComum(AreaEstacionamento areaComum) {
//		this.areaComum = areaComum;
//	}


	public int getCapacidadeMaxima() {
		return capacidadeMaxima;
	}


	public void setCapacidadeMaxima(int capacidadeMaxima) {
		this.capacidadeMaxima = capacidadeMaxima;
	}
	
	

	
	
	
	
}