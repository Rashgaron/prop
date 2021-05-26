package UI.Vistas.Controllers;

import Domain.Controllers.AuthController;
import Domain.Controllers.GameController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DatosOponenteCtrl {

    AuthController authController = new AuthController();
    GameController gameController = new GameController();

    private String titleGoBack;

    private String routeBackFile;

    private String titleNextScene;

    private String routeNextScene;

    @FXML
    private TextField lblEmail;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button closeButton;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        OpenStage openStage = new OpenStage();
        openStage.openStage(titleGoBack, routeBackFile);
        closeButtonAction();

    }

    @FXML
    void goToPlay(ActionEvent event) throws SQLException, IOException {
        String emailOponente = lblEmail.getText();
        boolean oponenteExists = authController.existsPersona(emailOponente);

        if(oponenteExists){
            int gameID = gameController.createGame(emailOponente);
            gameController.setCurrentGame(gameID);
            OpenStage openStage = new OpenStage();
            openStage.openStage(titleNextScene, routeNextScene );
            closeButtonAction();
        }
        else System.out.println("Game cannot be created");
    }


    public void setRouteNextScene(String routeNextScene) {
        this.routeNextScene = routeNextScene;
    }

    public void setTitleGoBack(String titleGoBack) {
        this.titleGoBack = titleGoBack;
    }

    public void setRouteBackFile(String routeBackFile) {
        this.routeBackFile = routeBackFile;
    }

    public void setTitleNextScene(String titleNextScene) {
        this.titleNextScene = titleNextScene;
    }
}
