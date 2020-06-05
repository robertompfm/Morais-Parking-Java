package control.content;

import dao.DataUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.SetorUsuario;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {
    // ATTRIBUTES
    @FXML
    private TextField nomeFieldAdd;

    @FXML
    private ComboBox<SetorUsuario> setorComboBoxAdd;

    @FXML
    private TextField emailFieldAdd;

    @FXML
    private PasswordField passwordFieldAdd;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField emailFieldDel;

    @FXML
    private Button buttonDel;

    @FXML
    private Label warningLabelDel;

    private DataUsuarios dataUsuarios;
    private Alert confirmationDialog;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataUsuarios = DataUsuarios.getInstance();
        setorComboBoxAdd.getItems().clear();
        setorComboBoxAdd.getItems().addAll(
                SetorUsuario.ESTACIONAMENTO,
                SetorUsuario.RH,
                SetorUsuario.GESTOR);

        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

        confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation dialog");
        confirmationDialog.setHeaderText("Você realmente deseja remover o funcionário?");
    }

    // BUTTON ACTION METHODS
    @FXML
    public void createUser(ActionEvent event) throws IOException {
        String nome = nomeFieldAdd.getText();
        SetorUsuario setor = setorComboBoxAdd.getValue();
        String email = emailFieldAdd.getText();
        String password = passwordFieldAdd.getText();
        if (!validateName(nome)) {
            return;
        }
        if (!validateSetor(setor)) {
            return;
        }
        if (!validateEmail(email)) {
            return;
        }
        if (!validatePassword(password)) {
            return;
        }
        Usuario usuario = new Usuario(nome, setor, email, password);
        dataUsuarios.open();
        if (dataUsuarios.insertUsuario(usuario)) {
            warningLabelAdd.setTextFill(Color.GREEN);
            warningLabelAdd.setText("funcionário criado com sucesso!");
            clearCreateUserFields();
        } else {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Não foi possível cadastrar o funcionário");
        }
        dataUsuarios.close();

    }

    @FXML
    public void removeUser(ActionEvent event) throws IOException {
        String email = emailFieldDel.getText();
        if (!validateEmailDel(email)) {
            return;
        }
        dataUsuarios.open();
        Usuario usuario = dataUsuarios.queryUsuarioByEmail(email);
        if (usuario == null) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não foi possível encontrar o funcionário");
        } else if (dataUsuarios.getCurrentUser().getEmail().equals(usuario.getEmail())) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Não é possível remover seu próprio cadastro");
        } else {
            confirmationDialog.setContentText(
                    "Usuario: " + usuario.getNome() +
                            "\nSetor: " + usuario.getSetor() +
                            "\nEmail: " + usuario.getEmail()
            );
            if (confirmationDialog.showAndWait().get() == ButtonType.OK) {
                if (dataUsuarios.deleteUsuarioByEmail(email)) {
                    warningLabelDel.setTextFill(Color.GREEN);
                    warningLabelDel.setText("funcionário removido com sucesso!");
                    clearCreateUserFields();
                } else {
                    warningLabelDel.setTextFill(Color.RED);
                    warningLabelDel.setText("Não foi possível remover o funcionário");
                }
            } else {
                warningLabelDel.setTextFill(Color.RED);
                warningLabelDel.setText("Operação abortada. O funcionário não foi removido");
            }
        }
        dataUsuarios.close();
    }

    // VALIDATION METHODS
    private boolean validateName(String name) {
        if (name.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }

    private boolean validateSetor(SetorUsuario setor) {
        if (setor == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa slecionar uma opção do campo 'Setor'");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Email'");
            return false;
        } else if (!email.contains("@")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Utilize um email valido");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Senha'");
            return false;
        }
        return true;
    }


    private boolean validateEmailDel(String email) {
        if (email.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Email'");
            return false;
        } else if (!email.contains("@")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Utilize um email valido");
            return false;
        }
        return true;
    }

    // CLEAR METHODS
    public void clearCreateUserFields() {
        nomeFieldAdd.clear();
        setorComboBoxAdd.getSelectionModel().clearSelection();
        emailFieldAdd.clear();
        passwordFieldAdd.clear();
    }

    public void clearRemoveUserFields() {
        emailFieldDel.clear();
    }


}
