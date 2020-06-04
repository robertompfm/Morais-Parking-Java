package dao;

import model.Proprietario;
import model.TipoVeiculo;
import model.Veiculo;

import java.sql.*;

public class DataVeiculos implements DataClass {
    private static Connection conn;

    private static DataVeiculos instance = new DataVeiculos();

    private PreparedStatement dropVehicleTable;
    private PreparedStatement createVehicleTable;

    private PreparedStatement queryVehicleByPlateStatement;
    private PreparedStatement queryVehicleByIdStatement;
    private PreparedStatement insertVehicleStatement;
    private PreparedStatement deleteVehicleStatement;

    private PreparedStatement queryOwnerByIdStatement;

    private Veiculo currentVeiculo;

    private DataVeiculos() {
        currentVeiculo = null;
    }

    public static DataVeiculos getInstance() {
        return instance;
    }

    public Veiculo getCurrentVeiculo() {
        return currentVeiculo;
    }

    public void setCurrentVeiculo(Veiculo currentVeiculo) {
        this.currentVeiculo = currentVeiculo;
    }

    @Override
    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropVehicleTable = conn.prepareStatement(Constants.DROP_VEICULOS_TABLE);
            createVehicleTable = conn.prepareStatement(Constants.CREATE_VEICULOS_TABLE);

//            dropVehicleTable.execute();
            createVehicleTable.execute();


            insertVehicleStatement = conn.prepareStatement(Constants.INSERT_VEICULO);
            queryVehicleByPlateStatement = conn.prepareStatement(Constants.QUERY_VEICULO_BY_PLACA);
            queryVehicleByIdStatement = conn.prepareStatement(Constants.QUERY_VEICULO_BY_ID);
            deleteVehicleStatement = conn.prepareStatement(Constants.DELETE_VEICULO);

            queryOwnerByIdStatement = conn.prepareStatement(Constants.QUERY_PROPRIETARIO_BY_ID);

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (dropVehicleTable != null) {
                dropVehicleTable.close();
            }
            if (createVehicleTable != null) {
                createVehicleTable.close();
            }
            if (queryVehicleByPlateStatement != null) {
                queryVehicleByPlateStatement.close();
            }
            if (queryVehicleByIdStatement != null) {
                queryVehicleByIdStatement.close();
            }
            if (insertVehicleStatement != null) {
                insertVehicleStatement.close();
            }
            if (deleteVehicleStatement != null) {
                deleteVehicleStatement.close();
            }
            if (queryOwnerByIdStatement != null) {
                queryOwnerByIdStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Veiculo queryVehicleByPlate(String plate) {
        try {
            queryVehicleByPlateStatement.setString(1, plate);
            ResultSet results = queryVehicleByPlateStatement.executeQuery();
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

    public Veiculo queryVehicleById(int vehicleId) {
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

    public boolean insertVehicle(Veiculo veiculo) {
        try {
            insertVehicleStatement.setString(1, veiculo.getPlaca());
            insertVehicleStatement.setInt(2, veiculo.getProprietario().getId());
            insertVehicleStatement.setString(3, veiculo.getModelo());
            insertVehicleStatement.setString(4, veiculo.getCor());
            insertVehicleStatement.setString(5, veiculo.getTipoVeiculo().toString());
            insertVehicleStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVehicle(String placa) {
        try {
            deleteVehicleStatement.setString(1, placa);
            deleteVehicleStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
