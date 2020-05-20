package model;

public class Permissao {

    // ATTRIBUTES
    private int id;
    private Veiculo veiculo;
    private AreaEstacionamento area;

    // CONSTRUCTORS
    public Permissao(Veiculo veiculo, AreaEstacionamento area) {
        this(-1, veiculo, area);
    }

    public Permissao(int id, Veiculo veiculo, AreaEstacionamento area) {
        this.id = id;
        this.veiculo = veiculo;
        this.area = area;
    }

    // GETTERS AND SETETRS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public AreaEstacionamento getArea() {
        return area;
    }

    public void setArea(AreaEstacionamento area) {
        this.area = area;
    }


    // TOSTRING
    @Override
    public String toString() {
        return "Permissao: " +
                veiculo.getProprietario().getNome() +
                " - " + veiculo.getPlaca() +
                " - " + area.getNome();
    }
}
