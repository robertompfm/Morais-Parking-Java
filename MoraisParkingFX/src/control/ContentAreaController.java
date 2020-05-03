package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentAreaController implements Initializable {

    @FXML
    private VBox contentArea;

    private boolean opened = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void openSidebar(ActionEvent event) throws IOException {
        BorderPane borderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        if (opened == false) {
            Parent sidebar = FXMLLoader.load(getClass().getResource("/view/fxml/sidebar.fxml"));
            borderPane.setLeft(sidebar);
            opened = true;
        } else {
            borderPane.setLeft(null);
            opened = false;
        }
    }
}
