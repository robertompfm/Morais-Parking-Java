package control.content;

import dao.DataAreas;
import dao.DataEventos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.AreaEstacionamento;
import model.Evento;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EventosController implements Initializable {

    @FXML
    private TextField nomeFieldAdd;

    @FXML
    private DatePicker inicioDate;

    @FXML
    private DatePicker fimDate;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField nomeFieldDel;

    @FXML
    private Label warningLabelDel;


    @FXML
    private VBox areasVBox;

    private DataAreas dataAreas;
    private DataEventos dataEventos;
//    private ArrayList<HBox> areaHBoxes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAreas = DataAreas.getInstance();
        dataEventos = DataEventos.getInstance();
        populateScreen();
        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

    }

    // BUTTON ACTION METHODS
    @FXML
    public void addEvent(ActionEvent event) {
        String nome = nomeFieldAdd.getText();
        LocalDate inicio = inicioDate.getValue();
        LocalDate fim = fimDate.getValue();
        if (!validateNameAdd(nome)) {
            return;
        }
        if (!validateDates(inicio, fim)) {
            return;
        }
        HashMap<AreaEstacionamento, Integer> reservas = new HashMap<>();
        for (Node node : areasVBox.getChildren()) {
            HBox currHBox = (HBox) node;
            String areaStr = ((Label) currHBox.getChildren().get(0)).getText();
            dataAreas.open();
            AreaEstacionamento currArea = dataAreas.queryAreaByName(areaStr);
            dataAreas.close();
            String reservaStr = ((TextField) currHBox.getChildren().get(1)).getText();
            if (!validateReserva(currArea, reservaStr)) {
                return;
            }
            int currReserva = Integer.parseInt(reservaStr);
            reservas.put(currArea, currReserva);
        }
        Evento newEvent = new Evento(nome, inicio, fim);
        newEvent.setVagasReservadas(reservas);
        dataEventos.open();
        if (!dataEventos.createEvent(newEvent)) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível criar o evento. Talvez ja tenha sido cadastrado um evento com mesmo nome.");
        } else {
            System.out.println("OK");
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Evento cadastrado com sucesso!");
            clearFieldsAdd();
        };
        dataEventos.close();

    }


    @FXML
    public void delEvent(ActionEvent event) {
        String nome = nomeFieldDel.getText();
        if (!validateNameDel(nome)) {
            return;
        }
        dataEventos.open();
        Evento currEvento = dataEventos.queryEventWithoutReservesByName(nome);
        if (currEvento == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Evento não encontrado");
            dataEventos.close();
            return;
        }
        if (dataEventos.deleteEventAndReserves(currEvento)) {
            warningLabelDel.setTextFill(Color.GREEN);
            warningLabelDel.setText("Evento cancelado!");
            clearFieldsDel();

        } else {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não possível cancelar o evento");

        }

        dataEventos.close();

    }


    // VALIDATION METHODS
    private boolean validateNameAdd(String name) {
        if (name.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }


    private boolean validateDates(LocalDate inicio, LocalDate fim) {
        if (inicio == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa escolher uma data de inicio");
            return false;
        }
        if (fim == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa escolher uma data de encerramento");
            return false;
        }
        if (inicio.compareTo(LocalDate.now()) <= 0) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("A data de inicio deve ser uma data futura");
            return false;
        }
        if (inicio.compareTo(fim) > 0) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("A data de inicio nao pode ser apos a data do fim");
            return false;
        }
        return true;
    }

    private boolean validateReserva(AreaEstacionamento area, String resevaStr) {
        if (area == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Algo deu errado. Tente novamente");
            return false;
        }
        try {
            int num = Integer.parseInt(resevaStr);
            if (num > area.getCapacidade()) {
                warningLabelAdd.setTextFill(Color.RED);
                warningLabelAdd.setText("As vagas reservadas não podem ser maiores que a capacidade das áreas");
                return false;
            }
            if (num < 0) {
                warningLabelAdd.setTextFill(Color.RED);
                warningLabelAdd.setText("As vagas reservadas não podem ser menores que zero");
                return false;
            }
        } catch (NumberFormatException nfe) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você deve inserir valores numéricos inteiros válidos nos campos de 'Reserva'");
            return false;
        }
        return true;
    }



    private boolean validateNameDel(String name) {
        if (name.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }


    // POPULATE SCREEN
    private void populateScreen() {
        try {

            dataAreas.open();
            ArrayList<String> areasNames = dataAreas.queryAllAreasName();
            dataAreas.close();
            ArrayList<AreaEstacionamento> areas = new ArrayList<>();
            for (String areaName : areasNames) {
                dataAreas.open();
                AreaEstacionamento area = dataAreas.queryAreaByName(areaName);
                dataAreas.close();
                areas.add(area);
                areasVBox.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fxml/content/reserva_area.fxml")));
            }
            for (int i = 0; i < areas.size(); i++) {
                HBox currHBox = (HBox) areasVBox.getChildren().get(i);
                ((Label) currHBox.getChildren().get(0)).setText(areas.get(i).getNome());
                ((TextField) currHBox.getChildren().get(1)).setText("0");
                ((Label) currHBox.getChildren().get(2)).setText(Integer.toString(areas.get(i).getCapacidade()));
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // CLEAR FIELDS
    private void clearFieldsAdd() {
        nomeFieldAdd.clear();
        inicioDate.getEditor().clear();
        fimDate.getEditor().clear();
        for (Node node : areasVBox.getChildren()) {
            HBox currHBox = (HBox) node;
            ((TextField) currHBox.getChildren().get(1)).setText("0");
        }
    }

    private void clearFieldsDel() {
        nomeFieldDel.clear();
    }
}
