package com.larissacsf.model;

import java.util.ArrayList;

public class Estacionamento {
    private ArrayList<Veiculo> veiculosCadastrados;
    private ArrayList<Area> areasCadastradas;

    // CONSTRUCTOR
    public Estacionamento() {
        this.veiculosCadastrados = new ArrayList<>();
        this.areasCadastradas = new ArrayList<>();
        this.areasCadastradas.add(new Area("Comum", 500));
        this.areasCadastradas.add(new Area("Especial", 100));
        this.areasCadastradas.add(new Area("Bicicleta/Moto", 300));
        this.areasCadastradas.add(new Area("Onibus", 20));
    }

    // METODOS
    // 1 - Identificar veiculo
    public Veiculo identificarVeiculo(String placa) {
        for (Veiculo veiculo : this.veiculosCadastrados) {
            if (placa.equals(veiculo.getPlaca())) {
                return veiculo;
            }
        }
        return null;
    }
    
    public Area identificarArea(String nome) {
        for (Area area : this.areasCadastradas) {
            if (nome.equals(area.getNome())) {
                return area;
            }
        }
        return null;
    }

    // 2 - Cadastro de veiculo
    public boolean cadastrarVeiculo(String proprietario, long matricula, String curso, String placa, String modelo) {
        if (identificarVeiculo(placa) != null) {
            return false;
        }
        veiculosCadastrados.add(new Veiculo(proprietario, matricula, curso, placa, modelo));
        return true;
    }


}
