package dao;

import model.AreaEstacionamento;
import model.Evento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataEventos {

    // ATTRIBUTES
    private static Connection conn;

    private static DataEventos instance = new DataEventos();

    private PreparedStatement dropEventosTable;
    private PreparedStatement createEventosTable;

    private PreparedStatement dropReservasTable;
    private PreparedStatement createReservasTable;

    private PreparedStatement queryEventByNameStatement;
    private PreparedStatement queryEventByIdStatement;
    private PreparedStatement insertEventStatement;
    private PreparedStatement deleteEventStatement;

    private PreparedStatement queryReservesStatement;
    private PreparedStatement insertReserveStatement;
    private PreparedStatement deleteReservesStatement;

    private Evento currentEvento;

    // CONSTRUCTOR
    private DataEventos() {
        currentEvento = null;
    }

    // GETTERS AND SETTERS
    public static DataEventos getInstance() {
        return instance;
    }

    public Evento getCurrentEvento() {
        return currentEvento;
    }

    public void setCurrentEvento(Evento currentEvento) {
        this.currentEvento = currentEvento;
    }


    // OPEN METHOD
    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropEventosTable = conn.prepareStatement(Constants.DROP_EVENTOS_TABLE);
            createEventosTable = conn.prepareStatement(Constants.CREATE_EVENTOS_TABLE);

            dropReservasTable = conn.prepareStatement(Constants.DROP_RESERVAS_TABLE);
            createReservasTable = conn.prepareStatement(Constants.CREATE_RESERVAS_TABLE);

            dropEventosTable.execute();
            dropReservasTable.execute();
            createEventosTable.execute();
            createReservasTable.execute();

            insertEventStatement = conn.prepareStatement(Constants.INSERT_EVENTO);
            queryEventByNameStatement = conn.prepareStatement(Constants.QUERY_EVENTO_BY_NAME);
            queryEventByIdStatement = conn.prepareStatement(Constants.QUERY_EVENTO_BY_ID);
            deleteEventStatement = conn.prepareStatement(Constants.DELETE_EVENTO);

            insertReserveStatement = conn.prepareStatement(Constants.INSERT_RESERVA);
            queryReservesStatement = conn.prepareStatement(Constants.QUERY_RESERVAS);
            deleteReservesStatement = conn.prepareStatement(Constants.DELETE_RESERVAS);


            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }


    // CLOSE METHOD
    public void close() {
        try {
            if (dropEventosTable != null) {
                dropEventosTable.close();
            }
            if (createEventosTable != null) {
                createEventosTable.close();
            }
            if (dropReservasTable != null) {
                dropReservasTable.close();
            }
            if (createReservasTable != null) {
                createReservasTable.close();
            }

            if (queryEventByNameStatement != null) {
                queryEventByNameStatement.close();
            }
            if (queryEventByIdStatement != null) {
                queryEventByIdStatement.close();
            }
            if (queryReservesStatement != null) {
                queryReservesStatement.close();
            }
            if (insertEventStatement != null) {
                insertEventStatement.close();
            }
            if (insertReserveStatement != null) {
                insertReserveStatement.close();
            }
            if (deleteEventStatement != null) {
                deleteEventStatement.close();
            }
            if (deleteReservesStatement != null) {
                deleteReservesStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


//    public boolean createEvent(Evento evento) {
//         return createEvent(
//                evento.getNome(),
//                evento.getInicio(),
//                evento.getFim(),
//                evento.getVagasReservadas()
//        );
//    }

    //INSERT METHODS
    public boolean insertEvent(String nome, LocalDate inicio, LocalDate fim) {

        try {
            insertEventStatement.setString(1, nome);
            insertEventStatement.setString(2, inicio.toString());
            insertEventStatement.setString(3, fim.toString());
            insertEventStatement.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }

    }

    public boolean insertReserves(int eventoId, HashMap<AreaEstacionamento, Integer> reservas) {
        boolean success = true;
        try {
            conn.setAutoCommit(false);

            for (Map.Entry<AreaEstacionamento, Integer> entry : reservas.entrySet()) {
                insertReserveStatement.setInt(1, eventoId);
                insertReserveStatement.setInt(2, entry.getKey().getId());
                insertReserveStatement.setInt(3, entry.getValue());
                insertReserveStatement.execute();
            }

        } catch (SQLException e) {
            success = false;
            System.out.println("SQLException: " + e.getMessage());
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

    public Evento queryEventWithoutDatesByName(String nome) {
        try {
            queryEventByNameStatement.setString(1, nome);
            ResultSet results = queryEventByNameStatement.executeQuery();

            if (results.next()) {
                int currId = results.getInt(1);
                String currNome = results.getString(2);
                LocalDate currInicio = LocalDate.parse(results.getString(3));
                LocalDate currFim = LocalDate.parse(results.getString(4));

                Evento evento = new Evento(currId, currNome, currInicio, currFim);
                return evento;
            }
            return null;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public Evento queryEventWithoutDatesById(int id) {
        try {
            queryEventByIdStatement.setInt(1, id);
            ResultSet results = queryEventByIdStatement.executeQuery();

            if (results.next()) {
                int currId = results.getInt(1);
                String currNome = results.getString(2);
                LocalDate currInicio = LocalDate.parse(results.getString(3));
                LocalDate currFim = LocalDate.parse(results.getString(4));

                Evento evento = new Evento(currId, currNome, currInicio, currFim);
                return evento;
            }
            return null;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


}
