package control.content;

import dao.DataProprietarios;
import dao.DataVeiculos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Proprietario;
import model.TipoVeiculo;
import model.Veiculo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VeiculosController implements Initializable {

    // ATTRIBUTES
    @FXML
    private TextField proprietarioNome;

    @FXML
    private Button validacaoProprietarioBtn;

    @FXML
    private TextField placaFieldAdd;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField corField;

    @FXML
    private ComboBox<TipoVeiculo> tipoComboBox;

    @FXML
    private Button addBtn;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField placaFieldDel;

    @FXML
    private Button delBtn;

    @FXML
    private Label warningLabelDel;

    private DataProprietarios dataProprietarios;
    private DataVeiculos dataVeiculos;
    private Alert confirmationDialog;
    private boolean locked;


    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataProprietarios = DataProprietarios.getInstance();
        dataVeiculos = DataVeiculos.getInstance();

        tipoComboBox.getItems().clear();
        tipoComboBox.getItems().addAll(
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
        confirmationDialog.setHeaderText("Você realmente deseja remover o veículo?");

        disableFields();
    }

    // BUTTON AND ACTION METHODS
    @FXML
    public void validateOwner(ActionEvent event) {
        String nome = proprietarioNome.getText();
        if (nome.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return;
        }
        dataProprietarios.open();
        Proprietario proprietario = dataProprietarios.queryOwnerByName(nome);
        dataProprietarios.close();
        if (locked && proprietario != null) {
            dataProprietarios.setCurrentProprietario(proprietario);
            enableFields();
            warningLabelAdd.setText("");
        } else if (locked) {
            warningLabelAdd.setText("Não foi possível encontrar o proprietario");
            warningLabelAdd.setTextFill(Color.RED);
        } else {
            disableFields();
            warningLabelAdd.setText("");
        }
    }

    @FXML
    public void createVehicle(ActionEvent event) throws IOException {
        String placa = placaFieldAdd.getText();
        String modelo = modeloField.getText();
        String cor = corField.getText();
        TipoVeiculo tipoVeiculo = tipoComboBox.getValue();
        if (!validatePlaca(placa)) {
            return;
        }
        if (!validateModelo(modelo)) {
            return;
        }
        if (!validateCor(cor)) {
            return;
        }
        if (!validateTipo(tipoVeiculo)) {
            return;
        }

        Proprietario proprietario = dataProprietarios.getCurrentProprietario();
        Veiculo veiculo = new Veiculo(placa, proprietario, modelo, cor, tipoVeiculo);
        dataVeiculos.open();
        if (dataVeiculos.insertVehicle(veiculo)) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Veículo criado com sucesso!");
            clearCreateVehicleFields();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível cadastrar o veículo");
        }
        dataProprietarios.close();

    }

    @FXML
    public void removeVehicle(ActionEvent event) throws IOException {
        String placa = placaFieldDel.getText();
        if (!validatePlacaDel(placa)) {
            return;
        }
        dataVeiculos.open();
        Veiculo veiculo = dataVeiculos.queryVehicleByPlate(placa);
        if (veiculo == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não foi possível encontrar o veículo");
        } else {
            confirmationDialog.setContentText(
                    "Placa: " + veiculo.getPlaca() +
                            "\nProprietário: " + veiculo.getProprietario() +
                            "\nModelo: " + veiculo.getModelo() +
                            "\nCor: " + veiculo.getCor() +
                            "\nTipo: " + veiculo.getTipoVeiculo().toString()
            );
            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
                if (dataVeiculos.deleteVehicle(placa)) {
                    warningLabelDel.setTextFill(Color.GREEN);
                    warningLabelDel.setText("Veículo removido com sucesso!");
                    clearRemoveVehicleFields();
                } else {
                    warningLabelDel.setTextFill(Color.RED);
                    warningLabelDel.setText("Não foi possível remover o veículo");
                }
            } else {
                warningLabelDel.setTextFill(Color.RED);
                warningLabelDel.setText("Operação abortada. O veículo não foi removido");
            }
        }
        dataVeiculos.close();
    }

    // VALIDATION METHODS
    private boolean validatePlaca(String placa) {
        if (placa.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Placa'");
            return false;
        }
        return true;
    }

    private boolean validateModelo(String modelo) {
        if (modelo.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Modelo'");
            return false;
        }
        return true;
    }

    private boolean validateCor(String cor) {
        if (cor.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Cor'");
            return false;
        }
        return true;
    }

    private boolean validateTipo(TipoVeiculo tipo) {
        if (tipo == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa slecionar uma opção do campo 'Tipo'");
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

    // ENABLE AND DISABLE
    private void disableFields() {
        placaFieldAdd.setDisable(true);
        modeloField.setDisable(true);
        corField.setDisable(true);
        tipoComboBox.setDisable(true);
        addBtn.setDisable(true);
        proprietarioNome.setDisable(false);
        validacaoProprietarioBtn.setText("Validar");
        dataProprietarios.setCurrentProprietario(null);
        validacaoProprietarioBtn.getStyleClass().remove("content-btn-red");
        validacaoProprietarioBtn.getStyleClass().add("content-btn");
        locked = true;
    }


    private void enableFields() {
        placaFieldAdd.setDisable(false);
        modeloField.setDisable(false);
        corField.setDisable(false);
        tipoComboBox.setDisable(false);
        addBtn.setDisable(false);
        proprietarioNome.setDisable(true);
        validacaoProprietarioBtn.setText("Cancelar");
        validacaoProprietarioBtn.getStyleClass().remove("content-btn");
        validacaoProprietarioBtn.getStyleClass().add("content-btn-red");
        locked = false;
    }

    // CLEAR FIELDS
    public void clearCreateVehicleFields() {
        placaFieldAdd.clear();
        tipoComboBox.getSelectionModel().clearSelection();
        modeloField.clear();
        corField.clear();
    }

    public void clearRemoveVehicleFields() {
        placaFieldDel.clear();
    }
}
