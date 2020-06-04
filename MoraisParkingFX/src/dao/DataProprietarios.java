package dao;

import model.Proprietario;

import java.sql.*;
import java.util.ArrayList;

public class DataProprietarios implements DataClass {
    private static Connection conn;

    private static DataProprietarios instance = new DataProprietarios();

    private PreparedStatement dropOwnersTable;
    private PreparedStatement createOwnersTable;

    private PreparedStatement queryOwnerByNameStatement;
    private PreparedStatement insertOwnerStatement;
    private PreparedStatement deleteOwnerStatement;
    private PreparedStatement queryOwnerByIdStatement;

    private PreparedStatement queryOwnersPlatesStatement;
    private PreparedStatement deleteOwnersVehicleStatement;

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

    @Override
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

            queryOwnersPlatesStatement = conn.prepareStatement(Constants.QUERY_VEICULOS_BY_PROPRIETARIO_NAME);
            deleteOwnersVehicleStatement = conn.prepareStatement(Constants.DELETE_VEICULO);

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
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
            if (queryOwnerByIdStatement != null) {
                queryOwnerByIdStatement.close();
            }
            if (insertOwnerStatement != null) {
                insertOwnerStatement.close();
            }
            if (deleteOwnerStatement != null) {
                deleteOwnerStatement.close();
            }
            if (queryOwnersPlatesStatement != null) {
                queryOwnersPlatesStatement.close();
            }
            if (deleteOwnersVehicleStatement != null) {
                deleteOwnersVehicleStatement.close();
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

    public ArrayList<String> queryOwnersPlatesByName(String nome) {
        try {
            queryOwnersPlatesStatement.setString(1, nome);
            ResultSet results = queryOwnersPlatesStatement.executeQuery();
            ArrayList<String> placas = new ArrayList<>();
            while (results.next()) {
                placas.add(results.getString(1));
            }
            return placas;
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
        ArrayList<String> placas = queryOwnersPlatesByName(nome);
        boolean success = true;

        try {
            conn.setAutoCommit(false);

            for (String placa : placas) {
                deleteOwnersVehicleStatement.setString(1, placa);
                deleteOwnersVehicleStatement.execute();
            }
            deleteOwnerStatement.setString(1, nome);
            deleteOwnerStatement.execute();
            conn.commit();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            success = false;
            try {
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't rollback. SQLException: " + e2.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit. SQLException: " + e.getMessage());
                success = false;
            }
        }
        return success;
    }
}
