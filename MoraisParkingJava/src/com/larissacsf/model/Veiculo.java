package com.larissacsf.model;

public class Veiculo {
  
  private String proprietario;
  private long matricula;
  private String curso;
  private String placa;
  private Area area;

  public Veiculo(String placa, Area area) {
      this("Desconhecido", 0L, "Desconhecido", placa, area);
  }

  public Veiculo(String proprietario, long matricula, String curso, String placa, Area area) {
      this.proprietario = proprietario;
      this.matricula = matricula;
      this.curso = curso;
      this.placa = placa;
      this.area = area;
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
  public Area getArea() {
      return area;
  }
  public void setArea(Area area) {
      this.area = area;
  }



  public String toString() {
      return  "Proprietario: " + this.proprietario + "\nMatrícula: " + this.matricula + "\nCurso: " + this.curso + "\nPlaca: " + this.placa
              + "\nArea: " + this.area.getNome();
  }

}





