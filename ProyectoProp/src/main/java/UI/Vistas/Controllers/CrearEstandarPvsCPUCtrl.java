package UI.Vistas.Controllers;

import Domain.Controllers.GameController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Pep Gascon
 */
public class CrearEstandarPvsCPUCtrl {

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
    public GameController gameController = new GameController();

    @FXML
    void createNormalGame(ActionEvent event) throws IOException {
        createGame("1");
    }

    @FXML
    void createDifficultGame(ActionEvent event) throws  IOException{
        createGame("2");
    }

    @FXML
    void createHellGame(ActionEvent event) throws IOException {
        createGame("3");
    }

    public void createGame(String botLvL) throws IOException {
        int gameID = gameController.createGame(botLvL);
        gameController.setCurrentGame(gameID);
        OpenStage openStage = new OpenStage();
        openStage.openStage("Game", "/GamePvsCPU.fxml");
        closeButtonAction();
    }
    @FXML
    public void goToEstandar(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("TipoPartidaEstandar","/TipoPartidaEstandar.fxml");
        closeButtonAction();
    }

}
