package model;

import java.util.ArrayList;

public class Estacionamento {
    //Atributos 
    private ArrayList<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
    private ArrayList<Area> listaArea = new ArrayList<Area>();
   

    //Construtor
     public Estacionamento() {
        
    }

    //Métodos Especiais
     public ArrayList<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }

    public void setListaVeiculo(ArrayList<Veiculo> listaVeiculo) {
        this.listaVeiculo = listaVeiculo;
    }

    public ArrayList<Area> getListaArea() {
        return listaArea;
    }

    public void setListaArea(ArrayList<Area> listaArea) {
        this.listaArea = listaArea;
    }

    //Métodos
    
    //1 - Cadastrar Veículo
    public void cadastrarVeiculo(String proprietario, String matricula, String curso, String placa, String modelo, String categoria) {
        Veiculo v = new Veiculo(proprietario, matricula, curso, placa, modelo, categoria);
        this.listaVeiculo.add(v); 
    }

    //2 - Remover Veículo
    public void removerVeiculo(Veiculo veiculo) {
        this.listaVeiculo.remove(veiculo);
    }

    //3 - Identificar Veículo
    public Veiculo identificacaoVeiculos(String placa ){
        for (Veiculo veiculo : this.listaVeiculo) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }
        return null;
    }
    
    //4 - Cadastrar Área
    public void cadastrarArea(String nome, String categorias, long capacidade){
        Area a = new Area(nome, categorias, capacidade);
            this.listaArea.add(a); 
    }
    
    // 5 - Exibir áreas
    public String exibirAreas(String nome){
        for(Area area : this.listaArea){
            if(area.getNome().equals(nome)){
                nome = area.toString();
                return nome;
            }
        }
        return "Área não foi cadastrada";  
    }
    
    // 6 - Registrar entrada do veículo na vaga
    public String registrarEntrada(String p){
        Veiculo veiculo = this.identificacaoVeiculos(p);
        for(Area area : this.listaArea){
            if(area.getCategorias().equals(veiculo.getCategoria())){
                area.entradaVeiculo(veiculo);
                return "Entrada Autorizada";
            }
        }  
        return "Entrada não autorizada";
    }
    
    // 7 - Registrar saída do veículo da vaga
    public String registrarSaida(String p){
        Veiculo veiculo = this.identificacaoVeiculos(p);
        for(Area area : this.listaArea){
            if(area.getCategorias().equals(veiculo.getCategoria())){
                area.saidaVeiculo(veiculo);
                return "Saída Autorizada";
            }
        }  
        return "Saída não autorizada"; 
    }
    
    // 8 - Registrar ocupação das vagas
    public String ocupacaoVagas(String nomeArea){
        for(Area area : this.listaArea){
            if(area.getNome().equals(nomeArea)){
                int quantidadeVagas = area.getListaVeiculo().size();
                int ocupacao = (int) (quantidadeVagas + area.getCapacidade());
                return "Ocupação total de vagas da área " + area.getNome() + " é " + ocupacao;
            }
            
        } 
        return null;
    }

   
  

    
    
    




















}
