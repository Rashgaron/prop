package UI.Vistas.Controllers;

import Domain.Controllers.RecordController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.javatuples.Pair;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class RecordsCtrl {
    public FactoryCtrl factoryCtrl = FactoryCtrl.Instance();

    @FXML
    private Label lblPlayerEmail;

    @FXML
    private Label lblBestPunctuation;

    @FXML
    private Label lblGameID;

    @FXML
    private Label lblPunctuationDifference;

    @FXML
    private Button btnHomeMenu;

    @FXML
    private Button closeButton;

    @FXML
    void closeWindow(ActionEvent event) {
        closeButtonAction();
    }

    @FXML
    void goToHomeMenu(ActionEvent event) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Home Menu", "/HomeMenu.fxml");
        closeButtonAction();

    }

    @FXML
    public void initialize(){
        RecordController ctrlRecord = factoryCtrl.getRecordCtrl();
        loadBestPunctuation(ctrlRecord);
        loadGreatherDifferenceOfPunctuation(ctrlRecord);
    }

    public void loadGreatherDifferenceOfPunctuation(RecordController ctrlRecord){
        Pair<Integer, Integer> data = ctrlRecord.getGameWithGreaterDifferenceOfPunctuation();
        if(data != null){
            int gameID = data.getValue0();
            int greatherDifference = data.getValue1();

            lblGameID.setText(String.valueOf(gameID));
            lblPunctuationDifference.setText(String.valueOf(greatherDifference));
        }else{
            lblGameID.setText("There is no game yet");
            lblPunctuationDifference.setText("No scoring yet");
        }

    }

    public void loadBestPunctuation(RecordController ctrlRecord){
        Pair<String, Integer> data = ctrlRecord.getBestPunctuation();

        if(data != null){
            String playerEmail = data.getValue0();
            int bestPunctuation = data.getValue1();

            lblPlayerEmail.setText(playerEmail);
            lblBestPunctuation.setText(String.valueOf(bestPunctuation));
        }else{
            lblPlayerEmail.setText("No player yet");
            lblBestPunctuation.setText("No scoring yet");
        }

    }

    public void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
