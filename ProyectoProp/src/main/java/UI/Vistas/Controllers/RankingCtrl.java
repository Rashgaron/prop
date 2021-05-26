package UI.Vistas.Controllers;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


import Domain.Models.Usuario.Ranking;
import Exceptions.PersonaDoesNotExists;
import UI.Controllers.UiCtrlRanking;
import UI.Vistas.Controllers.helpers.OpenStage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class RankingCtrl implements Initializable {
    

      @FXML
    private TableView<Ranking> table;

    @FXML
    private TableColumn<Ranking, Integer> position;

    @FXML
    private TableColumn<Ranking, String> email;

    @FXML
    private TableColumn<Ranking, Float> wins;

    @FXML
    private TableColumn<Ranking, Float> losses;

    @FXML
    private TableColumn<Ranking, Float> winratio;

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
    public void goToHomeMenu(ActionEvent args) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Home Menu", "/HomeMenu.fxml");
        closeButtonAction();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        losses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        winratio.setCellValueFactory(new PropertyValueFactory<>("winratio"));

        try {
            table.setItems(UiCtrlRanking.getRanking());
        } catch (PersonaDoesNotExists personaDoesNotExists) {
            personaDoesNotExists.printStackTrace();
        }
    }
}
