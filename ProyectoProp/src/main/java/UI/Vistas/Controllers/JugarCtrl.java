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
public class JugarCtrl {

    @FXML
    private Button btnCrearEstandar;

    @FXML
    private Button btnCrearPersonalizada;

    @FXML
    private Button btnContinuar;

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
    public void goToEstandar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("TipoPartidaEstandar","/TipoPartidaEstandar.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToPersonalizada(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("TipoPartidaPersonalizada", "/TipoPartidaPersonalizada.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToContinuar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Continue game","/ContinueGame.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToHomeMenu(ActionEvent event) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Home Menu", "/HomeMenu.fxml");
        closeButtonAction();
    }

}
