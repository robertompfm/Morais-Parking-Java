package model.objects;

import model.objects.TipoUsuario;

public class Usuario {
    private int id;
    private String username;
    private String password;
    private TipoUsuario tipo;

    public Usuario(int id, String username, String password, TipoUsuario tipo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "id:" + id +
                ", username: " + username +
                ", password: " + password +
                ", tipo: " + tipo;
    }
}
