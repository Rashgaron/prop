package UI.Vistas.Controllers;


import Domain.Controllers.CurrentGame;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Ranking;
import Exceptions.PersonaDoesNotExists;
import UI.Controllers.UIGameController;
import UI.Controllers.UiCtrlRanking;
import UI.Vistas.Controllers.helpers.ContinueGame;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Domain.Controllers.GameController;



import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class ContinueGameCtrl implements Initializable {
    

      @FXML
    private TableView<ContinueGame> table;

    @FXML
    private TableColumn<ContinueGame, Integer> position;

    @FXML
    private TableColumn<ContinueGame, String> white;

    @FXML
    private TableColumn<ContinueGame, String> black;

    @FXML
    private TableColumn<ContinueGame, Date> data;

    @FXML
    private Button closeButton;

    @FXML
    private Button selection;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    position.setCellValueFactory(new PropertyValueFactory<>("position"));
    white.setCellValueFactory(new PropertyValueFactory<>("white"));
    black.setCellValueFactory(new PropertyValueFactory<>("black"));
    data.setCellValueFactory(new PropertyValueFactory<>("data"));

    table.setItems(UIGameController.getGameVistes());
    }

    public void selectionTable(MouseEvent args) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {

            ContinueGame cont = table.getSelectionModel().getSelectedItem();
            UIGameController uiGameController = new UIGameController();
            Game current = uiGameController.setCurrentGame(cont.getGameID());

            Boolean botWhite = (current.getWhitePlayer().getID() >= 1 && current.getWhitePlayer().getID() <= 3);
            Boolean botBlack = (current.getBlackPlayer().getID() >= 1 && current.getBlackPlayer().getID() <= 3);

            OpenStage os = new OpenStage();

            if (botWhite && botBlack) os.openStage("CrearGameCPUvsCPU","/GamePersonalitzatCPUvsCPU.fxml");
            else if (!botWhite && !botBlack) os.openStage("Game", "/GamePvsP.fxml");
            else os.openStage("Game", "/GamePersonalitzatPvsCPU.fxml");

            closeButtonAction();

        }
    }
}
