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
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @author Pep Gascon
 */
public class TipoPartidaEstandarCtrl {

    GameController gameController = new GameController();

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
    public void goToCrearEstandarPvsCPU(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("CrearEstandarPvsCPU","/CrearEstandarPvsCPU.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToCrearCPUvsCPU(ActionEvent args) throws IOException {
        Random n = new Random();
        int num = n.nextInt(2);
        int gameId;
        if (num == 0) gameId = gameController.createBotGame("1", "2");
        else if (num == 1) gameId = gameController.createBotGame("1", "3");
        else gameId = gameController.createBotGame("2", "3");

        gameController.setCurrentGame(gameId);
        OpenStage os = new OpenStage();
        os.openStage("CrearGameCPUvsCPU","/GameCPUvsCPU.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToGetOpponentEmail(ActionEvent args) throws IOException{

        OpenStage os = new OpenStage();
        os.openStageDatosOponente("/GamePvsP.fxml", "Game", "/TipoPartidaEstandar.fxml", "Tipo Partida");

        closeButtonAction();
    }

    @FXML
    public void goToJugar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Jugar","/Jugar.fxml");
        closeButtonAction();
    }
}
