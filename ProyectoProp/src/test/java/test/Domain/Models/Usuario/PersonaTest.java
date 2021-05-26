package test.Domain.Models.Usuario;
import static org.junit.Assert.*;

import Domain.Models.Game.Game;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Rodriguez
 */

public class PersonaTest {


    public Persona persona;
    public int gameID = 1;
    public long date = 1;
    public int boardID = 1;

    @Before
    public void initPersona(){

        persona = new Persona(1, "dannirodriguez99@gmail.com","12345");
        Game game = new Game(gameID, date, boardID, null, null);
        persona.addGame(game);
    }
    @Test
    public void constructorPersona1(){
        Persona persona = new Persona(2, "dannirodriguez99@gmail.com","12345");
        assertEquals(2, persona.getID());
        assertEquals("dannirodriguez99@gmail.com","dannirodriguez99@gmail.com");
        assertEquals("12345","12345");
    }

    @Test
    public void getSelectedGameWithGamesInside(){
        Game actual = persona.getSelectedGame(0);

        assertEquals("Different game ID", gameID, actual.getID());
    }

    @Test

    public void getGameOutOfBounds(){

        Game actual = persona.getSelectedGame(1);

        assertEquals("Game is not out of bounds", null, actual);
    }


    @Test
    public void getGameByCorrectID(){
        Game actual = persona.getGame(gameID);

        assertEquals("Error getting the game by ID", gameID, actual.getID());
    }

    @Test
    public void getGameByWrongID(){

        Game actual = persona.getGame(0);

        assertEquals("Error getting game by ID", null, actual);
    }

    @Test
    public void deleteGameThatExists(){

        Game g = new Game(0,0,0,null,null);
        persona.addGame(g);

        boolean deleted = persona.deleteGame(0);
        assertTrue("Error deleting game",deleted);
    }

    @Test
    public void deleteGameThatNotExists(){
        boolean deleted = persona.deleteGame(0);
        assertFalse("Error deleting the game", deleted);

    }

    @Test
    public void setEstadistiques(){

        Estadistiques estadistiques = new Estadistiques(persona.getID(),10,20);
        persona.setEstadistiques(estadistiques);

        assertEquals(estadistiques, persona.getEstadistiques());
    }

}
