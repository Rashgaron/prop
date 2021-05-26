package Domain.DataControllers;

import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Exceptions.GameDoesNotExists;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia de la clase Game
 * @author Daniel Rodriguez
 */
public interface ICtrlGame {
    /**
     * Dados unos parámetros de entrada, se guarda el game en fichero
      * @param gameID
     * @param data
     * @param boardID
     * @param blackPlayerID
     * @param whitePlayerID
     */
    public void saveGame(int gameID, long data, int boardID, int blackPlayerID, int whitePlayerID);

    /**
     * Dada una persona, se retorna una lista con todos sus games
     * @param user
     * @return
     */
    public ArrayList<Game> getPersonaGames(Persona user);

    /**
     * Retorna true si el game con id == gameID, existe en bd
     * @param gameID
     * @return
     */
    public boolean existsGame(int gameID);

    /**
     * Elimina el game con id == gameID
     * @param gameID
     * @return
     */
    public boolean deleteGame(int gameID);

    /**
     * Se retornan todos los games del fichero
     * @return
     */
    public ArrayList<Game> getAllGames();
}
