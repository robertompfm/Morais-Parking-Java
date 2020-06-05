package control.content;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.AreaEstacionamento;
import model.Evento;
import model.Ocorrencia;
import model.TipoVeiculo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RelatoriosController implements Initializable {
    // ATTRIBUTE
    @FXML
    private TextArea relatorioArea;

    @FXML
    private Button saveBtn;

    @FXML
    private Label warningLabel;

    private DataAreas dataAreas;
    private DataEventos dataEventos;
    private DataEstacionamento dataEstacionamento;

    private Evento eventoDoDia;
    private String reportString;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAreas = DataAreas.getInstance();
        dataEventos = DataEventos.getInstance();
        dataEstacionamento = DataEstacionamento.getInstance();
        eventoDoDia = null;
        setTodaysEvent();
        reportString = "";
        updateMaxOccupation();

        relatorioArea.setText(generateReportString());

        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("");
    }

    // SAVE FILE
    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.setInitialFileName("relatorio.txt");

        File file = fileChooser.showSaveDialog(saveBtn.getScene().getWindow());

        if (file != null) {
            if (saveTextToFile(reportString, file)) {
                warningLabel.setTextFill(Color.GREEN);
                warningLabel.setText("Relatório salvo com sucesso!");
            } else {
                warningLabel.setTextFill(Color.RED);
                warningLabel.setText("Não foi possível salvar relatório");
            }
        }
    }

    private boolean saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return false;
        }
    }


    // GENERATE REPORT
    private String generateReportString() {
        StringBuilder reportStringBld = new StringBuilder();
        // CABECALHO
        reportStringBld.append("MORAIS PARKING - RELATORIO\n");
        reportStringBld.append("\nEstacionamento: UNIESP");
        reportStringBld.append("\nData: ").append(LocalDate.now().toString()).append("\n");

        // EVENTO DO DIA
        reportStringBld.append("\n\nEVENTO DO DIA:");
        if (eventoDoDia == null) {
            reportStringBld.append("\nNão há evento no dia de hoje");
        } else {
            reportStringBld.append("\n").append(eventoDoDia);
        }

        // OCUPACAO MAXIMA
        HashMap<AreaEstacionamento, Integer> ocupacoesMax = dataEstacionamento.getOcupacaoMaxima();
        reportStringBld.append("\n\nOCUPAÇÃO MÁXIMA:");
        int capacidadeTotal = 0;
        int ocupacaoTotal = 0;
        for (Map.Entry<AreaEstacionamento, Integer> entry : ocupacoesMax.entrySet()) {
            AreaEstacionamento area = entry.getKey();
            String areaNome = area.getNome();
            int capacidade = area.getCapacidade();
            int ocupacao = entry.getValue();
            capacidadeTotal += capacidade;
            ocupacaoTotal += ocupacao;
            String ocupacaoStr = ocupacao + "/" + capacidade;
            String taxaOcupacaoStr = String.format("%.2f", ((double) ocupacao / capacidade * 100)) + "%";
            reportStringBld.append("\n").append(areaNome);
            reportStringBld.append("\t").append(ocupacaoStr).append(" (").append(taxaOcupacaoStr).append(")");
        }

        // OCORRENCIAS
        reportStringBld.append("\n\nOCORRENCIAS:");
        ArrayList<Ocorrencia> ocorrencias = DataOcorrencias.getInstance().getOcorrencias();
        for (Ocorrencia ocorrencia : ocorrencias) {
            reportStringBld.append("\n").append(ocorrencia.toString());
        }
        if (ocorrencias.size() == 0) {
            reportStringBld.append("\nNão há ocorrências registradas");
        }
        this.reportString = reportStringBld.toString();
        return reportStringBld.toString();
    }

    // SET EVENT
    private void setTodaysEvent() {
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

    // UPDATE MAX OCCUPATION
    private void updateMaxOccupation() {
        dataAreas.open();
        ArrayList<AreaEstacionamento> areas = dataAreas.queryAllAreas();
        dataAreas.close();
        for (AreaEstacionamento area : areas) {
            int ocupacaoMax = dataEstacionamento.getOcupacaoByArea(area);
            int currOcupacao;
            dataEstacionamento.open();
            currOcupacao = dataEstacionamento.queryVehiclesByArea(area).size();
            dataEstacionamento.close();
            if (eventoDoDia != null) {
                dataEventos.open();
                currOcupacao += dataEventos.queryReservedSpots(eventoDoDia.getId(), area.getId());
                dataEventos.close();
            }
            if (currOcupacao >= ocupacaoMax) {
                dataEstacionamento.updateOccupationByArea(area, currOcupacao);
            }

        }

    }
}
