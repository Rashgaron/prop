package Domain.Controllers;

import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Player;

/**
 * @author Daniel Rodriguez
 *
 * Este Singleton se ha creado para mantener una instancia del game actual que se está jugando
 */
public class CurrentGame {

    private Game currentGame;
    private static CurrentGame _instance = null;

    public static CurrentGame Instance(){
        if(_instance == null){
            _instance = new CurrentGame();
        }
        return _instance;
    }
    public void setCurrentGame(Game currentGame){
        this.currentGame = currentGame;
   }

    public Game getCurrentGame(){
        return currentGame;
   }
    public Board getBoard(){
        return currentGame.getBoard();
   }
    public Player getWhitePlayer() { return currentGame.getWhitePlayer();}
    public Player getBlackPlayer() { return currentGame.getBlackPlayer();}
    public boolean blackTurn() {
        return currentGame.getBoard().getTurn();
    }
}
