package dao;

import model.AreaEstacionamento;
import model.Evento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataEventos implements DataClass {

    // ATTRIBUTES
    private static Connection conn;

    private static DataEventos instance = new DataEventos();

    private PreparedStatement dropEventosTable;
    private PreparedStatement createEventosTable;

    private PreparedStatement dropReservasTable;
    private PreparedStatement createReservasTable;

    private PreparedStatement queryAllEventsStatement;
    private PreparedStatement queryEventByNameStatement;
    private PreparedStatement queryEventByIdStatement;
    private PreparedStatement insertEventStatement;
    private PreparedStatement deleteEventStatement;

    private PreparedStatement queryReservesStatement;
    private PreparedStatement queryReservedSpotsStatement;
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
    @Override
    public boolean open() {
        try {
            conn = DriverManager.getConnection(Constants.CONECTION_STR);

            dropEventosTable = conn.prepareStatement(Constants.DROP_EVENTOS_TABLE);
            createEventosTable = conn.prepareStatement(Constants.CREATE_EVENTOS_TABLE);

            dropReservasTable = conn.prepareStatement(Constants.DROP_RESERVAS_TABLE);
            createReservasTable = conn.prepareStatement(Constants.CREATE_RESERVAS_TABLE);

//            dropEventosTable.execute();
//            dropReservasTable.execute();
            createEventosTable.execute();
            createReservasTable.execute();

            insertEventStatement = conn.prepareStatement(Constants.INSERT_EVENTO);
            queryAllEventsStatement = conn.prepareStatement(Constants.QUERY_ALL_EVENTOS);
            queryEventByNameStatement = conn.prepareStatement(Constants.QUERY_EVENTO_BY_NAME);
            queryEventByIdStatement = conn.prepareStatement(Constants.QUERY_EVENTO_BY_ID);
            deleteEventStatement = conn.prepareStatement(Constants.DELETE_EVENTO);

            insertReserveStatement = conn.prepareStatement(Constants.INSERT_RESERVA);
            queryReservesStatement = conn.prepareStatement(Constants.QUERY_RESERVAS);
            queryReservedSpotsStatement = conn.prepareStatement(Constants.QUERY_RESERVA);
            deleteReservesStatement = conn.prepareStatement(Constants.DELETE_RESERVAS);


            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }


    // CLOSE METHOD
    @Override
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

            if (queryAllEventsStatement != null) {
                queryAllEventsStatement.close();
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
            if (queryReservedSpotsStatement != null) {
                queryReservedSpotsStatement.close();
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

    // CREATE EVENT
    public boolean createEvent(Evento evento) {
        return createEvent(evento.getNome(), evento.getInicio(), evento.getFim(), evento.getVagasReservadas());
    }

    public boolean createEvent(String nome, LocalDate inicio, LocalDate fim,
                               HashMap<AreaEstacionamento, Integer> reservas) {
        Evento queriedEvent = queryEventWithoutReservesByName(nome);
        if (queriedEvent != null) {
            return false;
        };
        if (!insertEvent(nome, inicio, fim)) {
            return false;
        };
        queriedEvent = queryEventWithoutReservesByName(nome);
        if (queriedEvent == null) {
            deleteEvent(nome);
            return false;
        };
        if (!insertReserves(queriedEvent.getId(), reservas)) {
            deleteReserves(queriedEvent.getId());
            deleteEvent(nome);
            return false;
        }
        return true;

    }


    // INSERT METHODS
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
            conn.commit();

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

    // QUERY METHODS
    public ArrayList<Evento> queryAllEventsWithoutReserves() {
        try {
            ResultSet results = queryAllEventsStatement.executeQuery();
            ArrayList<Evento> eventos = new ArrayList<>();
            while (results.next()) {
                int currId = results.getInt(1);
                String currNome = results.getString(2);
                LocalDate currInicio = LocalDate.parse(results.getString(3));
                LocalDate currFim = LocalDate.parse(results.getString(4));

                Evento evento = new Evento(currId, currNome, currInicio, currFim);
                eventos.add(evento);
            }
            return eventos;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public Evento queryEventWithoutReservesByName(String nome) {
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

    public Evento queryEventWithoutReservesById(int id) {
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

    public HashMap<Integer, Integer> queryReservesByEventId(int eventId) {
        try {
            queryReservesStatement.setInt(1, eventId);
            ResultSet results = queryReservesStatement.executeQuery();
            HashMap<Integer, Integer> reservas = new HashMap<>();
            while (results.next()) {
                reservas.put(results.getInt(3), results.getInt(4));
            }
            return reservas;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public int queryReservedSpots(int eventId, int areaId) {
        try {
            queryReservedSpotsStatement.setInt(1, eventId);
            queryReservedSpotsStatement.setInt(2, areaId);
            ResultSet results = queryReservedSpotsStatement.executeQuery();
            int reservedSpots = 0;
            if (results.next()) {
                reservedSpots = results.getInt(4);
            }
            return reservedSpots;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return 0;
        }
    }


    // DELETE METHODS
    public boolean deleteEventAndReserves(Evento evento) {
        boolean success = true;
        try {
            conn.setAutoCommit(false);
            deleteEventStatement.setString(1, evento.getNome());
            deleteEventStatement.execute();
            deleteReservesStatement.setInt(1, evento.getId());
            deleteReservesStatement.execute();
            conn.commit();

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

    public boolean deleteEvent(String nome) {
        try {
            deleteEventStatement.setString(1, nome);
            deleteEventStatement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReserves(int eventoId) {
        try {
            deleteReservesStatement.setInt(1, eventoId);
            deleteReservesStatement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

}
