package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Evento {

    // ATTRIBUTES
    private int id;
    private String nome;
    private LocalDate inicio;
    private LocalDate fim;

    private HashMap<AreaEstacionamento, Integer> vagasReservadas;

    // CONSTRUCTORS
    public Evento(String nome, LocalDate inicio, LocalDate fim) {
        this(-1, nome, inicio, fim);
    }

    public Evento(int id, String nome, LocalDate inicio, LocalDate fim) {
        this.id = id;
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;

        this.vagasReservadas = new HashMap<>();
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public HashMap<AreaEstacionamento, Integer> getVagasReservadas() {
        return vagasReservadas;
    }

    public void setVagasReservadas(HashMap<AreaEstacionamento, Integer> vagasReservadas) {
        this.vagasReservadas = vagasReservadas;
    }

    // TO STRING

    @Override
    public String toString() {
        return nome +
                " - Inicio: " + inicio.toString() +
                "; Fim: " + fim.toString();
    }
}
