package dao;

import model.AreaEstacionamento;
import model.Proprietario;
import model.Veiculo;

import java.sql.*;

public class DataCadastro implements DataClass {

    private static Connection conn;

    private static DataCadastro instance = new DataCadastro();

    private PreparedStatement dropPropTable;
    private PreparedStatement createPropTable;
    private PreparedStatement dropVeicTable;
    private PreparedStatement createVeicTable;
    private PreparedStatement dropAreaTable;
    private PreparedStatement createAreaTable;
    private PreparedStatement dropUsuariosTable;
    private PreparedStatement createUsuariosTable;


    private PreparedStatement queryPropByName;
    private PreparedStatement queryPropById;
    private PreparedStatement insertProp;
    private PreparedStatement queryVeicByPlaca;
    private PreparedStatement queryVeicById;
    private PreparedStatement insertVeic;
    private PreparedStatement queryAreaByName;
    private PreparedStatement queryAreaById;
    private PreparedStatement insertArea;
    private PreparedStatement insertUsuario;



    private DataCadastro() {

    }

    public static DataCadastro getInstance() {
        return instance;
    }

    @Override
    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropPropTable = conn.prepareStatement(Constants.DROP_PROPRIETARIOS_TABLE);
            createPropTable = conn.prepareStatement(Constants.CREATE_PROPRIETARIOS_TABLE);
            dropVeicTable = conn.prepareStatement(Constants.DROP_VEICULOS_TABLE);
            createVeicTable = conn.prepareStatement(Constants.CREATE_VEICULOS_TABLE);
            dropAreaTable = conn.prepareStatement(Constants.DROP_AREAS_TABLE);
            createAreaTable = conn.prepareStatement(Constants.CREATE_AREAS_TABLE);
            dropUsuariosTable = conn.prepareStatement(Constants.DROP_USUARIOS_TABLE);

            queryPropByName = conn.prepareStatement(Constants.QUERY_PROPRIETARIO_BY_NAME);
            queryPropById = conn.prepareStatement(Constants.QUERY_PROPRIETARIO_BY_ID);
            insertProp = conn.prepareStatement(Constants.INSERT_PROPRIETARIO);
            queryVeicByPlaca = conn.prepareStatement(Constants.QUERY_VEICULO_BY_PLACA);
            queryVeicById = conn.prepareStatement(Constants.QUERY_VEICULO_BY_ID);
            insertVeic = conn.prepareStatement(Constants.INSERT_VEICULO);
            queryAreaByName = conn.prepareStatement(Constants.QUERY_AREA_BY_NAME);
            queryAreaById = conn.prepareStatement(Constants.QUERY_AREA_BY_ID);
            insertArea = conn.prepareStatement(Constants.INSERT_AREA);
            insertUsuario = conn.prepareStatement(Constants.INSERT_USUARIO);

            dropPropTable.execute();
            createPropTable.execute();
            dropVeicTable.execute();
            createVeicTable.execute();
            dropAreaTable.execute();
            createAreaTable.execute();
            dropUsuariosTable.execute();
            createUsuariosTable.execute();


            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
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
            if (dropUsuariosTable != null) {
                dropUsuariosTable.close();
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
            if (createUsuariosTable != null) {
                createUsuariosTable.close();
            }
            if (queryPropByName != null) {
                queryPropByName.close();
            }
            if (queryPropById != null) {
                queryPropById.close();
            }
            if (queryVeicByPlaca != null) {
                queryVeicByPlaca.close();
            }
            if (queryVeicById != null) {
                queryVeicById.close();
            }
            if (queryAreaByName != null) {
                queryAreaByName.close();
            }
            if (queryAreaById != null) {
                queryAreaById.close();
            }
            if (insertProp != null) {
                insertProp.close();
            }
            if (insertVeic != null) {
                insertProp.close();
            }
            if (insertArea != null) {
                insertProp.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Proprietario queryProprietarioByName(String nome) {
        try {
            queryPropByName.setString(1, nome.toUpperCase());
            ResultSet results = queryPropByName.executeQuery();
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

    public Proprietario queryProprietarioById(int id) {
        try {
            queryPropById.setInt(1, id);
            ResultSet results = queryPropById.executeQuery();
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

//    public AreaEstacionamento queryAreaByName(String nome) {
//        try {
//            queryAreaByName.setString(1, nome.toUpperCase());
//            ResultSet results = queryAreaByName.executeQuery();
//            if (results.next()) {
//                int currId = (results.getInt(1));
//                String currNome = (results.getString(2));
//                int currCap = (results.getInt(3));
//                AreaEstacionamento area = new AreaEstacionamento(currId, currNome, currCap);
//                return area;
//            }
//            return null;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return null;
//        }
//    }

//    public AreaEstacionamento queryAreaById(int id) {
//        try {
//            queryAreaById.setInt(1, id);
//            ResultSet results = queryAreaById.executeQuery();
//            if (results.next()) {
//                int currId = (results.getInt(1));
//                String currNome = (results.getString(2));
//                int currCap = (results.getInt(3));
//                AreaEstacionamento area = new AreaEstacionamento(currId, currNome, currCap);
//                return area;
//            }
//            return null;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return null;
//        }
//    }

//    public boolean insertArea(String nome, int capacidade) {
//        nome = nome.toUpperCase();
////        AreaEstacionamento area = queryArea(nome);
////        if (area != null) {
////            return false;
////        }
//        try {
//            insertArea.setString(1, nome);
//            insertArea.setLong(2, capacidade);
//            insertArea.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return false;
//        }
//    }

//    public Veiculo queryVeiculo(String placa) {
//        try {
//            queryVeicByPlaca.setString(1, placa.toUpperCase());
//            ResultSet results = queryVeicByPlaca.executeQuery();
//            if (results.next()) {
//                int currId = (results.getInt(1));
//                String currPlaca = (results.getString(2));
//                int currPropId = (results.getInt(3));
//                String currModelo = (results.getString(4));
//                String currCor = (results.getString(5));
//                int currAreaId = (results.getInt(6));
//                Proprietario currProp = queryProprietarioById(currPropId);
//                String currAreaName = queryAreaById(currId).getNome();
//                Veiculo veiculo = new Veiculo(currId, currPlaca, currProp, currModelo, currCor, currAreaName);
//                return veiculo;
//            }
//            return null;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean insertVeiculo(String placa, String nome, long matricula, String curso, String modelo, String cor, String area) {
//        nome = nome.toUpperCase();
//        curso = curso.toUpperCase();
////        Proprietario proprietario = queryProprietario(nome);
////        if (proprietario != null) {
////            return false;
////        }
//        try {
//            insertProp.setString(1, nome);
//            insertProp.setLong(2, matricula);
//            insertProp.setString(3, curso);
//            insertProp.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            return false;
//        }
//
//    }

}
