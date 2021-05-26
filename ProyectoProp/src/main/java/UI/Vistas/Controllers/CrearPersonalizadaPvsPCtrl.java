package UI.Vistas.Controllers;

import Domain.Controllers.GameController;
import Domain.Models.Game.Board;
import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.javatuples.Pair;

import javax.swing.event.MenuDragMouseEvent;
import java.io.IOException;

public class CrearPersonalizadaPvsPCtrl {
    private static GameController gameCtrl = new GameController();
    private boolean black = true;
    private char [][] board = new char[8][8];
    private boolean isNull = true;

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
    private Label TurnLabel;


    @FXML
    private Button exitButton;

    @FXML
    private Label errorMsg;


    @FXML
    public void turnFitxa() {
        if(black) {
            TurnLabel.setText("Set the black tokens");
        }
        else {
            TurnLabel.setText("Set the white tokens");
        }

    }

    @FXML
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            Node node = (Node) event.getSource() ;
            String data = node.getId();
            int x = Integer.parseInt(String.valueOf(data.charAt(0)));
            int y = Integer.parseInt(String.valueOf(data.charAt(1)));

            if(black) {
                setCell(x,y,Color.BLACK);
                board[x][y] = 'B';
                isNull = false;
            }
            else {
                setCell(x,y,Color.WHITE);
                board[x][y] = 'W';
                isNull = false;
            }
        }
    };


    private char[][] initBoard(){

        gridpane.setStyle("-fx-background-color: seagreen;");
        bPane.setCenter(gridpane);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = '.';
            }
        }
        return board;
    }


    @FXML
    public void initialize() {
        initBoard();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                setCellPossibleMove(i,j);
            }
        }
        turnFitxa();
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


    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    public void putWhites(ActionEvent event) throws IOException {
        black = false;
        turnFitxa();
    }

    @FXML
    public void putBlacks(ActionEvent event) throws IOException {
        black = true;
        turnFitxa();
    }

    @FXML
    public void goToHomeMenu(ActionEvent event) throws IOException {
        OpenStage os = new OpenStage();
        os.openStage("Home Menu", "/HomeMenu.fxml");
        closeButtonAction();
    }


    @FXML
    public void goToGame(ActionEvent event) throws IOException {
        if(isNull){
            errorMsg.setVisible(true);
        }
        else {
            Board b = new Board(1);
            b.setBoard(board);
            gameCtrl.changeCurrentBoard(b);
            OpenStage os = new OpenStage();
            os.openStage("Game","/GamePvsP.fxml");
            closeButtonAction();
        }
    }


}

