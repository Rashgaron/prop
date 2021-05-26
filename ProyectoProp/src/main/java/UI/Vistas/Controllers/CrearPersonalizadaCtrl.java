package UI.Vistas.Controllers;

import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Pep Gascon
 */
public class CrearPersonalizadaCtrl {

    @FXML
    private Button btnNormal;

    @FXML
    private Button btnDificil;

    @FXML
    private Button btnExperto;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button closeButton;

    @FXML
    void closeWindow(ActionEvent event) {
        closeButtonAction();
    }
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void goToJugar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Jugar","/Jugar.fxml");
        closeButtonAction();
    }

}
