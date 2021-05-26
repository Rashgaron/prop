package UI.Vistas.Controllers;

import Domain.Controllers.GameController;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameCPUvsCPUCtrl {
    private static GameController gameCtrl = new GameController();

    private final String cellStyle = "-fx-background-radius: 20em; " +
            "-fx-min-width: 30px; " +
            "-fx-min-height: 30px; " +
            "-fx-max-width: 40px; " +
            "-fx-max-height: 40px;" +
            "-fx-border-radius: 22em;" +
            "-fx-border-width: 1px;" +
            "-fx-background-color: darkseagreen;" +
            "-fx-border-color: black;";

    Stage window;

    @FXML
    GridPane gridpane;

    @FXML
    BorderPane bPane;

    @FXML
    private Label idWhite;

    @FXML
    private Label idBlack;

    @FXML
    private Label whiteCount;

    @FXML
    private Label blackCount;

    @FXML
    private Label gameTitle;

    @FXML
    private Label TurnLabel;

    @FXML
    private Circle cercle1;

    @FXML
    private Circle cercle2;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button exitButton;

    Timeline timeline;

    boolean pause = false;

    boolean start = true;

    boolean initialboard;

    @FXML
    public void initPlayersEmails() {
        Pair<String,String> emails = gameCtrl.getEmailPlayers();
        String whitePlayerEmail = emails.getValue0();
        String blackPlayerEmail = emails.getValue1();

        idWhite.setText(whitePlayerEmail);
        idBlack.setText(blackPlayerEmail);

    }

    @FXML
    public void turnGame() {
        boolean blackTurn = gameCtrl.getTurn();
        Pair<String,String> emails = gameCtrl.getEmailPlayers();
        String whitePlayerEmail = emails.getValue0();
        String blackPlayerEmail = emails.getValue1();
        if(blackTurn) {
            TurnLabel.setText("Black " + blackPlayerEmail.toLowerCase() + "'s turn");
            cercle1.setFill(Color.BLACK);
            cercle2.setFill(Color.BLACK);
        }
        else {
            TurnLabel.setText("White " + whitePlayerEmail.toLowerCase() + "'s turn");
            cercle1.setFill(Color.WHITE);
            cercle2.setFill(Color.WHITE);
        }

    }

    @FXML
    public void turnGameInverse() {
        boolean blackTurn = gameCtrl.getTurn();
        Pair<String,String> emails = gameCtrl.getEmailPlayers();
        String whitePlayerEmail = emails.getValue0();
        String blackPlayerEmail = emails.getValue1();
        if(blackTurn) {
            TurnLabel.setText(blackPlayerEmail + "'s turn");
            cercle1.setFill(Color.WHITE);
            cercle2.setFill(Color.WHITE);
        }
        else {
            TurnLabel.setText(whitePlayerEmail + "'s turn");
            cercle1.setFill(Color.BLACK);
            cercle2.setFill(Color.BLACK);
        }

    }

    @FXML
    public boolean scores() {
        Pair<Integer, Integer> puntuations = gameCtrl.getPuntuations();

        whiteCount.setText(String.valueOf(puntuations.getValue0()));
        blackCount.setText(String.valueOf(puntuations.getValue1()));

        if((puntuations.getValue0() + puntuations.getValue1() == 64)){
            return true;
        }
        return false;

    }

    @FXML
    private void startGame(){
        if (start) {
            start = false;
            timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                if (initialboard) {
                    char[][] board = initBoard();
                    Pair<Integer, Integer> p = gameCtrl.getUltimMov();
                    print(board, p.getValue0(), p.getValue1());
                    scores();
                    turnGame();
                    boolean finished = gameCtrl.isGameFinished();
                    endTheGameIfFinished(finished);
                    initialboard = false;
                } else {
                    char[][] board = gameCtrl.playCPU();
                    Pair<Integer, Integer> p = gameCtrl.getUltimMov();
                    print(board, p.getValue0(), p.getValue1());
                    scores();
                    turnGame();
                    boolean finished = gameCtrl.isGameFinished();
                    endTheGameIfFinished(finished);
                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    private void finishGame(){
        if(timeline != null) timeline.stop();
        boolean finished = gameCtrl.isGameFinished();
        while (!finished) {
            char[][] board = gameCtrl.playCPU();
            Pair<Integer, Integer> p = gameCtrl.getUltimMov();
            print(board, p.getValue0(), p.getValue1());
            scores();
            turnGame();
            finished = gameCtrl.isGameFinished();
            endTheGameIfFinished(finished);
        }
    }

    @FXML
    private void pauseGame(){
        if(timeline != null) {
            if (!pause) {
                pauseButton.setText("Resume");
                timeline.pause();
                pause = true;
            } else {
                pauseButton.setText("Pause");
                timeline.play();
                pause = false;
            }
        };
    }

    public void endTheGameIfFinished(boolean finished){
        if(finished){
            String winner = gameCtrl.getWinner();
            gameCtrl.finishTheGame();
            Pair<Integer, Integer> score = gameCtrl.getPuntuations();
            if (score.getValue0() > score.getValue1()) TurnLabel.setText("WHITE " + winner.toUpperCase() + " WON!");
            else if(score.getValue0() < score.getValue1()) TurnLabel.setText("BLACK " + winner.toUpperCase() + " WON!");
            else TurnLabel.setText("IT'S A DRAW!");
            TurnLabel.setStyle("-fx-font-size:25px; " +
                    "-fx-font-family: 'AR BLANCA' ");
            cercle1.setFill(Color.DARKRED);
            cercle2.setFill(Color.DARKRED);
            if(timeline != null) timeline.stop();
        }
    }

    private char[][] firstBoard () {
        char [][] b = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if ((i == 3 && j == 3) || (i == 4 && j == 4)) b[i][j] = GamePvsPCtrl.boardCells.whiteCell;
                else if((i == 3 && j == 4) || (i == 4 && j == 3)) b[i][j] = GamePvsPCtrl.boardCells.blackCell;
                else if ((i == 2 && j == 3) || (i == 3 && j == 2)
                || (i == 4 && j == 5) || (i == 5 && j == 4)) b[i][j] = GamePvsPCtrl.boardCells.possibleMoveCell;
                else b[i][j] = '.';
            }
        }
        return b;
    }

    private char[][] initBoard(){

        gridpane.setStyle("-fx-background-color: seagreen;");
        bPane.setCenter(gridpane);
        char [][] board = gameCtrl.getBoardCurrentGame();

        boolean finished = gameCtrl.isGameFinished();
        endTheGameIfFinished(finished);

        return board;
    }


    @FXML
    public void initialize() {

        char [][] board = initBoard();
        initPlayersEmails();
        if (board[2][3] == GamePvsPCtrl.boardCells.blackCell || board[3][2] == GamePvsPCtrl.boardCells.blackCell
        || board[4][5] == GamePvsPCtrl.boardCells.blackCell|| board[5][4] == GamePvsPCtrl.boardCells.blackCell) {
            print(firstBoard(), -1, -1);
            turnGameInverse();
            initialboard = true;
        } else {
            Pair<Integer, Integer> p = gameCtrl.getUltimMov();
            print(board, p.getValue0(), p.getValue1());
            turnGame();
            initialboard = false;
        }
        scores();


    }

    private void setUltimMovimient(int i, int j, Color color) {
        setCell(i,j,color);
        Circle c = new Circle(0,0,5 );
        c.setFill(Color.RED);
        c.setStroke(color);
        GridPane.setConstraints(c, i, j);
        GridPane.setHalignment(c, HPos.CENTER);
        gridpane.add(c, j, i);
    }


    private void setCell(int i, int j, Color color){
        Circle c = new Circle(0, 0, 20);
        c.setFill(color);
        c.setStroke(color);
        GridPane.setConstraints(c, i, j);
        GridPane.setHalignment(c, HPos.CENTER);
        gridpane.add(c, j, i);
    }

    private void setCellPossibleMoveNotPlayable(int i, int j){
        Circle c = new Circle(0,0,20);
        c.setFill(Color.DARKSEAGREEN);
        c.setStroke(Color.BLACK);
        GridPane.setConstraints(c, i, j);
        GridPane.setHalignment(c, HPos.CENTER);
        gridpane.add(c, j, i);
    }

    public void resetCell(int i, int j){
        Circle c = new Circle(0,0,25);
        c.setFill(Color.SEAGREEN);
        c.setStroke(Color.SEAGREEN);
        GridPane.setConstraints(c, j, i);
        GridPane.setHalignment(c, HPos.CENTER);
        gridpane.add(c,j,i);
    }

    private void print(char [][] board, int x, int y) {

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == GamePvsPCtrl.boardCells.whiteCell) {

                    if(x == i && y == j) setUltimMovimient(i,j,Color.WHITE);
                    else setCell(i,j,Color.WHITE);

                } else if (board[i][j] == GamePvsPCtrl.boardCells.blackCell) {

                    if(x == i && y == j) setUltimMovimient(i,j,Color.BLACK);
                    else setCell(i,j,Color.BLACK);

                } else if (board[i][j] == GamePvsPCtrl.boardCells.possibleMoveCell) {

                    setCellPossibleMoveNotPlayable(i, j);

                }else{

                    resetCell(i,j);

                }

            }
        }
    }

    @FXML
    private void closeButtonAction(){
        if(timeline != null) timeline.stop();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    public void goToHomeMenu(ActionEvent event) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Home Menu", "/HomeMenu.fxml");
        closeButtonAction();
    }

}
