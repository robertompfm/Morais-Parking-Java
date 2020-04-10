package com.larissacsf.model;

public class Veiculo {
  
  private String proprietario;
  private long matricula;
  private String curso;
  private String placa;
  private String modelo;

  public Veiculo(String placa, String modelo) {
      this("Desconhecido", 0L, "Desconhecido", placa, modelo);
  }

  public Veiculo(String proprietario, long matricula, String curso, String placa, String modelo) {
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
  public long getMatricula() {
      return matricula;
  }
  public void setMatricula(long matricula) {
      this.matricula = matricula;
  }
  public String getCurso() {
      return curso;
  }
  public void setCurso(String curso) {
      this.curso = curso;
  }
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



  public String toString() {
      return  "Proprietario: " + this.proprietario + "\nMatrícula: " + this.matricula + "\nCurso: " + this.curso + "\nPlaca: " + this.placa
              + "\nModelo: " + this.modelo;
  }

}





