package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DataPermissoes {
    private static Connection conn;

    private static DataPermissoes instance = new DataPermissoes();

    private PreparedStatement dropPermissionsTable;
    private PreparedStatement createPermissionsTable;

    private PreparedStatement queryPermissionStatement;
    private PreparedStatement queryPermissionsByIdStatement;
    private PreparedStatement insertPermissionStatement;
    private PreparedStatement deletePermissionStatement;

    private PreparedStatement queryVehicleByIdStatement;
    private PreparedStatement queryAreasByIdStatement;

    private Permissao currentPermissao;

    private DataPermissoes() {
        currentPermissao = null;
    }

    public static DataPermissoes getInstance() {
        return instance;
    }

    public Permissao getCurrentProprietario() {
        return currentPermissao;
    }

    public void setCurrentPermissao(Permissao currentPermissao) {
        this.currentPermissao = currentPermissao;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropPermissionsTable = conn.prepareStatement(Constants.DROP_PERMISSOES_TABLE);
            createPermissionsTable = conn.prepareStatement(Constants.CREATE_PERMISSOES_TABLE);

//            dropPermissionsTable.execute();
            createPermissionsTable.execute();


            insertPermissionStatement = conn.prepareStatement(Constants.INSERT_PERMISSAO);
            queryPermissionStatement = conn.prepareStatement(Constants.QUERY_PERMISSAO);
            deletePermissionStatement = conn.prepareStatement(Constants.DELETE_PERMISSAO);
            queryPermissionsByIdStatement = conn.prepareStatement(Constants.QUERY_PERMISSOES_BY_VEICULO_ID);


            queryVehicleByIdStatement = conn.prepareStatement(Constants.QUERY_VEICULO_BY_ID);
            queryAreasByIdStatement = conn.prepareStatement(Constants.QUERY_AREA_BY_ID);

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropPermissionsTable != null) {
                dropPermissionsTable.close();
            }
            if (createPermissionsTable != null) {
                createPermissionsTable.close();
            }
            if (queryPermissionStatement != null) {
                queryPermissionStatement.close();
            }
            if (queryPermissionsByIdStatement != null) {
                queryPermissionsByIdStatement.close();
            }
            if (insertPermissionStatement != null) {
                insertPermissionStatement.close();
            }
            if (deletePermissionStatement != null) {
                deletePermissionStatement.close();
            }
            if (queryVehicleByIdStatement != null) {
                queryVehicleByIdStatement.close();
            }
            if (queryAreasByIdStatement != null) {
                queryAreasByIdStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public boolean hasPermission(int veiculo_id, int area_id) {
        try {
            queryPermissionStatement.setInt(1, veiculo_id);
            queryPermissionStatement.setInt(2, area_id);
            ResultSet results = queryPermissionStatement.executeQuery();
            if (results.next()) {

                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public Permissao queryPermission(int veiculo_id, int area_id) {
        try {
            queryPermissionStatement.setInt(1, veiculo_id);
            queryPermissionStatement.setInt(2, area_id);
            ResultSet results = queryPermissionStatement.executeQuery();
            if (results.next()) {
                int id = results.getInt(1);
                int propId = results.getInt(2);
                String prop = results.getString(3);
                int veicId = results.getInt(4);
                String placa = results.getString(5);
                int areaId = results.getInt(6);
                String areaNome = results.getString(7);

                Veiculo veiculo = queryVehicleById(veicId);
                Proprietario proprietario = veiculo.getProprietario();
                AreaEstacionamento area = queryAreaById(areaId);

                Permissao permissao = new Permissao(id, veiculo, area);
                return permissao;

            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

//    public ArrayList<String> queryOwnersPlatesByName(String nome) {
//        try {
//            queryOwnersPlatesStatement.setString(1, nome);
//            ResultSet results = queryOwnersPlatesStatement.executeQuery();
//            ArrayList<String> placas = new ArrayList<>();
//            while (results.next()) {
//                placas.add(results.getString(1));
//            }
//            return placas;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return null;
//        }
//    }

    public boolean insertPermission(Permissao permissao) {
        try {
            Veiculo veiculo = permissao.getVeiculo();
            Proprietario proprietario = veiculo.getProprietario();
            insertPermissionStatement.setInt(1, proprietario.getId());
            insertPermissionStatement.setString(2, proprietario.getNome());
            insertPermissionStatement.setInt(3, veiculo.getId());
            insertPermissionStatement.setString(4, veiculo.getPlaca());
            insertPermissionStatement.setInt(5, permissao.getArea().getId());
            insertPermissionStatement.setString(6, permissao.getArea().getNome());
            insertPermissionStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePermission(int veiculoId, int areaId) {
        try {
            deletePermissionStatement.setInt(1, veiculoId);
            deletePermissionStatement.setInt(2, areaId);
            deletePermissionStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    private AreaEstacionamento queryAreaById(int areaId) {
        try {
            queryAreasByIdStatement.setInt(1, areaId);
            ResultSet results = queryAreasByIdStatement.executeQuery();
            if (results.next()) {
                int currId = results.getInt(1);
                String currNome = results.getString(2);
                int currCapacidade = results.getInt(3);
                TipoVeiculo currTipoVeiculo = TipoVeiculo.valueOf(results.getString(4));
                boolean currEspecial = results.getBoolean(5);
                AreaEstacionamento area = new AreaEstacionamento(currId, currNome,
                        currCapacidade, currTipoVeiculo, currEspecial);
                return area;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    private Veiculo queryVehicleById(int vehicleId) {
        try {
            queryVehicleByIdStatement.setInt(1, vehicleId);
            ResultSet results = queryVehicleByIdStatement.executeQuery();
            if (results.next()) {
                int id = results.getInt(1);
                String placa = results.getString(2);
                int propId = results.getInt(3);
                String nome = results.getString(4);
                long matricula = results.getLong(5);
                String curso = results.getString(6);
                String modelo = results.getString(7);
                String cor = results.getString(8);
                TipoVeiculo tipo = TipoVeiculo.valueOf(results.getString(9));
                Proprietario proprietario = new Proprietario(propId, nome, matricula, curso);
                Veiculo veiculo = new Veiculo(id, placa, proprietario, modelo, cor, tipo);
                return veiculo;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
