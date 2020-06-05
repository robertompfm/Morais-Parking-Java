package control.content;

import dao.DataAreas;
import dao.DataEstacionamento;
import dao.DataEventos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.AreaEstacionamento;
import model.Evento;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MonitoramentoController implements Initializable {

    @FXML
    private Label totalLabel;

    @FXML
    private Label ocupacaoTotalLabel;

    @FXML
    private ProgressBar ocupacaoBar;

    @FXML
    private Label taxaOcupacaoTotalLabel;

    @FXML
    private Label warningLabelAdd;


    @FXML
    private VBox areasVBox;

    private DataAreas dataAreas;
    private DataEventos dataEventos;
    private DataEstacionamento dataEstacionamento;

    private Evento eventoDoDia;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAreas = DataAreas.getInstance();
        dataEventos = DataEventos.getInstance();
        dataEstacionamento = DataEstacionamento.getInstance();
        eventoDoDia = null;
        setTodaysEvent();
        populateScreen();
        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);

    }


    // POPULATE SCREEN
    private void populateScreen() {
        try {
            int ocupacaoTotal = 0;
            int capacidadeTotal = 0;
            int i = 0;
            HashMap<AreaEstacionamento, Integer> occupations = getOccupations();
            for (Map.Entry<AreaEstacionamento, Integer> entry : occupations.entrySet()) {
                AreaEstacionamento currArea = entry.getKey();
                int currOcupacao = entry.getValue();
                ocupacaoTotal += currOcupacao;
                capacidadeTotal += currArea.getCapacidade();
                String ocupacaoStr = currOcupacao + "/" + currArea.getCapacidade();
                double taxaOcupacao = ((double) currOcupacao) / currArea.getCapacidade();
                String taxaOcupacaoStr = String.format("%.2f", (taxaOcupacao * 100)) + "%";
                areasVBox.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fxml/content/ocupacao_area.fxml")));
                HBox currHBox = (HBox) areasVBox.getChildren().get(i);
                ((Label) currHBox.getChildren().get(0)).setText(currArea.getNome());
                ((Label) currHBox.getChildren().get(1)).setText(ocupacaoStr);
                ((ProgressBar) currHBox.getChildren().get(2)).setProgress(taxaOcupacao);
                ((Label) currHBox.getChildren().get(3)).setText(taxaOcupacaoStr);
                i++;
            }
            String ocupacaoTotalStr = ocupacaoTotal + "/" + capacidadeTotal;
            double taxaOcupacaoTotal = ((double) ocupacaoTotal) / capacidadeTotal;
            String taxaOcupacaoTotalStr = String.format("%.2f", (taxaOcupacaoTotal * 100)) + "%";
            ocupacaoTotalLabel.setText(ocupacaoTotalStr);
            ocupacaoBar.setProgress(taxaOcupacaoTotal);
            taxaOcupacaoTotalLabel.setText(taxaOcupacaoTotalStr);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // SET TODAYS EVENT
    public void setTodaysEvent() {
        dataEventos.open();
        ArrayList<Evento> eventos = dataEventos.queryAllEventsWithoutReserves();
        dataEventos.close();
        for (Evento evento : eventos) {
            LocalDate today = LocalDate.now();
            if (evento.getInicio().compareTo(today) <= 0 && evento.getFim().compareTo(today) >= 0) {
                eventoDoDia = evento;
            }
        }
        if (eventoDoDia != null) {
            dataEventos.open();
            HashMap<Integer, Integer> reservesIdxVal = dataEventos.queryReservesByEventId(eventoDoDia.getId());
            dataEventos.close();
            HashMap<AreaEstacionamento, Integer> reserves = new HashMap<>();
            for (Map.Entry<Integer, Integer> reserveIdxVal : reservesIdxVal.entrySet()) {
                dataAreas.open();
                AreaEstacionamento currArea = dataAreas.queryAreaById(reserveIdxVal.getKey());
                dataAreas.close();
                reserves.put(currArea, reserveIdxVal.getValue());
            }
            eventoDoDia.setVagasReservadas(reserves);
        }
    }

    // GET OCCUPATIONS
    public HashMap<AreaEstacionamento, Integer> getOccupations() {
        dataAreas.open();
        ArrayList<AreaEstacionamento> areas = dataAreas.queryAllAreas();
        dataAreas.close();
        HashMap<AreaEstacionamento, Integer> ocupacoes = new HashMap<>();
        for (AreaEstacionamento area : areas) {
            int ocupacao;
            dataEstacionamento.open();
            ocupacao = dataEstacionamento.queryVehiclesByArea(area).size();
            dataEstacionamento.close();
            if (eventoDoDia != null) {
                dataEventos.open();
                ocupacao += dataEventos.queryReservedSpots(eventoDoDia.getId(), area.getId());
                dataEventos.close();
            }
            ocupacoes.put(area, ocupacao);
        }
        return ocupacoes;
    }

    // CLEAR FIELDS
//    private void clearFieldsAdd() {
//        nomeFieldAdd.clear();
//        inicioDate.getEditor().clear();
//        fimDate.getEditor().clear();
//        for (Node node : areasVBox.getChildren()) {
//            HBox currHBox = (HBox) node;
//            ((TextField) currHBox.getChildren().get(1)).setText("0");
//        }
//    }

}
