package Domain.Controllers;

import DataBase.Controllers.CtrlGame;
import DataBase.Controllers.CtrlPersona;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Player;
import Domain.Models.Usuario.Record;
import org.javatuples.Pair;

/**
 * Controlador de la clase record
 * @author Daniel Rodriguez
 */
public class RecordController {

    private Record record;

    public RecordController(){
        this.record = new Record(new CtrlGame(), new CtrlPersona());
    }


    /**
     * Se obtienen los datos de la mejor puntuación. Player email y puntuación
     * @return
     */
    public Pair<String, Integer> getBestPunctuation(){
        Pair<Player, Integer> bestPunctuation = record.getBestPunctuation();

        Pair<String, Integer> result = null;

        Player playerWithBestPunctuation = bestPunctuation.getValue0();

        if( playerWithBestPunctuation != null){
            String playerEmail = playerWithBestPunctuation.getEmail();
            int punctuation = bestPunctuation.getValue1();

            result = new Pair<String, Integer>(playerEmail, punctuation);
        }

        return result;
    }

    /**
     * Se obtienen los datos de la partida con más diferencia de puntuación
     * @return
     */
    public Pair<Integer, Integer> getGameWithGreaterDifferenceOfPunctuation(){
        Pair<Game, Integer> res = record.getGameWithGreaterDifferenceOfPunctuation();

        Game resultGame = res.getValue0();
        Pair<Integer, Integer> result = null;

        if(resultGame != null){
            int gameID = resultGame.getID();
            int greaterDifference = res.getValue1();

            result = new Pair<Integer, Integer>(gameID, greaterDifference);

        }

        return result;
    }


    /**
     * Funcion para inyectar dependencias en los test
     * @param r
     */
    public void setRecord(Record r){
        this.record = r;
    }
}
