package UI.Controllers;

import Domain.Controllers.GameController;
import Domain.Controllers.RankingController;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Ranking;
import UI.Vistas.Controllers.helpers.ContinueGame;
import javafx.collections.ObservableList;

import java.util.Scanner;

/**
 * @author Daniel Rodriguez
 */
public class UIGameController {

    public static GameController gameController = new GameController();

    public static ObservableList<ContinueGame> getGameVistes() {
        ObservableList<ContinueGame> cont = gameController.getGamesVistes();
        return cont;
    }

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }


    public static void createGame(){
        Scanner scan = new Scanner(System.in);
        int opt = -1;

        try{
            opt = scan.nextInt();
            boolean validOp = false;
            while(!validOp) {
                validOp = true;
                switch (opt) {
                    case 1:
                        System.out.println("Awesome! You have friends!");
                        System.out.println("Please, enter your friend email:");
                        scan.nextLine();
                        String secondPlayerID = scan.nextLine();
                        createGame(secondPlayerID);

                        break;
                    case 2:
                        boolean notCorrectIALevel = true;
                        int iaLvl = 0;
                        while (notCorrectIALevel) {
                            System.out.println("Awesome! You want to practice a little bit to beat your friends !");
                            System.out.println("Let's start immediately");
                            System.out.println("Select de IA lvl");
                            System.out.println("1.- Easy peasy lemon squeeze");
                            System.out.println("2.- Not so easy");
                            System.out.println("3.- Crazy mode");
                            iaLvl = scan.nextInt();

                            if (iaLvl == 1 || iaLvl == 2 || iaLvl == 3) {
                                notCorrectIALevel = false;
                            }
                        }
                        createGame(String.valueOf(iaLvl));
                        break;
                    case 3:
                        createBotGame("1", "3");
                        break;
                    case 4:
                        break;
                    default:
                        validOp = false;
                        break;

                }
            }
        } catch (Exception e) {
            System.out.println("Please, enter a valid opt ...");
            createGame();
        }


    }


    public static boolean createGame(String secondPlayerId) {
        int gameID = gameController.createGame(secondPlayerId);
        gameController.setCurrentGame(gameID);
        gameController.play(gameID);
        return true;
    }


    public static boolean createBotGame(String black, String white) {
        int gameID = gameController.createBotGame(black, white);
        gameController.setCurrentGame(gameID);
        gameController.play(gameID);

        return true;
    }

    public static String getGames(){
        String games = gameController.getGames();

        return games;
    }
    public static boolean chargeSelectedGame(int selectedGame){
        int gameID = gameController.getSelectedGameID(selectedGame);
        if(gameID == -1) return false;
        gameController.setCurrentGame(gameID);
        gameController.play(gameID);
        return true;

    }

    public Game setCurrentGame(int gameID) {

        gameController.setCurrentGame(gameID);
        Game current = gameController.getCurrentGame();

        return current;
    }
}
