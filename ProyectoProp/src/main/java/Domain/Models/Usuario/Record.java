package Domain.Models.Usuario;

import DataBase.Controllers.CtrlGame;
import DataBase.Controllers.CtrlPersona;
import Domain.DataControllers.ICtrlGame;
import Domain.DataControllers.ICtrlPersona;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Player;
import org.javatuples.Pair;

import java.util.ArrayList;

/**
 * Clase creada para el calculo de los records
 * @author Daniel Rodriguez
 */
public class Record {

    private Pair<Player, Integer> bestPuntuation;
    private Pair<Game, Integer> gameWithGreaterDifferenceOfPunctuation;
    private Game gameWithMoreTurns;
    private ICtrlGame ctrlGame;
    private ICtrlPersona ctrlPersona;

    /**
     * Constructora con inyeccion de dependencias para facilitar el testeo
     * @param ctrlGame
     * @param ctrlPersona
     */
    public Record(CtrlGame ctrlGame, CtrlPersona ctrlPersona){
        this.ctrlGame = ctrlGame;
        this.ctrlPersona = ctrlPersona;
    }

    /**
     * Se generan los records
     */
    public void initRecord(){
       bestPuntuation = getBestPunctuation();
       gameWithGreaterDifferenceOfPunctuation = getGameWithGreaterDifferenceOfPunctuation();
       gameWithMoreTurns = getGameWithMoreTurns();
    }

    /**
     * No implementado
     * @return
     */
    public Game getGameWithMoreTurns(){
        return null;
    }


    /**
     * Funcion que busca el player con la mayor puntuación
     * @return
     */
    public Pair<Player, Integer> getBestPunctuation(){
        ArrayList<Game> games = ctrlGame.getAllGames();

        int bestPunctuation = 0;
        Player player = null;

        for(int i = 0 ; i < games.size() ; i++){
            Board board = games.get(i).getBoard();
            if(board.getBlacks() >= bestPunctuation){

                player = games.get(i).getBlackPlayer();
                if(isNotABot(player.getID()))
                    bestPunctuation = board.getBlacks();
                else
                    player = null;
            }
            if(board.getWhites() >= bestPunctuation){
                player = games.get(i).getWhitePlayer();
                if(isNotABot(player.getID()))
                    bestPunctuation = board.getWhites();
                else
                    player = null;
            }

        }
        return new Pair<Player, Integer>(player, bestPunctuation);
    }

    /**
     * Comprueba que el player id == id, no sea bot
     * @param id
     * @return
     */
    public boolean isNotABot(int id){
        return (id >= 0 && id <= 3) ? false : true;
    }


    /**
     * Busca el game con la mayor diferencia de puntuacion entre los dos jugadores
     * @return
     */

    public Pair<Game, Integer> getGameWithGreaterDifferenceOfPunctuation(){

        ArrayList<Game> games = ctrlGame.getAllGames();
        int greaterDifferenceOfPunctuation = 0;
        Game gameWithGreaterDifferenceOfPunctuation = null;

        for(int i = 0 ; i < games.size() ; i++){
            Board board = games.get(i).getBoard();
            int possibleValue = Math.abs(board.getBlacks() - board.getWhites());
            if(possibleValue >= greaterDifferenceOfPunctuation){
                greaterDifferenceOfPunctuation = possibleValue;
                gameWithGreaterDifferenceOfPunctuation = games.get(i);
            }
        }

        return new Pair<Game, Integer>(gameWithGreaterDifferenceOfPunctuation, greaterDifferenceOfPunctuation);
    }




}
