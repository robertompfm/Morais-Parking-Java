package dao;

import model.Ocorrencia;

import java.util.ArrayList;

public class DataOcorrencias {

    // ATTRIBUTES
    private ArrayList<Ocorrencia> ocorrencias;
    private static DataOcorrencias instance = new DataOcorrencias();

    // CONSTRUCTOR
    private DataOcorrencias() {
        ocorrencias = new ArrayList<>();
    }

    // GETTERS AND SETTERS
    public ArrayList<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(ArrayList<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public static DataOcorrencias getInstance() {
        return instance;
    }

    // ADD OCORRENCIA
    public void addOcorrencia(Ocorrencia ocorrencia) {
        ocorrencias.add(ocorrencia);
    }


}
