package model;

public class Veiculo extends Proprietario{
    //Atributos
    private String placa;
    private String modelo;
    private String categoria;

    //Construtor
    public Veiculo(String nome, String matricula, String curso, String placa, String modelo, String categoria) {
        super(nome, matricula, curso);
        this.placa = placa;
        this.modelo = modelo;
        this.categoria = categoria;
    }

    //Métodos Especiais
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    

    @Override
    public String toString() {
        
        return "Nome: " + this.getNome() + "\nMatricula: " + this.getMatricula() + "\nCurso: " + this.getCurso() + "\nPlaca: " + this.getPlaca() + "\nModelo: " + this.getModelo() + "\nCategoria: " + this.getCategoria();
        
    }
    
}
