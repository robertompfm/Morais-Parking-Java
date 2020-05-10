package control.sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuGestorController {

    private String currentContentName;

    @FXML
    private void openMonitoramento(MouseEvent event) throws IOException {
        openContent(event, "monitoramento");
    }

    @FXML
    private void openAreas(MouseEvent event) throws IOException {
        openContent(event, "areas");
    }

    @FXML
    private void openRelatorios(MouseEvent event) throws IOException {
        openContent(event, "relatorios");
    }

    @FXML
    private void openPermissoes(MouseEvent event) throws IOException {
        openContent(event, "permissoes");
    }

    @FXML
    private void openEventos(MouseEvent event) throws IOException {
        openContent(event, "eventos");
    }

    @FXML
    private void openUsuarios(MouseEvent event) throws IOException {
        openContent(event, "usuarios");
    }

    private void openContent(MouseEvent event, String contentName) throws IOException {
        if (!contentName.equals(currentContentName)) {
            currentContentName = contentName;
            System.out.println(currentContentName);
            String path = "/view/fxml/content/" + currentContentName + ".fxml";
            Parent content = FXMLLoader.load(getClass().getResource(path));
            BorderPane borderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
            VBox contentArea = (VBox) borderPane.getCenter();
            contentArea.getChildren().set(1, content);
        }
    }

}
