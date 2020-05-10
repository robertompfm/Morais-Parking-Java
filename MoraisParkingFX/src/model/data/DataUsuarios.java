package model.data;

import model.Proprietario;
import model.TipoUsuario;
import model.Usuario;

import java.sql.*;

public class DataUsuarios {

    private static Connection conn;

    private static DataUsuarios instance = new model.data.DataUsuarios();

    private PreparedStatement dropUsuariosTable;
    private PreparedStatement createUsuariosTable;

    private PreparedStatement queryUserByUsername;
    private PreparedStatement insertUser;

    private Usuario currentUser;

    private DataUsuarios() {
        currentUser = null;
    }

    public static DataUsuarios getInstance() {
        return instance;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropUsuariosTable = conn.prepareStatement(Constants.DROP_USUARIOS_TABLE);
            createUsuariosTable = conn.prepareStatement(Constants.CREATE_USUARIOS_TABLE);
//            dropUsuariosTable.execute();
            createUsuariosTable.execute();


            insertUser = conn.prepareStatement(Constants.INSERT_USUARIO);
            queryUserByUsername = conn.prepareStatement(Constants.QUERY_USUARIO_BY_USERNAME);



            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropUsuariosTable != null) {
                dropUsuariosTable.close();
            }
            if (createUsuariosTable != null) {
                createUsuariosTable.close();
            }
            if (queryUserByUsername != null) {
                queryUserByUsername.close();
            }
            if (insertUser != null) {
                insertUser.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Usuario queryUsuarioByUsername(String username) {
        try {
            queryUserByUsername.setString(1, username);
            ResultSet results = queryUserByUsername.executeQuery();
            if (results.next()) {
                int currId = (results.getInt(1));
                String currUsername = (results.getString(2));
                String currPassword = (results.getString(3));
                TipoUsuario currTipo = TipoUsuario.valueOf(results.getString(4));
                Usuario usuario = new Usuario(currId, currUsername, currPassword, currTipo);
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertUsuario(String username, String password, String tipo) {
        try {
            insertUser.setString(1, username);
            insertUser.setString(2, password);
            insertUser.setString(3, tipo);
            insertUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

}
