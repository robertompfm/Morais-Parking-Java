package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ocorrencia {
    // ATTRIBUTES
    int id;
    TipoOcorrencia tipoOcorrencia;
    LocalDate data;
    ArrayList<Veiculo> veiculosEnvolvidos;
    String descricao;

    // CONSTRUCTORS
    public Ocorrencia(TipoOcorrencia tipoOcorrencia, LocalDate data, String observacoes) {
        this(-1, tipoOcorrencia, data, observacoes);
    }

    public Ocorrencia(int id, TipoOcorrencia tipoOcorrencia, LocalDate data, String observacoes) {
        this.id = id;
        this.tipoOcorrencia = tipoOcorrencia;
        this.data = data;
        this.descricao = observacoes;
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ArrayList<Veiculo> getVeiculosEnvolvidos() {
        return veiculosEnvolvidos;
    }

    public void setVeiculosEnvolvidos(ArrayList<Veiculo> veiculosEnvolvidos) {
        this.veiculosEnvolvidos = veiculosEnvolvidos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // TO STRING
    @Override
    public String toString() {
        StringBuilder veiculosStrBld = new StringBuilder();
        for (Veiculo veiculo : veiculosEnvolvidos) {
            veiculosStrBld.append("\n\t").append(veiculo.getPlaca()).append(" - ").append(veiculo.getProprietario().getNome());
        }
        return "Ocorrencia:" +
                "\nTipo: " + tipoOcorrencia +
                "\nData: " + data +
                "\nVeiculos envolvidos:" + veiculosStrBld.toString() +
                "\nDescricao: " + descricao;
    }
}
