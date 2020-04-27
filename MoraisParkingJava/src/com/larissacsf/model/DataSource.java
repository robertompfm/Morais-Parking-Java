package com.larissacsf.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static Connection conn;

    private static DataSource instance = new DataSource();

    private PreparedStatement dropPropTable;
    private PreparedStatement createPropTable;
    private PreparedStatement dropVeicTable;
    private PreparedStatement createVeicTable;
    private PreparedStatement dropAreaTable;
    private PreparedStatement createAreaTable;

    private PreparedStatement queryProp;
    private PreparedStatement insertProp;
    private PreparedStatement queryVeic;
    private PreparedStatement insertVeic;
    private PreparedStatement queryArea;
    private PreparedStatement insertArea;


    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropPropTable = conn.prepareStatement(Constants.DROP_PROPRIETARIOS_TABLE);
            createPropTable = conn.prepareStatement(Constants.CREATE_PROPRIETARIOS_TABLE);
            dropVeicTable = conn.prepareStatement(Constants.DROP_VEICULOS_TABLE);
            createVeicTable = conn.prepareStatement(Constants.CREATE_VEICULOS_TABLE);
            dropAreaTable = conn.prepareStatement(Constants.DROP_AREAS_TABLE);
            createAreaTable = conn.prepareStatement(Constants.CREATE_AREAS_TABLE);

            queryProp = conn.prepareStatement(Constants.QUERY_PROPRIETARIO);
            insertProp = conn.prepareStatement(Constants.INSERT_PROPRIETARIO);
            queryVeic = conn.prepareStatement(Constants.QUERY_VEICULO);
            insertVeic = conn.prepareStatement(Constants.INSERT_VEICULO);
            queryArea = conn.prepareStatement(Constants.QUERY_AREA);
            insertArea = conn.prepareStatement(Constants.INSERT_AREA);

            dropPropTable.execute();
            createPropTable.execute();
            dropVeicTable.execute();
            createVeicTable.execute();
            dropAreaTable.execute();
            createAreaTable.execute();


            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (dropPropTable != null) {
                dropPropTable.close();
            }
            if (dropVeicTable != null) {
                dropVeicTable.close();
            }
            if (dropAreaTable != null) {
                dropAreaTable.close();
            }
            if (createPropTable != null) {
                createPropTable.close();
            }
            if (createVeicTable != null) {
                createVeicTable.close();
            }
            if (createAreaTable != null) {
                createAreaTable.close();
            }
            if (queryProp != null) {
                queryProp.close();
            }
            if (insertProp != null) {
                insertProp.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Proprietario queryProprietario(String nome) {
        try {
            queryProp.setString(1, nome.toUpperCase());
            ResultSet results = queryProp.executeQuery();
            if (results.next()) {
                int currId = (results.getInt(1));
                String currNome = (results.getString(2));
                long currMat = (results.getLong(3));
                String currCurso = (results.getString(4));
                Proprietario proprietario = new Proprietario(currId, currNome, currMat, currCurso);
                return proprietario;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertProprietario(String nome, long matricula, String curso) {
        nome = nome.toUpperCase();
        curso = curso.toUpperCase();
//        Proprietario proprietario = queryProprietario(nome);
//        if (proprietario != null) {
//            return false;
//        }
        try {
            insertProp.setString(1, nome);
            insertProp.setLong(2, matricula);
            insertProp.setString(3, curso);
            insertProp.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }

    }

    public AreaEstacionamento queryArea(String nome) {
        try {
            queryArea.setString(1, nome.toUpperCase());
            ResultSet results = queryArea.executeQuery();
            if (results.next()) {
                int currId = (results.getInt(1));
                String currNome = (results.getString(2));
                int currCap = (results.getInt(3));
                AreaEstacionamento area = new AreaEstacionamento(currId, currNome, currCap);
                return area;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public boolean insertArea(String nome, int capacidade) {
        nome = nome.toUpperCase();
//        AreaEstacionamento area = queryArea(nome);
//        if (area != null) {
//            return false;
//        }
        try {
            insertArea.setString(1, nome);
            insertArea.setLong(2, capacidade);
            insertArea.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }

    }

}
