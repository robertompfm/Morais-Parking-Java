package model;

public enum TipoOcorrencia {
    BATIDAS ("Batidas"),
    FURTO_OU_ASSALTO ("Furto ou assalto"),
    ESTACIONAMENTO_INDEVIDO ("Estacionamento indevido"),
    INUNDACAO ("Inundação"),
    DANO_AO_VEICULO ("Dano ao veículo"),
    OUTROS ("Outros");

    private final String tipo;

    private TipoOcorrencia(String tipo) {
        this.tipo = tipo;
    }

    public boolean equalsName(String otherName) {
        return this.tipo.equals(otherName);
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }

    public static TipoOcorrencia getEnum(String value) {
        for (TipoOcorrencia o : values()) {
            if (o.getTipo().equalsIgnoreCase(value)) return o;
        }
        throw new IllegalArgumentException();
    }
}
