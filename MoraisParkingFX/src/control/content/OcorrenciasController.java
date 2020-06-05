package control.content;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OcorrenciasController implements Initializable {

    // ATTRIBUTES
    @FXML
    private ComboBox<TipoOcorrencia> tipoOcorrenciaComboBox;

    @FXML
    private TextField placaField;

    @FXML
    private Button addVeiculoBtn;

    @FXML
    private Button remVeiculoBtn;

    @FXML
    private VBox veiculosVBox;

    @FXML
    private TextArea descricaoArea;

    @FXML
    private Button registerBtn;

    @FXML
    private Label warningLabel;

    private DataVeiculos dataVeiculos;
    private DataOcorrencias dataOcorrencias;

    private ArrayList<Veiculo> veiculosEnvolvidos;

    // INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataVeiculos = DataVeiculos.getInstance();
        dataOcorrencias = DataOcorrencias.getInstance();

        veiculosEnvolvidos = new ArrayList<>();

        warningLabel.setText("");
        warningLabel.setTextFill(Color.RED);

        remVeiculoBtn.getStyleClass().remove("content-btn");
        remVeiculoBtn.getStyleClass().add("content-btn-red");

        tipoOcorrenciaComboBox.getItems().clear();
        tipoOcorrenciaComboBox.getItems().addAll(TipoOcorrencia.values());

    }

    // ADD VEHICLE BUTTON
    @FXML
    public void addVehicle(ActionEvent event) {
        // VALIDATING
        String placa = placaField.getText();
        if (!validatePlaca(placa)) {
            return;
        }
        dataVeiculos.open();
        Veiculo veiculo = dataVeiculos.queryVehicleByPlate(placa);
        dataVeiculos.close();

        if (veiculo == null) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Veículo não cadastrado");
            return;
        }

        if (veiculosEnvolvidos.contains(veiculo)) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("O veículo ja foi adicionado");
            return;
        }

        veiculosEnvolvidos.add(veiculo);
        updateVBox();


        warningLabel.setText("");
    }

    @FXML
    public void remVehicle(ActionEvent event) {
        // VALIDATING
        String placa = placaField.getText();
        if (!validatePlaca(placa)) {
            return;
        }
        dataVeiculos.open();
        Veiculo veiculo = dataVeiculos.queryVehicleByPlate(placa);
        dataVeiculos.close();

        if (veiculo == null) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Veículo não cadastrado");
            return;
        }

        if (!veiculosEnvolvidos.contains(veiculo)) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("O veículo não está na lista de veículos envolvidos");
            return;
        }

        veiculosEnvolvidos.remove(veiculo);
        updateVBox();



        warningLabel.setText("");
    }

    @FXML
    public void register(ActionEvent event) {
        // VALIDATING
        TipoOcorrencia tipo = tipoOcorrenciaComboBox.getValue();
        if (!validateTipo(tipo)) {
            return;
        }
        String descricao = descricaoArea.getText();
        if (!validateDescricao(descricao)) {
            return;
        }

        // REGISTERING OCCURRENCE
        Ocorrencia ocorrencia = new Ocorrencia(tipo, LocalDate.now(), descricao);
        ocorrencia.setVeiculosEnvolvidos(veiculosEnvolvidos);
        dataOcorrencias.addOcorrencia(ocorrencia);

        warningLabel.setTextFill(Color.GREEN);
        warningLabel.setText("Ocorrencia registrada com sucesso!");

    }


    // UPDATE
    private void updateVBox() {
        try {
            veiculosVBox.getChildren().clear();
            for (int i = 0; i < veiculosEnvolvidos.size(); i++) {
                Veiculo currVeiculo = veiculosEnvolvidos.get(i);
                veiculosVBox.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fxml/content/veiculo_envolvido.fxml")));
                HBox currHBox = (HBox) veiculosVBox.getChildren().get(i);
                ((Label) currHBox.getChildren().get(0)).setText(currVeiculo.getPlaca());
                ((Label) currHBox.getChildren().get(1)).setText(currVeiculo.getModelo());
                ((Label) currHBox.getChildren().get(2)).setText(currVeiculo.getCor());
                ((Label) currHBox.getChildren().get(3)).setText(currVeiculo.getProprietario().getNome());
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // VALIDATION METHODS
    private boolean validatePlaca(String placa) {
        if (placa.equals("")) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Você precisa preencher o campo 'Placa'");
            return false;
        }
        return true;
    }

    private boolean validateDescricao(String descricao) {
        if (descricao.trim().equals("")) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Você precisa preencher o campo 'Descrição'");
            return false;
        }
        return true;
    }

    private boolean validateTipo(TipoOcorrencia tipo) {
        if (tipo == null) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Você precisa slecionar uma opção do campo 'Tipo de ocorrencia'");
            return false;
        }
        return true;
    }


}
