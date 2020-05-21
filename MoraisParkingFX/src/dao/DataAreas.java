package dao;

import model.AreaEstacionamento;
import model.SetorUsuario;
import model.TipoVeiculo;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class DataAreas {

    private static Connection conn;

    private static DataAreas instance = new DataAreas();

    private PreparedStatement dropAreasTable;
    private PreparedStatement createAreasTable;

    private PreparedStatement queryAreasByNameStatement;
    private PreparedStatement queryAreasByIdStatement;
    private PreparedStatement insertAreaStatement;
    private PreparedStatement deleteAreaStatement;
    private PreparedStatement querySpecialAreasNameStatement;
    private PreparedStatement queryAllAreasNameStatement;

    private AreaEstacionamento currentArea;

    private DataAreas() {
        currentArea = null;
    }

    public static DataAreas getInstance() {
        return instance;
    }

    public AreaEstacionamento getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(AreaEstacionamento currentArea) {
        this.currentArea = currentArea;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropAreasTable = conn.prepareStatement(Constants.DROP_AREAS_TABLE);
            createAreasTable = conn.prepareStatement(Constants.CREATE_AREAS_TABLE);

//            dropAreasTable.execute();
            createAreasTable.execute();


            insertAreaStatement = conn.prepareStatement(Constants.INSERT_AREA);
            queryAreasByNameStatement = conn.prepareStatement(Constants.QUERY_AREA_BY_NAME);
            deleteAreaStatement = conn.prepareStatement(Constants.DELETE_AREA);
            querySpecialAreasNameStatement = conn.prepareStatement(Constants.QUERY_SPECIAL_AREAS_NAME);
            queryAreasByIdStatement = conn.prepareStatement(Constants.QUERY_AREA_BY_ID);
            queryAllAreasNameStatement = conn.prepareStatement(Constants.QUERY_ALL_AREAS_NAME);

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropAreasTable != null) {
                dropAreasTable.close();
            }
            if (createAreasTable != null) {
                createAreasTable.close();
            }
            if (queryAreasByNameStatement != null) {
                queryAreasByNameStatement.close();
            }
            if (insertAreaStatement != null) {
                insertAreaStatement.close();
            }
            if (deleteAreaStatement != null) {
                deleteAreaStatement.close();
            }
            if (querySpecialAreasNameStatement != null) {
                querySpecialAreasNameStatement.close();
            }
            if (queryAllAreasNameStatement != null) {
                queryAllAreasNameStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public AreaEstacionamento queryAreaByName(String nome) {
        try {
            queryAreasByNameStatement.setString(1, nome);
            ResultSet results = queryAreasByNameStatement.executeQuery();
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

    public AreaEstacionamento queryAreaById(int areaId) {
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

    public ArrayList<String> querySpecialAreasName() {
        try {
            ResultSet results = querySpecialAreasNameStatement.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (results.next()) {
                String currName = results.getString(1);
                names.add(currName);
            }
            return names;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<String> queryAllAreasName() {
        try {
            ResultSet results = queryAllAreasNameStatement.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (results.next()) {
                String currName = results.getString(1);
                names.add(currName);
            }
            return names;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertArea(AreaEstacionamento area) {
        try {
            insertAreaStatement.setString(1, area.getNome());
            insertAreaStatement.setInt(2, area.getCapacidade());
            insertAreaStatement.setString(3, area.getTipoVeiculo().toString());
            insertAreaStatement.setBoolean(4, area.isEspecial());
            insertAreaStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAreaByName(String nome) {
        try {
            deleteAreaStatement.setString(1, nome);
            deleteAreaStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
