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


    // PROPRIETARIO
    public static final String DROP_PROPRIETARIOS_TABLE = "DROP TABLE IF EXISTS " +
            PROPRIETARIOS_TABLE;
    public static final String CREATE_PROPRIETARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PROPRIETARIOS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL UNIQUE, matricula INTEGER UNIQUE, curso TEXT)";

    public static final String QUERY_PROPRIETARIO_BY_NAME = "SELECT * FROM " + PROPRIETARIOS_TABLE +
            " WHERE nome = ?";
    public static final String QUERY_PROPRIETARIO_BY_ID = "SELECT * FROM " + PROPRIETARIOS_TABLE +
            " WHERE _id = ?";
    public static final String INSERT_PROPRIETARIO = "INSERT INTO " + PROPRIETARIOS_TABLE +
            " (nome, matricula, curso) VALUES (?, ?, ?)";


    // VEICULO
    public static final String DROP_VEICULOS_TABLE = "DROP TABLE IF EXISTS " +
            VEICULOS_TABLE;
    public static final String CREATE_VEICULOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            VEICULOS_TABLE +
            " (_id INTEGER PRIMARY KEY, placa TEXT NOT NULL UNIQUE, proprietario_id INTEGER, " +
            " modelo TEXT, cor TEXT, area_id INTEGER)";

    public static final String QUERY_VEICULO_BY_PLACA = "SELECT * FROM " + VEICULOS_TABLE +
            " WHERE placa = ?";
    public static final String QUERY_VEICULO_BY_ID = "SELECT * FROM " + VEICULOS_TABLE +
            " WHERE _id = ?";
    public static final String INSERT_VEICULO = "INSERT INTO " + VEICULOS_TABLE +
            " (placa, proprietario_id, modelo, cor, area_id) VALUES (?, ?, ?, ?, ?)";


    // AREAS
    public static final String DROP_AREAS_TABLE = "DROP TABLE IF EXISTS " +
            AREAS_TABLE;
    public static final String CREATE_AREAS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            AREAS_TABLE +
            " (_id INTEGER PRIMARY KEY, nome TEXT NOT NULL UNIQUE, capacidade INTEGER NOT NULL)";

    public static final String QUERY_AREA_BY_NAME = "SELECT * FROM " + AREAS_TABLE +
            " WHERE nome = ?";
    public static final String QUERY_AREA_BY_ID = "SELECT * FROM " + AREAS_TABLE +
            " WHERE _id = ?";
    public static final String INSERT_AREA = "INSERT INTO " + AREAS_TABLE +
            " (nome, capacidade) VALUES (?, ?)";
}
