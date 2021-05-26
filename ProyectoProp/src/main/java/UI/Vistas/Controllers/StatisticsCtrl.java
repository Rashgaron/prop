package UI.Vistas.Controllers;

import Domain.Controllers.IStatisticsController;
import Domain.Controllers.StatisticsController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class StatisticsCtrl {

    @FXML
    private Label lblWon;

    @FXML
    private Label lblLost;

    @FXML
    private Label lblWinRatio;
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

    @FXML
    public void initialize(){
        float[] statisticsData = getStatistics();
        setStatisticsOnLabels(statisticsData);
    }

    public void setStatisticsOnLabels(float[] statisticsData){
        float won = statisticsData[0];
        float lost = statisticsData[1];
        float winRatio = statisticsData[2];

        lblWon.setText(String.valueOf(won));
        lblLost.setText(String.valueOf(lost));
        lblWinRatio.setText(String.valueOf(winRatio));
    }


    private static IStatisticsController statisticCtrl = new StatisticsController();

    public static float[] getStatistics(){

        float[] estadistiques = statisticCtrl.getStatisticsArrayFromLoggedUser();

        return estadistiques;
    }

    public void setStatisticCtrl(IStatisticsController ctrl){
        statisticCtrl = ctrl;
    }


}
