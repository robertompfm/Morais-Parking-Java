package control.content;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;

public class AcessoController implements Initializable {


    // ATTRIBUTES
    @FXML
    private TextField placaFieldAdd;

    @FXML
    private TextField placaFieldDel;

    @FXML
    private ComboBox<TipoVeiculo> tipoComboBoxAdd;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private Button buttonDel;

    @FXML
    private Label warningLabelDel;

    private Evento eventoDoDia;

    private DataAreas dataAreas;
    private DataEventos dataEventos;
    private DataVeiculos dataVeiculos;
    private DataPermissoes dataPermissoes;
    private DataEstacionamento dataEstacionamento;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAreas = DataAreas.getInstance();
        dataEventos = DataEventos.getInstance();
        dataVeiculos = DataVeiculos.getInstance();
        dataPermissoes = DataPermissoes.getInstance();
        dataEstacionamento = DataEstacionamento.getInstance();
        eventoDoDia = null;
        setTodaysEvent();

        tipoComboBoxAdd.getItems().clear();
        tipoComboBoxAdd.getItems().addAll(
                TipoVeiculo.CARRO,
                TipoVeiculo.MOTOCICLETA,
                TipoVeiculo.ONIBUS
        );

        clearFieldsAdd();
        clearFieldsDel();

        warningLabelAdd.setText("");
        warningLabelDel.setText("");

    }

    // BUTTON ACTION METHODS
    @FXML
    public void letEnter(ActionEvent event) throws IOException {
        // VALIDATION
        String placa = placaFieldAdd.getText();
        TipoVeiculo tipoVeiculo = tipoComboBoxAdd.getValue();
        if (!validatePlacaAdd(placa)) {
            return;
        }
        if (!validateType(tipoVeiculo)) {
            return;
        }
        // SEARCH FOR VEHICLE
        Veiculo veiculo;
        dataVeiculos.open();
        veiculo = dataVeiculos.queryVehicleByPlate(placa);
        dataVeiculos.close();
        if (veiculo == null) {
            veiculo = new Veiculo(placa, tipoVeiculo);
        }
        // CHECK IF VEHICLE IS ALREADY PARKED
        dataEstacionamento.open();
        boolean isParked = dataEstacionamento.isParked(veiculo.getPlaca());
        dataEstacionamento.close();
        if (isParked) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("O veiculo ja se encontra no estacionamento");
            return;
        }
        // CHECK FOR PERMISSIONS
        AreaEstacionamento areaEspecial = null;
        dataPermissoes.open();
        Permissao permissao = dataPermissoes.queryPermissionByVeiculoId(veiculo.getId());
        dataPermissoes.close();

        if (permissao != null) {
            areaEspecial = permissao.getArea();
        }
        dataAreas.open();
        AreaEstacionamento areaComum = dataAreas.queryCommonAreaByTipo(veiculo.getTipoVeiculo());
        dataAreas.close();
        System.out.println(areaEspecial);
        System.out.println(areaComum);

        // TRY TO PARK IN SPECIAL AREA IF HAS PERMISSION
        boolean success = false;
        if (areaEspecial != null) {
            int ocupacao = getOccupation(areaEspecial);
            System.out.println(ocupacao);
            // CHECK IF THERE IS EMPTY SPOT
            if (ocupacao < areaEspecial.getCapacidade()) {
//                dataEstacionamento.open();
//                success = dataEstacionamento.insertVehicle(veiculo, areaEspecial);
//                dataEstacionamento.close();
                System.out.println("OK area especial");
            }
        }

        // TRY TO PARK IN COMMON AREA
        if (!success) {
            int ocupacao = getOccupation(areaComum);
            System.out.println(ocupacao);
            // CHECK IF THERE IS EMPTY SPOT
            if (ocupacao < areaComum.getCapacidade()) {
//                dataEstacionamento.open();
//                success = dataEstacionamento.insertVehicle(veiculo, areaComum);
//                dataEstacionamento.close();
                System.out.println("OK area comum");
            }
        }
        // SHOW FEEDBACK
        if (success) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Entrada autorizada!");
            clearFieldsAdd();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível autorizar a entrada");
        }
    }
