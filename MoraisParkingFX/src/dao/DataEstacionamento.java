package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DataEstacionamento {
    private static Connection conn;

    private static DataEstacionamento instance = new DataEstacionamento();

    private PreparedStatement dropEstacionamentoTable;
    private PreparedStatement createEstacionamentoTable;

    private PreparedStatement queryVehicleStatement;
    private PreparedStatement queryVehiclesByAreaStatement;
    private PreparedStatement queryAllVehiclesStatement;
    private PreparedStatement insertVehicleStatement;
    private PreparedStatement deleteVehicleStatement;



    public static DataEstacionamento getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);
            dropEstacionamentoTable = conn.prepareStatement(Constants.DROP_ESTACIONAMENTO_TABLE);
            createEstacionamentoTable = conn.prepareStatement(Constants.CREATE_ESTACIONAMENTO_TABLE);
            dropEstacionamentoTable.execute();
            createEstacionamentoTable.execute();

            insertVehicleStatement = conn.prepareStatement(Constants.INSERT_VEICULO_IN_ESTACIONAMENTO);
            queryVehicleStatement = conn.prepareStatement(Constants.QUERY_VEICULO_IN_ESTACIONAMENTO);
            deleteVehicleStatement = conn.prepareStatement(Constants.DELETE_VEICULO_FROM_ESTACIONAMENTO);
            queryAllVehiclesStatement = conn.prepareStatement(Constants.QUERY_ALL_VEICULOS_IN_ESTACIONAMENTO);
            queryVehiclesByAreaStatement = conn.prepareStatement(Constants.QUERY_VEICULOS_IN_AREA);
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropEstacionamentoTable != null) {
                dropEstacionamentoTable.close();
            }
            if (createEstacionamentoTable != null) {
                createEstacionamentoTable.close();
            }
            if (queryVehicleStatement != null) {
                queryVehicleStatement.close();
            }
            if (queryAllVehiclesStatement != null) {
                queryAllVehiclesStatement.close();
            }
            if (insertVehicleStatement != null) {
                insertVehicleStatement.close();
            }
            if (deleteVehicleStatement != null) {
                deleteVehicleStatement.close();
            }
            if (queryVehiclesByAreaStatement != null) {
                queryVehiclesByAreaStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public boolean isParked(String placa) {
        try {
            queryVehicleStatement.setString(1, placa);
            ResultSet results = queryVehicleStatement.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> queryAllVehicles() {
        try {
            ResultSet results = queryAllVehiclesStatement.executeQuery();
            ArrayList<String> placas = new ArrayList<>();
            while (results.next()) {
                placas.add(results.getString(3));
            }
            return placas;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<String> queryVehiclesByArea(AreaEstacionamento area) {
        try {
            queryVehiclesByAreaStatement.setInt(1, area.getId());
            ResultSet results = queryVehiclesByAreaStatement.executeQuery();
            ArrayList<String> placas = new ArrayList<>();
            while (results.next()) {
                placas.add(results.getString(3));
            }
            return placas;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public boolean insertVehicle(Veiculo veiculo, AreaEstacionamento area) {
        try {
            insertVehicleStatement.setInt(1, veiculo.getId());
            insertVehicleStatement.setString(2, veiculo.getPlaca());
            insertVehicleStatement.setInt(3, area.getId());
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
