package Domain.Models.Usuario;

import Domain.Models.Game.Game;
import org.javatuples.Pair;

import java.util.InputMismatchException;

/**
 * @author Daniel Rodriguez
 */
public abstract class Player {

    private int ID;

    Player(int ID){
        this.ID = ID;
    }

    public boolean deleteGame(int gameID){return false;};

    public abstract Pair<Integer, Integer> makeYourMove(Game game) throws InputMismatchException;

    public int getID() {
        return ID;
    }

    public abstract String getEmail();


}
