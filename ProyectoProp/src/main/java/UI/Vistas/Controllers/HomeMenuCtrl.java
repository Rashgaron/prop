package UI.Vistas.Controllers;

import Domain.UserLogged;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class HomeMenuCtrl {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnEstadistiques;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRanking;

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


    @FXML
    public void goToStatistics(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Statistics","/Statistics.fxml");
        closeButtonAction();
    }

    @FXML
    public void goToRanking(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Ranking","/Ranking.fxml");
        closeButtonAction();
    }
    @FXML
    public void goToRecords(ActionEvent args)throws IOException{
        OpenStage os = new OpenStage();
        os.openStage("Records","/Records.fxml");
        closeButtonAction();
    }

    @FXML
    void logout(ActionEvent event) {
        UserLogged userLogged = UserLogged.Instance();
        userLogged.delete();
        closeButtonAction();

    }

}
