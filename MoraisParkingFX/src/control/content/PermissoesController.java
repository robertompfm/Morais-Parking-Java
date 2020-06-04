package control.content;

import dao.DataAreas;
import dao.DataPermissoes;
import dao.DataProprietarios;
import dao.DataVeiculos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PermissoesController implements Initializable {

    // ATTRIBUTES
    @FXML
    private TextField proprietarioNomeAdd;

    @FXML
    private Button validacaoProprietarioBtnAdd;

    @FXML
    private ComboBox<String> placaComboBoxAdd;

    @FXML
    private ComboBox<String> areaComboBoxAdd;

    @FXML
    private Button btnAdd;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField proprietarioNomeDel;

    @FXML
    private Button validacaoProprietarioBtnDel;

    @FXML
    private ComboBox<String> placaComboBoxDel;

    @FXML
    private ComboBox<String> areaComboBoxDel;

    @FXML
    private Button btnDel;

    @FXML
    private Label warningLabelDel;

    private DataProprietarios dataProprietarios;
    private DataVeiculos dataVeiculos;
    private DataAreas dataAreas;
    private DataPermissoes dataPermissoes;
    private Alert confirmationDialog;
    private boolean lockedAdd;
    private boolean lockedDel;
    private Proprietario currProprietrioAdd;
    private Proprietario currProprietrioDel;


    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataProprietarios = DataProprietarios.getInstance();
        dataVeiculos = DataVeiculos.getInstance();
        dataAreas = DataAreas.getInstance();
        dataPermissoes = DataPermissoes.getInstance();

        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

        confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation dialog");
        confirmationDialog.setHeaderText("Você realmente deseja remover a permissão?");


        disableFieldsAdd();
        disableFieldsDel();
    }

    // BUTTON AND ACTION METHODS
    @FXML
    public void validateOwnerAdd(ActionEvent event) {
        String nome = proprietarioNomeAdd.getText();
        if (nome.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return;
        }
        dataProprietarios.open();
        currProprietrioAdd = dataProprietarios.queryOwnerByName(nome);
        dataProprietarios.close();
        if (lockedAdd && currProprietrioAdd != null) {
            enableFieldsAdd();
            populatePlacasAdd();
            populateAreasAdd();
            warningLabelAdd.setText("");
        } else if (lockedAdd) {
            warningLabelAdd.setText("Não foi possível encontrar o proprietario");
            warningLabelAdd.setTextFill(Color.RED);
        } else {
            disableFieldsAdd();
            warningLabelAdd.setText("");
        }
    }


    @FXML
    public void validateOwnerDel(ActionEvent event) {
        String nome = proprietarioNomeDel.getText();
        if (nome.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Nome'");
            return;
        }
        dataProprietarios.open();
        currProprietrioDel = dataProprietarios.queryOwnerByName(nome);
        dataProprietarios.close();
        if (lockedDel && currProprietrioDel != null) {
            enableFieldsDel();
            populatePlacasDel();
            populateAreasDel();
            warningLabelDel.setText("");
        } else if (lockedDel) {
            warningLabelDel.setText("Não foi possível encontrar o proprietario");
            warningLabelDel.setTextFill(Color.RED);
        } else {
            disableFieldsDel();
            warningLabelDel.setText("");
        }
    }

    @FXML
    public void plateSelectedAdd(ActionEvent event) {;
        String placa = placaComboBoxAdd.getValue();
        if (placa == null) {
            return;
        }
        dataVeiculos.open();
        Veiculo currVeiculo = dataVeiculos.queryVehicleByPlate(placa);
        dataVeiculos.close();
        populateAreasAdd(currVeiculo.getTipoVeiculo());
    }

    @FXML
    public void plateSelectedDel(ActionEvent event) {;
        String placa = placaComboBoxDel.getValue();
        if (placa == null) {
            return;
        }
        dataVeiculos.open();
        Veiculo currVeiculo = dataVeiculos.queryVehicleByPlate(placa);
        dataVeiculos.close();
        populateAreasAdd(currVeiculo.getTipoVeiculo());
    }

    @FXML
    public void givePermission(ActionEvent event) throws IOException {
        String placaStr = placaComboBoxAdd.getValue();
        String areaStr = areaComboBoxAdd.getValue();
        if (!validatePlacaAdd(placaStr)) {
            return;
        }
        if (!validateAreaAdd(areaStr)) {
            return;
        }
        dataVeiculos.open();
        Veiculo veiculo = dataVeiculos.queryVehicleByPlate(placaStr);
        dataVeiculos.close();

        dataAreas.open();
        AreaEstacionamento area = dataAreas.queryAreaByName(areaStr);
        dataAreas.close();

        Permissao permissao = new Permissao(veiculo, area);

        dataPermissoes.open();
        if (dataPermissoes.hasPermission(veiculo.getId(), area.getId())) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("O veículo já possui permissão para essa área");
        } else if (dataPermissoes.insertPermission(permissao)) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Permissão concedida com sucesso!");
            clearGivePermissionFields();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível conceder a permissão");
        }
        dataPermissoes.close();
    }

    @FXML
    public void removePermission(ActionEvent event) throws IOException {
        String placaStr = placaComboBoxDel.getValue();
        String areaStr = areaComboBoxDel.getValue();
        if (!validatePlacaDel(placaStr)) {
            return;
        }
        if (!validateAreaDel(areaStr)) {
            return;
        }
        dataVeiculos.open();
        Veiculo veiculo = dataVeiculos.queryVehicleByPlate(placaStr);
        dataVeiculos.close();

        dataAreas.open();
        AreaEstacionamento area = dataAreas.queryAreaByName(areaStr);
        dataAreas.close();;

        dataPermissoes.open();
//        Permissao permissao = dataPermissoes.queryPermission(veiculo.getId(), area.getId());
        boolean hasPermission = dataPermissoes.hasPermission(veiculo.getId(), area.getId());
        if (!hasPermission) {

            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("O veículo não possui essa permissão");
        } else {
            confirmationDialog.setContentText(
                    "Veículo :" + veiculo.getModelo() + " " + veiculo.getCor() +
                            "(" + veiculo.getPlaca() + ")" +
                            "\nÁrea: " + area.getNome()
            );
            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
                if (dataPermissoes.deletePermission(
                        veiculo.getId(),
                        area.getId()
                )) {
                    warningLabelDel.setTextFill(Color.GREEN);
                    warningLabelDel.setText("Permissão removida com sucesso!");
                    clearRemovePermissionFields();
                } else {
                    warningLabelDel.setTextFill(Color.RED);
                    warningLabelDel.setText("Não foi possível remover a permissão");
                }
            } else {
                warningLabelDel.setTextFill(Color.RED);
                warningLabelDel.setText("Operação abortada. A permissão não foi removido");
            }
        }
        dataPermissoes.close();
    }

    // VALIDATION METHODS
    private boolean validatePlacaAdd(String placa) {
        if (placa == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa slecionar uma opção do campo 'Placa'");
            return false;
        }
        return true;
    }

    private boolean validateAreaAdd(String area) {
        if (area == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa slecionar uma opção do campo 'Área'");
            return false;
        }
        return true;
    }

    private boolean validatePlacaDel(String placa) {
        if (placa == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa slecionar uma opção do campo 'Placa'");
            return false;
        }
        return true;
    }

    private boolean validateAreaDel(String area) {
        if (area == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa slecionar uma opção do campo 'Área'");
            return false;
        }
        return true;
    }



    // ENABLE AND DISABLE
    private void disableFieldsAdd() {
        placaComboBoxAdd.setDisable(true);
        areaComboBoxAdd.setDisable(true);
        btnAdd.setDisable(true);
        proprietarioNomeAdd.setDisable(false);
        validacaoProprietarioBtnAdd.setText("Validar");
        validacaoProprietarioBtnAdd.getStyleClass().remove("content-btn-red");
        validacaoProprietarioBtnAdd.getStyleClass().add("content-btn");
        currProprietrioAdd = null;
        lockedAdd = true;
    }

    private void disableFieldsDel() {
        placaComboBoxDel.setDisable(true);
        areaComboBoxDel.setDisable(true);
        btnDel.setDisable(true);
        proprietarioNomeDel.setDisable(false);
        validacaoProprietarioBtnDel.setText("Validar");
        validacaoProprietarioBtnDel.getStyleClass().remove("content-btn-red");
        validacaoProprietarioBtnDel.getStyleClass().add("content-btn");
        currProprietrioDel = null;
        lockedDel = true;
    }


    private void enableFieldsAdd() {
        placaComboBoxAdd.setDisable(false);
        areaComboBoxAdd.setDisable(false);
        btnAdd.setDisable(false);
        proprietarioNomeAdd.setDisable(true);
        validacaoProprietarioBtnAdd.setText("Cancelar");
        validacaoProprietarioBtnAdd.getStyleClass().remove("content-btn");
        validacaoProprietarioBtnAdd.getStyleClass().add("content-btn-red");
        lockedAdd = false;
    }

    private void enableFieldsDel() {
        placaComboBoxDel.setDisable(false);
        areaComboBoxDel.setDisable(false);
        btnDel.setDisable(false);
        proprietarioNomeDel.setDisable(true);
        validacaoProprietarioBtnDel.setText("Cancelar");
        validacaoProprietarioBtnDel.getStyleClass().remove("content-btn");
        validacaoProprietarioBtnDel.getStyleClass().add("content-btn-red");
        lockedDel = false;
    }


    // POPULATE METHODS
    private void populatePlacasAdd() {
        dataProprietarios.open();
        ArrayList<String> placas = dataProprietarios.queryOwnersPlatesByName(currProprietrioAdd.getNome());
        dataProprietarios.close();
        placaComboBoxAdd.getItems().setAll(placas);
    }

    private void populateAreasAdd() {
        dataAreas.open();
        ArrayList<String> areas = dataAreas.querySpecialAreasName();
        dataAreas.close();
        areaComboBoxAdd.getItems().setAll(areas);
    }

    private void populateAreasAdd(TipoVeiculo tipo) {
        dataAreas.open();
        ArrayList<String> areas = dataAreas.queryCompatibleSpecialAreasName(tipo);
        dataAreas.close();
        areaComboBoxAdd.getItems().setAll(areas);
    }

    private void populatePlacasDel() {
        dataProprietarios.open();
        ArrayList<String> placas = dataProprietarios.queryOwnersPlatesByName(currProprietrioDel.getNome());
        dataProprietarios.close();
        placaComboBoxDel.getItems().setAll(placas);
    }

    private void populateAreasDel() {
        dataAreas.open();
        ArrayList<String> areas = dataAreas.querySpecialAreasName();
        dataAreas.close();
        areaComboBoxDel.getItems().setAll(areas);
    }

    private void populateAreasDel(TipoVeiculo tipo) {
        dataAreas.open();
        ArrayList<String> areas = dataAreas.queryCompatibleSpecialAreasName(tipo);
        dataAreas.close();
        areaComboBoxDel.getItems().setAll(areas);
    }

    // CLEAR FIELDS
    public void clearGivePermissionFields() {
        placaComboBoxAdd.getSelectionModel().clearSelection();
        areaComboBoxAdd.getSelectionModel().clearSelection();
    }

    public void clearRemovePermissionFields() {
        placaComboBoxDel.getSelectionModel().clearSelection();
        areaComboBoxDel.getSelectionModel().clearSelection();
    }
}
