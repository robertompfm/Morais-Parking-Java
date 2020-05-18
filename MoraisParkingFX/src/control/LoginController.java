package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import dao.DataUsuarios;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    private Alert loginFailed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginFailed = new Alert(Alert.AlertType.ERROR);
        loginFailed.setTitle("Login failed");
        loginFailed.setHeaderText("Não foi possível realizar o login:");
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        DataUsuarios dataUsuarios = DataUsuarios.getInstance();
        dataUsuarios.open();
        Usuario usuario = dataUsuarios.queryUsuarioByEmail(username.getText());
        if (usuario == null) {
            loginFailed.setContentText("Usuário não encontrado");
            loginFailed.show();
        } else if (!usuario.getPassword().equals(password.getText())) {
            loginFailed.setContentText("Usuário ou senha incorretos");
            loginFailed.show();
        } else {
            dataUsuarios.setCurrentUser(usuario);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/main.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("MoraisParking");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        dataUsuarios.close();
    }
}
