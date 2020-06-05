package control;

import dao.DataUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentAreaController implements Initializable {

    @FXML
    private VBox contentArea;

    @FXML
    private Button openMenuBtn;

    @FXML
    private Label nomeLabel;

    private boolean opened = false;
    private Usuario usuario = DataUsuarios.getInstance().getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeLabel.setText(usuario.getNome());
    }

    @FXML
    private void openSidebar(ActionEvent event) throws IOException {
        BorderPane borderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        if (opened == false) {
            Parent sidebar = FXMLLoader.load(getClass().getResource("/view/fxml/sidebar/sidebar.fxml"));
            borderPane.setLeft(sidebar);
            opened = true;
        } else {
            borderPane.setLeft(null);
            opened = false;
        }
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        openMenuBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/login.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        DataUsuarios dataUsuarios = DataUsuarios.getInstance();
        dataUsuarios.setCurrentUser(null);
    }
}
