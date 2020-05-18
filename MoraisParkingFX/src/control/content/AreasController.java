package control.content;

import dao.DataAreas;
import dao.DataUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.AreaEstacionamento;
import model.SetorUsuario;
import model.TipoVeiculo;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AreasController implements Initializable {

    // ATTRIBUTES
    @FXML
    private TextField nomeFieldAdd;

    @FXML
    private TextField capacidadeFieldAdd;

    @FXML
    private ComboBox<TipoVeiculo> tipoComboBoxAdd;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField nomeFieldDel;

    @FXML
    private Button buttonDel;

    @FXML
    private Label warningLabelDel;

    private DataAreas dataAreas;
    private Alert confirmationDialog;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAreas = DataAreas.getInstance();
        tipoComboBoxAdd.getItems().clear();
        tipoComboBoxAdd.getItems().addAll(
                TipoVeiculo.CARRO,
                TipoVeiculo.MOTOCICLETA,
                TipoVeiculo.ONIBUS
        );

        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

        confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation dialog");
        confirmationDialog.setHeaderText("Você realmente deseja remover essa área?");

    }

    // BUTTON ACTION METHODS
    @FXML
    public void createArea(ActionEvent event) throws IOException {
        String nome = nomeFieldAdd.getText();
        String capacidade = capacidadeFieldAdd.getText();
        TipoVeiculo tipoVeiculo = tipoComboBoxAdd.getValue();
        if (!validateName(nome)) {
            return;
        }
        if (!validateCapacidade(capacidade)) {
            return;
        }
        if (!validateType(tipoVeiculo)) {
            return;
        }
        AreaEstacionamento area = new AreaEstacionamento(
                nome, Integer.parseInt(capacidade), tipoVeiculo, true
        );
        dataAreas.open();
        if (dataAreas.insertArea(area)) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Área cadastrada com sucesso!");
            clearCreateAreaFields();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível criar a área");
        }
        dataAreas.close();

    }

    @FXML
    public void removeArea(ActionEvent event) throws IOException {
        String nome = nomeFieldDel.getText();
        if (!validateNameDel(nome)) {
            return;
        }
        dataAreas.open();
        AreaEstacionamento area = dataAreas.queryAreaByName(nome);
        if (area == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não foi possível encontrar a área especial inserida");
        } else if (!area.isEspecial()) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não é possível remover uma área comum");
        } else {
            confirmationDialog.setContentText(
                    "Área: " + area.getNome() +
                            "\nCapacidade: " + area.getCapacidade() +
                            "\nTipo de veículo: " + area.getTipoVeiculo()
            );
            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
                if (dataAreas.deleteAreaByName(nome)) {
                    warningLabelDel.setTextFill(Color.GREEN);
                    warningLabelDel.setText("Área removida com sucesso!");
                    clearRemoveAreaFields();
                } else {
                    warningLabelDel.setTextFill(Color.RED);
                    warningLabelDel.setText("Não foi possível remover a usuário área");
                }
            } else {
                warningLabelDel.setTextFill(Color.RED);
                warningLabelDel.setText("Operação abortada. A área não foi removido");
            }
        }
        dataAreas.close();
    }


    // VALIDATION METHODS
    private boolean validateName(String nome) {
        if (nome.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }

    private boolean validateCapacidade(String capacidade) {
        if (capacidade.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Capacidade'");
            return false;
        }
        try {
            int num = Integer.parseInt(capacidade);
        } catch (NumberFormatException nfe) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Insira um valor numérico inteiro válido no campo 'Capacidade'");
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

    private boolean validateNameDel(String nome) {
        if (nome.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }

    // CLEAR METHODS
    public void clearCreateAreaFields() {
        nomeFieldAdd.clear();
        tipoComboBoxAdd.getSelectionModel().clearSelection();
        capacidadeFieldAdd.clear();
    }

    public void clearRemoveAreaFields() {
        nomeFieldDel.clear();
    }


}
