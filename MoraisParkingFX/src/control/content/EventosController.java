package control.content;

import dao.DataAreas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.AreaEstacionamento;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventosController implements Initializable {

    @FXML
    private TextField nomeFieldAdd;

    @FXML
    private DatePicker inicioDate;

    @FXML
    private DatePicker fimDate;

    @FXML
    private Label warningLabelAdd;

    @FXML
    private TextField nomeFieldDel;

    @FXML
    private Label warningLabelDel;


    @FXML
    private VBox areasVBox;

    private DataAreas dataAreas;
//    private ArrayList<HBox> areaHBoxes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateScreen();
        warningLabelAdd.setText("");
        warningLabelAdd.setTextFill(Color.RED);
        warningLabelDel.setText("");
        warningLabelDel.setTextFill(Color.RED);

    }

    @FXML
    public void addEvent(ActionEvent event) {
        String nome = nomeFieldAdd.getText();
        LocalDate inicio = inicioDate.getValue();
        LocalDate fim = fimDate.getValue();
        if (!validateNameAdd(nome)) {
            return;
        }
        if (!validateStartDate(inicio)) {
            return;
        }
        if (!validateEndDate(fim)) {
            return;
        }
        for (Node node : areasVBox.getChildren()) {
            HBox currHBox = (HBox) node;
            System.out.println(1);
            String areaStr = ((Label) currHBox.getChildren().get(0)).getText();
            System.out.println(2 + ": " + areaStr);
            String reservaStr = ((TextField) currHBox.getChildren().get(1)).getText();
            System.out.println(3 + ": " + reservaStr);
            String capacidadeStr = ((Label) currHBox.getChildren().get(2)).getText();
            System.out.println(4 + ": " + capacidadeStr);
        }
        clearFieldsAdd();
    }


    // VALIDATION METHODS
    private boolean validateNameAdd(String name) {
        if (name.equals("")) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }

    private boolean validateStartDate(LocalDate date) {
        if (date == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa escolher uma data de inicio");
            return false;
        }
        return true;
    }

    private boolean validateEndDate(LocalDate date) {
        if (date == null) {
            warningLabelAdd.setTextFill(Color.RED);
            warningLabelAdd.setText("Você precisa escolher uma data de encerramento");
            return false;
        }
        return true;
    }



    private boolean validateNameDel(String name) {
        if (name.equals("")) {
            warningLabelDel.setTextFill(Color.RED);
            warningLabelDel.setText("Você precisa preencher o campo 'Nome'");
            return false;
        }
        return true;
    }


    // POPULATE SCREEN
    private void populateScreen() {
        try {
            dataAreas = DataAreas.getInstance();
            dataAreas.open();
            ArrayList<String> areasNames = dataAreas.queryAllAreasName();
            dataAreas.close();
            ArrayList<AreaEstacionamento> areas = new ArrayList<>();
            System.out.println(areasNames);
            for (String areaName : areasNames) {
                dataAreas.open();
                AreaEstacionamento area = dataAreas.queryAreaByName(areaName);
                dataAreas.close();
                areas.add(area);
                areasVBox.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fxml/content/reserva_area.fxml")));
            }
            for (int i = 0; i < areas.size(); i++) {
                HBox currHBox = (HBox) areasVBox.getChildren().get(i);
                ((Label) currHBox.getChildren().get(0)).setText(areas.get(i).getNome());
                ((TextField) currHBox.getChildren().get(1)).setText("0");
                ((Label) currHBox.getChildren().get(2)).setText(Integer.toString(areas.get(i).getCapacidade()));
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // CLEAR FIELDS
    private void clearFieldsAdd() {
        nomeFieldAdd.clear();
        inicioDate.getEditor().clear();
        fimDate.getEditor().clear();
        for (Node node : areasVBox.getChildren()) {
            HBox currHBox = (HBox) node;
            ((TextField) currHBox.getChildren().get(1)).setText("0");
        }
    }
}
