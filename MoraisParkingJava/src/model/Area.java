package model;

import java.util.ArrayList;

public class Area {
    //Atributos
    private String categorias;
    private ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private long capacidade;
    private String nome;
    
    //Construtor
    public Area(String nome, String categorias, long capacidade) {
        this.nome = nome;
        this.categorias = categorias;
        this.capacidade = capacidade;
    }
    
    //Metódos Especiais
    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public ArrayList<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }

    public void setListaVeiculo(ArrayList<Veiculo> listaVeiculo) {
        this.listaVeiculo = listaVeiculo;
    }
    public long getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(long capacidade) {
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //Métodos
    
    //1 - Entrada do veiculo
    public void entradaVeiculo(Veiculo veiculo){
        this.listaVeiculo.add(veiculo);
    }
    
    //2 - Saida do Veículo
    public void saidaVeiculo(Veiculo veiculo){
        this.listaVeiculo.remove(veiculo);   
    }

    @Override
    public String toString() {
        return "Informações da Área: " + "\nNome da área: " + this.nome +"\nCategoria: " + this.categorias + "\nCapacidade: " + this.capacidade;
    }
    
    
    
    
}
