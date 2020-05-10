package control.sidebar;

import javafx.event.ActionEvent;
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

public class MenuEstController {

    private String currentContentName;


    @FXML
    private void openMonitoramento(MouseEvent event) throws IOException {
        openContent(event, "monitoramento");
    }

    @FXML
    private void openAcesso(MouseEvent event) throws IOException {
        openContent(event, "acesso");
    }

    @FXML
    private void openProprietarios(MouseEvent event) throws IOException {
        openContent(event, "proprietarios");
    }

    @FXML
    private void openVeiculos(MouseEvent event) throws IOException {
        openContent(event, "veiculos");
    }

    @FXML
    private void openOcorrencias(MouseEvent event) throws IOException {
        openContent(event, "ocorrencias");
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
