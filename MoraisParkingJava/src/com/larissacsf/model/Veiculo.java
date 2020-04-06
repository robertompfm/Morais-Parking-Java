package com.larissacsf.model;

public class Veiculo {
  
  private String proprietario;
  private int matricula;
  private String curso;
  private String placa;
  private String modelo;


  public Veiculo(String proprietario, int matricula, String curso, String placa, String modelo) {
      this.proprietario = proprietario;
      this.matricula = matricula;
      this.curso = curso;
      this.placa = placa;
      this.modelo = modelo;
}


  public String getProprietario() {
      return proprietario;
  }
  public void setProprietario(String proprietario) {
      this.proprietario = proprietario;
  }
  public int getMatricula() {
      return matricula;
  }
  public void setMatricula(int matricula) {
      this.matricula = matricula;
  }
  public String getCurso() {
      return curso;
  }
  public void setModelo(String curso) {
      this.curso = curso;
  }
  public String getPlaca() {
      return placa;
  }
  public void setPlaca(String placa) {
      this.placa = placa;
  }
  public String getModelo() {
      return Modelo;
  }
  public void setModelo(String modelo) {
      this.modelo = modelo;
  }



  public String toString() {
      return  "Proprietario: " + this.proprietario + "\nMatrícula: " + this.matricula + "\nCurso: " + this.curso + "\nPlaca: " + this.placa
              + "\nModelo: " + this.modelo;
  }

}





