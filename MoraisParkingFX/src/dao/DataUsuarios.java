package dao;

import model.SetorUsuario;
import model.Usuario;

import java.sql.*;

public class DataUsuarios implements DataClass {

    private static Connection conn;

    private static DataUsuarios instance = new DataUsuarios();

    private PreparedStatement dropUsuariosTable;
    private PreparedStatement createUsuariosTable;

    private PreparedStatement queryUserByEmail;
    private PreparedStatement insertUser;
    private PreparedStatement deleteUser;

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

    @Override
    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropUsuariosTable = conn.prepareStatement(Constants.DROP_USUARIOS_TABLE);
            createUsuariosTable = conn.prepareStatement(Constants.CREATE_USUARIOS_TABLE);

//            dropUsuariosTable.execute();
            createUsuariosTable.execute();


            insertUser = conn.prepareStatement(Constants.INSERT_USUARIO);
            queryUserByEmail = conn.prepareStatement(Constants.QUERY_USUARIO_BY_EMAIL);
            deleteUser = conn.prepareStatement(Constants.DELETE_USUARIO);


            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (dropUsuariosTable != null) {
                dropUsuariosTable.close();
            }
            if (createUsuariosTable != null) {
                createUsuariosTable.close();
            }
            if (queryUserByEmail != null) {
                queryUserByEmail.close();
            }
            if (insertUser != null) {
                insertUser.close();
            }
            if (deleteUser != null) {
                deleteUser.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Usuario queryUsuarioByEmail(String email) {
        try {
            queryUserByEmail.setString(1, email);
            ResultSet results = queryUserByEmail.executeQuery();
            if (results.next()) {
                int currId = (results.getInt(1));
                String currNome = results.getString(2);
                SetorUsuario currSetor = SetorUsuario.valueOf(results.getString(3));
                String currEmail = (results.getString(4));
                String currPassword = (results.getString(5));
                Usuario usuario = new Usuario(currId, currNome, currSetor, currEmail, currPassword);
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertUsuario(Usuario usuario) {
        try {
            insertUser.setString(1, usuario.getNome());
            insertUser.setString(2, usuario.getSetor().toString());
            insertUser.setString(3, usuario.getEmail());
            insertUser.setString(4, usuario.getPassword());
            insertUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUsuarioByEmail(String email) {
        try {
            deleteUser.setString(1, email);
            deleteUser.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

}
