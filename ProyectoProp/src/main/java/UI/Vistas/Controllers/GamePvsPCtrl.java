package UI.Vistas.Controllers;

import Domain.Controllers.GameController;
import UI.Vistas.Controllers.helpers.OpenStage;
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
import org.javatuples.Pair;

import java.io.IOException;

public class GamePvsPCtrl {
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
    private Button exitButton;

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
            TurnLabel.setText(blackPlayerEmail + "'s turn");
            cercle1.setFill(Color.BLACK);
            cercle2.setFill(Color.BLACK);
        }
        else {
            TurnLabel.setText(whitePlayerEmail + "'s turn");
            cercle1.setFill(Color.WHITE);
            cercle2.setFill(Color.WHITE);
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
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {

            Node node = (Node) event.getSource() ;
            String data = node.getId();
            int x = Integer.parseInt(String.valueOf(data.charAt(0)));
            int y = Integer.parseInt(String.valueOf(data.charAt(1)));
            char [][] board = gameCtrl.playP(new Pair<Integer,Integer>(y,x));

            print(board, x, y);
            scores();
            turnGame();
            boolean finished = gameCtrl.isGameFinished();
            endTheGameIfFinished(finished);
        }
    };

    public void endTheGameIfFinished(boolean finished){
        if(finished){
            String winner = gameCtrl.getWinner();
            gameCtrl.finishTheGame();
            TurnLabel.setText(winner.toUpperCase() + " WON!");
            TurnLabel.setStyle("-fx-font-size:30px; " +
                    "-fx-font-family: 'AR BLANCA' ");
            cercle1.setFill(Color.DARKRED);
            cercle2.setFill(Color.DARKRED);
        }
    }

    private char[][] initBoard(){

        gridpane.setStyle("-fx-background-color: seagreen;");
        bPane.setCenter(gridpane);
        char [][] board = gameCtrl.getBoardCurrentGame();

        return board;
    }


    @FXML
    public void initialize() {

        char [][] board = initBoard();

        initPlayersEmails();
        print(board, -1, -1);
        scores();
        turnGame();

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

    private void setCellPossibleMove(int i, int j){
        Button b = new Button();
        b.setId(i + Integer.toString(j));
        b.setOnAction(event);
        b.setStyle(cellStyle);
        GridPane.setConstraints(b, i, j);
        GridPane.setHalignment(b, HPos.CENTER);
        gridpane.add(b, j, i);
    }

    public void resetCell(int i, int j){
        Circle c = new Circle(0,0,25);
        c.setFill(Color.SEAGREEN);
        c.setStroke(Color.SEAGREEN);
        GridPane.setConstraints(c, j, i);
        GridPane.setHalignment(c, HPos.CENTER);
        gridpane.add(c,j,i);
    }

    public class boardCells{
        public static final char whiteCell = 'W';
        public static final char blackCell = 'B';
        public static final char possibleMoveCell = 'o';
    }

    private void print(char [][] board, int x, int y) {

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == boardCells.whiteCell) {

                    if(x == i && y == j) setUltimMovimient(i,j,Color.WHITE);
                    else setCell(i,j,Color.WHITE);

                } else if (board[i][j] == boardCells.blackCell) {

                    if(x == i && y == j) setUltimMovimient(i,j,Color.BLACK);
                    else setCell(i,j,Color.BLACK);

                } else if (board[i][j] == boardCells.possibleMoveCell) {

                    setCellPossibleMove(i,j);

                }else{

                    resetCell(i,j);

                }

            }
        }
    }

    @FXML
    private void closeButtonAction(){
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
