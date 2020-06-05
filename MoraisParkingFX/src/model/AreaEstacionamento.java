package model;

import java.util.ArrayList;
import java.util.List;

public class AreaEstacionamento {
	
	// ATTRIBUTES
	private int id;
	private String nome;
	private int capacidade;
	private TipoVeiculo tipoVeiculo;
	private boolean especial;
	
	
	// CONSTRUCTOR
	public AreaEstacionamento(String nome, int capacidade, TipoVeiculo tipoVeiculo, boolean especial) {
		this(-1, nome, capacidade, tipoVeiculo, especial);
	}

	public AreaEstacionamento(int id, String nome, int capacidade, TipoVeiculo tipoVeiculo, boolean especial) {
		this.id = id;
		this.nome = nome;
		this.capacidade = capacidade;
		this.tipoVeiculo = tipoVeiculo;
		this.especial = especial;
	}

	// GETTERS AND SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
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
		if (!(obj instanceof AreaEstacionamento)) {
			return false;
		}
		AreaEstacionamento area = (AreaEstacionamento) obj;
		return nome.equalsIgnoreCase(area.getNome());
	}

	@Override
	public int hashCode() {
		return this.nome.hashCode();
	}

	// TO STRING
	@Override
	public String toString() {
		return "Nome: " + nome +
				"; Capacidade: " + capacidade +
				"; tipo de veiculo: " + tipoVeiculo +
				"; especial: " + especial;
	}
		
	
}