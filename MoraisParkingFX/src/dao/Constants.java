package dao;

public final class Constants {
    // DATABASE
    public static final String DB_NAME = "morais_parking.db";
    public static final String CONECTION_STR = "jdbc:sqlite:" + DB_NAME;


    // TABLES
    public static final String USUARIOS_TABLE = "usuarios";
    public static final String PROPRIETARIOS_TABLE = "proprietarios";
    public static final String VEICULOS_TABLE = "veiculos";
    public static final String AREAS_TABLE = "areas_estacionamento";
    public static final String PERMISSOES_TABLE = "permissoes";
    public static final String EVENTOS_TABLE = "eventos";
    public static final String RESERVAS_TABLE = "reservas";
    public static final String ESTACIONAMENTO_TABLE = "estacionamento";


    // USUARIOS
    public static final String DROP_USUARIOS_TABLE = "DROP TABLE IF EXISTS " +
            USUARIOS_TABLE;
    public static final String CREATE_USUARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            USUARIOS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL, setor TEXT NOT NULL," +
            " email TEXT NOT NULL UNIQUE, password TEXT NOT NULL UNIQUE)";

    public static final String QUERY_USUARIO_BY_EMAIL = "SELECT * FROM " + USUARIOS_TABLE +
            " WHERE email = ?";
    public static final String INSERT_USUARIO = "INSERT INTO " + USUARIOS_TABLE +
            " (nome, setor, email, password) VALUES (?, ?, ?, ?)";
    public static final String DELETE_USUARIO = "DELETE FROM " + USUARIOS_TABLE +
            " WHERE email = ?";


    // AREAS
    public static final String DROP_AREAS_TABLE = "DROP TABLE IF EXISTS " +
            AREAS_TABLE;
    public static final String CREATE_AREAS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            AREAS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL UNIQUE, capacidade INTEGER NOT NULL," +
            " tipo_veiculo TEXT NOT NULL, especial INTEGER NOT NULL)";


    public static final String QUERY_AREA_BY_NAME = "SELECT * FROM " + AREAS_TABLE +
            " WHERE nome = ?";
    public static final String QUERY_AREA_BY_ID = "SELECT * FROM " + AREAS_TABLE +
            " WHERE _id = ?";
    public static final String QUERY_SPECIAL_AREAS_NAME = "SELECT nome FROM " + AREAS_TABLE +
            " WHERE especial = 1";
    public static final String QUERY_COMPATIBLE_SPECIAL_AREAS_NAME = "SELECT nome FROM " + AREAS_TABLE +
            " WHERE especial = 1 and tipo_veiculo = ?";
    public static final String QUERY_COMMON_AREA_BY_TIPO = "SELECT * FROM " + AREAS_TABLE +
            " WHERE especial = 0 and tipo_veiculo = ?";
    public static final String QUERY_ALL_AREAS_NAME = "SELECT nome FROM " + AREAS_TABLE;
    public static final String QUERY_ALL_AREAS = "SELECT * FROM " + AREAS_TABLE;
    public static final String INSERT_AREA = "INSERT INTO " + AREAS_TABLE +
            " (nome, capacidade, tipo_veiculo, especial) VALUES (?, ?, ?, ?)";
    public static final String DELETE_AREA = "DELETE FROM " + AREAS_TABLE +
            " WHERE especial = 1 AND nome = ?";



