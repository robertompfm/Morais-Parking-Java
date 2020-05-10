package com.larissacsf.model;

import java.util.ArrayList;
import java.util.List;

public class AreaEstacionamento {
	
	// ATTRIBUTES
	private int id;
	private String nome;
	private int capacidade;
	private List<Veiculo> veiculos;
	private double taxaOcupacao;
	
	
	// CONSTRUCTOR
	public AreaEstacionamento(String nome, int capacidade) {
		this(-1, nome, capacidade);
	}

	public AreaEstacionamento(int id, String nome, int capacidade) {
		this.id = id;
		this.nome = nome;
		this.capacidade = capacidade;
		this.veiculos = new ArrayList<>();
		this.taxaOcupacao = 0.0;
	}

	
	// SPECIFIC METHODS
	public boolean adicionarVeiculo(Veiculo veiculo) {
		if (veiculo == null) {
			return false;
		}
		if (!veiculos.contains(veiculo) && veiculos.size() < capacidade) {
			veiculos.add(veiculo);
			atualizarTaxaOcupacao();
			return true;
		}
		return false;
	}
	
	
	public boolean removerVeiculo(Veiculo veiculo) {
		if (veiculo == null) {
			return false;
		}
		if (veiculos.contains(veiculo)) {
			veiculos.remove(veiculo);
			atualizarTaxaOcupacao();
			return true;
		}
		return false;
	}
	
	
	private void atualizarTaxaOcupacao() {
		this.taxaOcupacao = ((double) veiculos.size()) / capacidade; 
	}
	

	// GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getCapacidade() {
		return capacidade;
	}


	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
		atualizarTaxaOcupacao();
	}


	public List<Veiculo> getVeiculos() {
		return veiculos;
	}


	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
		atualizarTaxaOcupacao();
	}
	
	
	public int getOcupacao() {
		return veiculos.size();
	}


	public double getTaxaOcupacao() {
		return taxaOcupacao;
	}


	// TO STRING
	@Override
	public String toString() {
		String txOcupacaoPercent = String.format("%.2f", (taxaOcupacao * 100.0));
		return "Area: " + nome + "; ocupacao: " + veiculos.size() + " / " + capacidade 
				+ " (" + txOcupacaoPercent + "%)";
	}
		
	
}