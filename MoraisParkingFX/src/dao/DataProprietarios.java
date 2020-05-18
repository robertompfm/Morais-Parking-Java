package dao;

import model.Proprietario;
import model.SetorUsuario;
import model.Usuario;

import java.sql.*;

public class DataProprietarios {
    private static Connection conn;

    private static DataProprietarios instance = new DataProprietarios();

    private PreparedStatement dropOwnersTable;
    private PreparedStatement createOwnersTable;

    private PreparedStatement queryOwnerByNameStatement;
    private PreparedStatement insertOwnerStatement;
    private PreparedStatement deleteOwnerStatement;
    private PreparedStatement queryOwnerByIdStatement;

    private Proprietario currentProprietario;

    private DataProprietarios() {
        currentProprietario = null;
    }

    public static DataProprietarios getInstance() {
        return instance;
    }

    public Proprietario getCurrentProprietario() {
        return currentProprietario;
    }

    public void setCurrentProprietario(Proprietario currentProprietario) {
        this.currentProprietario = currentProprietario;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropOwnersTable = conn.prepareStatement(Constants.DROP_PROPRIETARIOS_TABLE);
            createOwnersTable = conn.prepareStatement(Constants.CREATE_PROPRIETARIOS_TABLE);

//            dropOwnersTable.execute();
            createOwnersTable.execute();


            insertOwnerStatement = conn.prepareStatement(Constants.INSERT_PROPRIETARIO);
            queryOwnerByNameStatement = conn.prepareStatement(Constants.QUERY_PROPRIETARIO_BY_NAME);
            deleteOwnerStatement = conn.prepareStatement(Constants.DELETE_PROPRIETARIO);
            queryOwnerByIdStatement = conn.prepareStatement(Constants.QUERY_PROPRIETARIO_BY_ID);

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropOwnersTable != null) {
                dropOwnersTable.close();
            }
            if (createOwnersTable != null) {
                createOwnersTable.close();
            }
            if (queryOwnerByNameStatement != null) {
                queryOwnerByNameStatement.close();
            }
            if (insertOwnerStatement != null) {
                insertOwnerStatement.close();
            }
            if (deleteOwnerStatement != null) {
                deleteOwnerStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Proprietario queryOwnerByName(String nome) {
        try {
            queryOwnerByNameStatement.setString(1, nome);
            ResultSet results = queryOwnerByNameStatement.executeQuery();
            if (results.next()) {
                int currId = results.getInt(1);
                String currNome = results.getString(2);
                long currMatricula = results.getLong(3);
                String currCurso = results.getString(4);
                Proprietario proprietario = new Proprietario(currId, currNome, currMatricula, currCurso);
                return proprietario;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertOwner(Proprietario proprietario) {
        try {
            insertOwnerStatement.setString(1, proprietario.getNome());
            insertOwnerStatement.setLong(2, proprietario.getMatricula());
            insertOwnerStatement.setString(3, proprietario.getCurso());
            insertOwnerStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOwner(String nome) {
        try {
            deleteOwnerStatement.setString(1, nome);
            deleteOwnerStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
