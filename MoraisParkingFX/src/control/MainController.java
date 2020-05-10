package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane borderPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent contentArea = FXMLLoader.load(getClass().getResource("/view/fxml/content_area.fxml"));
            borderPane.setCenter(contentArea);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
