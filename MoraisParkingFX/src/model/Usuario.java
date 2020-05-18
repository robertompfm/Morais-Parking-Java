package model;

public class Usuario {
    // ATTRIBUTES
    private int id;
    private String nome;
    private SetorUsuario setor;
    private String email;
    private String password;

    // CONSTRUCTORS
    public Usuario(String nome, SetorUsuario setor, String email, String password) {
        this(-1, nome, setor, email, password);
    }

    public Usuario(int id, String nome, SetorUsuario setor, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.setor = setor;
        this.email = email;
        this.password = password;
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

    public SetorUsuario getSetor() {
        return setor;
    }

    public void setSetor(SetorUsuario setor) {
        this.setor = setor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // TO STRING
    @Override
    public String toString() {
        return "id:" + id +
                ", nome: " + nome +
                ", setor: " + setor +
                ", email: " + email +
                ", password: " + password;
    }
}
