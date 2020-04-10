package com.larissacsf.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Area {
    private String nome;
    private int capacidade;
    private ArrayList<Veiculo> veiculos;

    // CONSTRUCTOR
    public Area(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade > 0 ? capacidade : 1;
        this.veiculos = new ArrayList<>();
    }

    // METHODS
    public boolean adicionarVeiculo(Veiculo veiculo) {
        if ((this.veiculos.size() < capacidade) && (!this.veiculos.contains(veiculo))) {
            this.veiculos.add(veiculo);
            return true;
        }
        return false;
    }

    public boolean removerVeiculo(Veiculo veiculo) {
        if (this.veiculos.contains(veiculo)) {
            this.veiculos.remove(veiculo);
            return true;
        }
        return false;
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
    }

    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public double getTaxaOcupacao() {
        return ((double) this.veiculos.size()) / this.capacidade;
    }

    // TO STRING
    @Override
    public String toString() {
        DecimalFormat df2 = new DecimalFormat("#.##");
        double taxaOcupacao = getTaxaOcupacao() * 100;
        return "Area: " + this.nome +
                "; capacidade: " + this.capacidade +
                "; ocupacao: " + veiculos.size() +
                " (" + df2.format(taxaOcupacao) + "%)";
    }
}
