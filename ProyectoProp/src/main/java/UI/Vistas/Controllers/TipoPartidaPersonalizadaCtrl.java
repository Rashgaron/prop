package UI.Vistas.Controllers;

import Domain.Controllers.GameController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Pep Gascon
 */
public class TipoPartidaPersonalizadaCtrl {

    @FXML
    private Button btnPvsP;

    @FXML
    private Button btnPvsCPU;

    @FXML
    private Button btnCPUvsCPU;

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
    public void goToCrearPersonalizadaPvsP(ActionEvent args) throws IOException {

        OpenStage os = new OpenStage();
        os.openStageDatosOponente("/CrearPersonalizadaPvsP.fxml", "CrearPartidaPersonalizadaPvP",
                "/TipoPartidaPersonalizada.fxml", "Tipo Partida");

        closeButtonAction();
    }

    @FXML
    public void goToCrearPersonalizadaPvsCPU(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("CrearPersonalizadaPvsCPU","/CrearPersonalizadaPvsCPU.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToCrearPersonalizadaCPUvsCPU(ActionEvent args) throws IOException {
        GameController gameCtrl = new GameController();
        int gameID = gameCtrl.createBotGame("1", "3");
        gameCtrl.setCurrentGame(gameID);
        OpenStage os = new OpenStage();
        os.openStage("CrearPersonalizadaCPUvsCPU","/CrearPersonalizadaCPUvsCPU.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToJugar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Jugar","/Jugar.fxml");
        closeButtonAction();
    }
}