//
//    @FXML
//    public void removeArea(ActionEvent event) throws IOException {
//        String nome = nomeFieldDel.getText();
//        if (!validateNameDel(nome)) {
//            return;
//        }
//        dataAreas.open();
//        AreaEstacionamento area = dataAreas.queryAreaByName(nome);
//        if (area == null) {
//            warningLabelDel.setTextFill(Color.RED);
//            warningLabelDel.setText("Não foi possível encontrar a área especial inserida");
//        } else if (!area.isEspecial()) {
//            warningLabelDel.setTextFill(Color.RED);
//            warningLabelDel.setText("Não é possível remover uma área comum");
//        } else {
//            confirmationDialog.setContentText(
//                    "Área: " + area.getNome() +
//                            "\nCapacidade: " + area.getCapacidade() +
//                            "\nTipo de veículo: " + area.getTipoVeiculo()
//            );
//            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
//                if (dataAreas.deleteAreaByName(nome)) {
//                    warningLabelDel.setTextFill(Color.GREEN);
//                    warningLabelDel.setText("Área removida com sucesso!");
//                    clearRemoveAreaFields();
//                } else {
//                    warningLabelDel.setTextFill(Color.RED);
//                    warningLabelDel.setText("Não foi possível remover a usuário área");
//                }
//            } else {
//                warningLabelDel.setTextFill(Color.RED);
//                warningLabelDel.setText("Operação abortada. A área não foi removido");
//            }
//        }
//        dataAreas.close();
//    }
//
//
    // VALIDATION METHODS
    private boolean validatePlacaAdd(String placa) {
        if (placa.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Placa'");
            return false;
        }
        return true;
    }

    private boolean validatePlacaDel(String placa) {
        if (placa.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Placa'");
            return false;
        }
        return true;
    }


    private boolean validateType(TipoVeiculo tipo) {
        if (tipo == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa slecionar uma opção do campo 'Tipo'");
            return false;
        }
        return true;
    }


    // CLEAR METHODS
    public void clearFieldsAdd() {
        placaFieldAdd.clear();
        tipoComboBoxAdd.getSelectionModel().clearSelection();
    }

    public void clearFieldsDel() {
        placaFieldDel.clear();
    }

    // SET EVENT
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

    // GET OCCUPATION
    public int getOccupation(AreaEstacionamento area) {
        dataAreas.open();
        ArrayList<AreaEstacionamento> areas = dataAreas.queryAllAreas();
        dataAreas.close();
        HashMap<AreaEstacionamento, Integer> ocupacoes = new HashMap<>();
        for (AreaEstacionamento currArea : areas) {
            if (!area.getNome().equals(currArea.getNome())) {
                continue;
            }
            int ocupacao;
            dataEstacionamento.open();
            ocupacao = dataEstacionamento.queryVehiclesByArea(currArea).size();
            dataEstacionamento.close();
            if (eventoDoDia != null) {
                dataEventos.open();
                ocupacao += dataEventos.queryReservedSpots(eventoDoDia.getId(), currArea.getId());
//              dataEventos.close();
            }
            ocupacoes.put(currArea, ocupacao);
            return ocupacao;
        }
        return 0;
    }

    public HashMap<AreaEstacionamento, Integer> getOccupations() {
        dataAreas.open();
        ArrayList<AreaEstacionamento> areas = dataAreas.queryAllAreas();
//        System.out.println(areas.size());
        dataAreas.close();
        HashMap<AreaEstacionamento, Integer> ocupacoes = new HashMap<>();
        for (AreaEstacionamento area : areas) {
            int ocupacao;
            dataEstacionamento.open();
            ocupacao = dataEstacionamento.queryVehiclesByArea(area).size();
//            System.out.println(ocupacao);
            dataEstacionamento.close();
            if (eventoDoDia != null) {
                dataEventos.open();
                ocupacao += dataEventos.queryReservedSpots(eventoDoDia.getId(), area.getId());
//                System.out.println(ocupacao);
                dataEventos.close();
            }
            ocupacoes.put(area, ocupacao);
        }
        return ocupacoes;
    }
}