    // PROPRIETARIOS
    public static final String DROP_PROPRIETARIOS_TABLE = "DROP TABLE IF EXISTS " +
            PROPRIETARIOS_TABLE;
    public static final String CREATE_PROPRIETARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PROPRIETARIOS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL UNIQUE, matricula INTEGER, curso TEXT)";

    public static final String QUERY_PROPRIETARIO_BY_NAME = "SELECT * FROM " + PROPRIETARIOS_TABLE +
            " WHERE nome = ?";
    public static final String QUERY_PROPRIETARIO_BY_ID = "SELECT * FROM " + PROPRIETARIOS_TABLE +
            " WHERE _id = ?";
    public static final String INSERT_PROPRIETARIO = "INSERT INTO " + PROPRIETARIOS_TABLE +
            " (nome, matricula, curso) VALUES (?, ?, ?)";
    public static final String DELETE_PROPRIETARIO = "DELETE FROM " + PROPRIETARIOS_TABLE +
            " WHERE nome = ?";


    // VEICULO
    public static final String DROP_VEICULOS_TABLE = "DROP TABLE IF EXISTS " +
            VEICULOS_TABLE;
    public static final String CREATE_VEICULOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            VEICULOS_TABLE +
            " (_id INTEGER PRIMARY KEY, placa TEXT NOT NULL UNIQUE, proprietario_id INTEGER, " +
            " modelo TEXT, cor TEXT, tipo_veiculo TEXT NOT NULL)";

    public static final String QUERY_VEICULO_BY_PLACA =  "SELECT " + VEICULOS_TABLE +
        "._id, placa, proprietario_id, nome, matricula, curso, modelo, cor, tipo_veiculo FROM " +
        VEICULOS_TABLE + " INNER JOIN " + PROPRIETARIOS_TABLE + " ON " +
        VEICULOS_TABLE + ".proprietario_id" + " = " + PROPRIETARIOS_TABLE + "._id" +
        " WHERE placa = ?";
    public static final String QUERY_VEICULO_BY_ID =  "SELECT " + VEICULOS_TABLE +
            "._id, placa, proprietario_id, nome, matricula, curso, modelo, cor, tipo_veiculo FROM " +
            VEICULOS_TABLE + " INNER JOIN " + PROPRIETARIOS_TABLE + " ON " +
            VEICULOS_TABLE + ".proprietario_id" + " = " + PROPRIETARIOS_TABLE + "._id" +
            " WHERE " + VEICULOS_TABLE + "._id = ?";
    public static final String QUERY_VEICULOS_BY_PROPRIETARIO_NAME = "SELECT placa FROM " +
            VEICULOS_TABLE + " JOIN " + PROPRIETARIOS_TABLE + " ON " +
            VEICULOS_TABLE + ".proprietario_id" + " = " + PROPRIETARIOS_TABLE + "._id" +
            " WHERE nome = ?";
    public static final String INSERT_VEICULO = "INSERT INTO " + VEICULOS_TABLE +
            " (placa, proprietario_id, modelo, cor, tipo_veiculo) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_VEICULO = "DELETE FROM " + VEICULOS_TABLE +
            " WHERE placa = ?";


    // PERMISSOES
    public static final String DROP_PERMISSOES_TABLE = "DROP TABLE IF EXISTS " +
            PERMISSOES_TABLE;
    public static final String CREATE_PERMISSOES_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PERMISSOES_TABLE +
            " (_id INTEGER PRIMARY KEY, veiculo_id INTEGER NOT NULL UNIQUE, area_id INTEGER NOT NULL)";

    public static final  String QUERY_PERMISSAO = "SELECT * FROM " + PERMISSOES_TABLE +
            " WHERE " + "veiculo_id = ?" + " AND " + "area_id = ?";
    public static final  String QUERY_PERMISSAO_BY_VEICULO_ID = "SELECT * FROM " + PERMISSOES_TABLE +
            " WHERE " + "veiculo_id = ?";
    public static final String INSERT_PERMISSAO = "INSERT INTO " + PERMISSOES_TABLE +
            " (veiculo_id, area_id)" +
            " VALUES (?, ?)";
    public static final String DELETE_PERMISSAO = "DELETE FROM " + PERMISSOES_TABLE +
            " WHERE " + "veiculo_id = ?" + " AND " + "area_id = ?";


    // EVENTOS E RESERVAS
    public static final String CREATE_EVENTOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            EVENTOS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL UNIQUE," +
            " inicio TEXT NOT NULL UNIQUE, fim TEXT NOT NULL UNIQUE)";
    public static final String CREATE_RESERVAS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            RESERVAS_TABLE +
            " (_id INTEGER PRIMARY KEY, evento_id INTEGER NOT NULL," +
            " area_id INTEGER NOT NULL, vagas INTEGER NOT NULL)";
    public static final String DROP_EVENTOS_TABLE = "DROP TABLE IF EXISTS " +
            EVENTOS_TABLE;
    public static final String DROP_RESERVAS_TABLE = "DROP TABLE IF EXISTS " +
            RESERVAS_TABLE;


    public static final String QUERY_ALL_EVENTOS = "SELECT * FROM " + EVENTOS_TABLE;
    public static final String QUERY_EVENTO_BY_NAME = "SELECT * FROM " + EVENTOS_TABLE +
            " WHERE " + "nome = ?";
    public static final String QUERY_EVENTO_BY_ID = "SELECT * FROM " + EVENTOS_TABLE +
            " WHERE " + "_id = ?";
    public static final String QUERY_RESERVAS = "SELECT * FROM " + RESERVAS_TABLE +
            " WHERE " + "evento_id = ?";
    public static final String QUERY_RESERVA = "SELECT * FROM " + RESERVAS_TABLE +
            " WHERE " + "evento_id = ?" + " AND " + "area_id = ?";

    public static final String INSERT_EVENTO = "INSERT INTO " + EVENTOS_TABLE +
            " (nome, inicio, fim)" +
            " VALUES (?, ?, ?)";
    public static final String INSERT_RESERVA = "INSERT INTO " + RESERVAS_TABLE +
            " (evento_id, area_id, vagas)" +
            " VALUES (?, ?, ?)";

    public static final String DELETE_EVENTO = "DELETE FROM " + EVENTOS_TABLE +
            " WHERE " + "nome = ?";
    public static final String DELETE_RESERVAS = "DELETE FROM " + RESERVAS_TABLE +
            " WHERE " + "evento_id = ?";

    // ESTACIONAMENTO
    public static final String DROP_ESTACIONAMENTO_TABLE = "DROP TABLE IF EXISTS " +
            ESTACIONAMENTO_TABLE;
    public static final String CREATE_ESTACIONAMENTO_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ESTACIONAMENTO_TABLE +
            " (_id INTEGER PRIMARY KEY, veiculo_id INTEGER NOT NULL," +
            " placa TEXT NOT NULL UNIQUE, area_id INTEGER NOT NULL)";

    public static final  String QUERY_VEICULOS_IN_AREA = "SELECT * FROM " + ESTACIONAMENTO_TABLE +
            " WHERE " + "area_id = ?";
    public static final  String QUERY_ALL_VEICULOS_IN_ESTACIONAMENTO = "SELECT * FROM " +
            ESTACIONAMENTO_TABLE;
    public static final  String QUERY_VEICULO_IN_ESTACIONAMENTO = "SELECT * FROM " +
            ESTACIONAMENTO_TABLE + " WHERE " + "placa = ?";;


    public static final String INSERT_VEICULO_IN_ESTACIONAMENTO = "INSERT INTO " +
            ESTACIONAMENTO_TABLE +
            " (veiculo_id, placa, area_id)" +
            " VALUES (?, ?, ?)";
    public static final String DELETE_VEICULO_FROM_ESTACIONAMENTO = "DELETE FROM " + ESTACIONAMENTO_TABLE +
            " WHERE " + "placa = ?";

}
