package Domain.DataControllers;

import Domain.Models.Game.Board;
import Exceptions.BoardDoesNotExists;

/**
 * Esta clase se encarga de mantener la persistencia de board
 * @author Daniel Rodriguez
 */

public interface ICtrlBoard {
    /**
     * Dados unos parámetros de entrada, se guarda la board
     * @param boardID
     * @param boardState
     * @param turn
     * @return
     */
    public boolean saveBoard(int boardID, char[][] boardState, boolean turn);

    /**
     * Dado un id, se devuelve la board seleccionada o una excepción
     * @param boardID
     * @return
     * @throws BoardDoesNotExists
     */
    public Board getBoard(int boardID) throws BoardDoesNotExists;

    /**
     * Si existe la boardID, se elimina de bd
     * @param boardID
     * @return
     */
    public boolean deleteBoard(int boardID);

}
