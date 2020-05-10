package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.TipoUsuario;
import model.Usuario;
import model.data.DataUsuarios;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {


    @FXML
    private VBox sidebar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DataUsuarios dataUsuarios = DataUsuarios.getInstance();
            TipoUsuario tipo = dataUsuarios.getCurrentUser().getTipo();
            Parent menu = null;
            switch (tipo) {
                case ESTACIONAMENTO:
                    menu = FXMLLoader.load(getClass().getResource("/view/fxml/menu_estacionamento.fxml"));
                    break;
                case RH:
                    menu = FXMLLoader.load(getClass().getResource("/view/fxml/menu_rh.fxml"));
                    break;
                case GESTOR:
                    menu = FXMLLoader.load(getClass().getResource("/view/fxml/menu_gestor.fxml"));
                    break;
            }
            if (menu != null) {
                sidebar.getChildren().add(menu);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
