package control.content;

import dao.DataProprietarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Proprietario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProprietariosController implements Initializable {
    // ATTRIBUTES
    @FXML
    private TextField nomeFieldAdd;

    @FXML
    private TextField matriculaFieldAdd;

    @FXML
    private TextField cursoFieldAdd;

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

    private DataProprietarios dataProprietarios;
    private Alert confirmationDialog;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataProprietarios = DataProprietarios.getInstance();

        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

        confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation dialog");
        confirmationDialog.setHeaderText(
                "Você realmente deseja remover o proprietário?" +
                        "\nTodos os veículos do proprietário também serão removidos"
        );
    }

    // BUTTON ACTION METHODS
    @FXML
    public void createOwner(ActionEvent event) throws IOException {
        String nome = nomeFieldAdd.getText();
        String matriculaStr = matriculaFieldAdd.getText();
        String curso = cursoFieldAdd.getText();
        if (!validateName(nome)) {
            return;
        }
        if (!validateMatricula(matriculaStr)) {
            return;
        }
        if (!validateCurso(curso)) {
            return;
        }
        Proprietario proprietario = new Proprietario(
                nome, Long.parseLong(matriculaStr), curso
        );
        dataProprietarios.open();
        if (dataProprietarios.insertOwner(proprietario)) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("Proprietário criado com sucesso!");
            clearCreateOwnerFields();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível cadastrar o proprietário");
        }
        dataProprietarios.close();

    }

    @FXML
    public void removeOwner(ActionEvent event) throws IOException {
        String nome = nomeFieldDel.getText();
        if (!validateNameDel(nome)) {
            return;
        }
        dataProprietarios.open();
        Proprietario proprietario = dataProprietarios.queryOwnerByName(nome);
        if (proprietario == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não foi possível encontrar o proprietário");
        } else {
            confirmationDialog.setContentText(
                    "Proprietário: " + proprietario.getNome() +
                            "\nMatricula: " + proprietario.getMatricula() +
                            "\nCurso: " + proprietario.getCurso()
            );
            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
                if (dataProprietarios.deleteOwner(nome)) {
                    warningLabelDel.setTextFill(Color.GREEN);
                    warningLabelDel.setText("Proprietário removido com sucesso!");
                    clearRemoveOwnerFields();
                } else {
                    warningLabelDel.setTextFill(Color.RED);
                    warningLabelDel.setText("Não foi possível remover o proprietário");
                }
            } else {
                warningLabelDel.setTextFill(Color.RED);
                warningLabelDel.setText("Operação abortada. O proprietário não foi removido");
            }
        }
        dataProprietarios.close();
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

    private boolean validateMatricula(String matricula) {
        if (matricula.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Matricula'");
            return false;
        }
        try {
            long num = Long.parseLong(matricula);
        } catch (NumberFormatException nfe) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("A sua matricula deve ser composta apenas por digitos");
            return false;
        }
        return true;
    }

    private boolean validateCurso(String curso) {
        if (curso.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Curso'");
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
    public void clearCreateOwnerFields() {
        nomeFieldAdd.clear();
        matriculaFieldAdd.clear();
        cursoFieldAdd.clear();
    }

    public void clearRemoveOwnerFields() {
        nomeFieldDel.clear();
    }

}
