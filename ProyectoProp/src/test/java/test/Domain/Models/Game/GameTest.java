package test.Domain.Models.Game;
import Domain.Models.Game.Game;

import Domain.Models.Usuario.Persona;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void constructor(){
        Game joc = new Game(1, new Date().getTime(), 1, player1, player2);
        assertEquals(1, joc.getID());
    }
    Persona player1;
    Persona player2;
    Game game;
    @Before
    public void initialize() {
        player1 =  new Persona(1, "email1", "1");
        player2 =  new Persona(2, "email2", "2");
        game = new Game(1, new Date().getTime(), 1, player1, player2);
        game.getBoard().initialize();
    }

    @Test
    public void calculamoveTest1() {
        game.calculamove(game.getBlackPlayer());
        boolean [][] taula  = new boolean [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = false;
            }
        }
        taula[2][3] = true;
        taula[3][2] = true;
        taula[4][5] = true;
        taula[5][4] = true;
        assertArrayEquals(taula, game.getTaulell());
    }

    @Test
    public void calculamoveTestSenseMov() { //provar situació sense moviments possibles
        char [][] b = new char [8][8];
        for (int i = 0; i < 8; ++i) { // taula de tot blanques
            for (int j = 0; j < 8; ++j) {
                b[i][j] = 'W';
            }
        }
        game.getBoard().setBoard(b);
        game.calculamove(game.getBlackPlayer());
        boolean [][] taula  = new boolean [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = false;
            }
        }
        assertArrayEquals(taula, game.getTaulell());
    }

    @Test
    public void moveTest1() {
        game.calculamove(game.getBlackPlayer());
        game.move(game.getBlackPlayer(), 2, 3);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[4][4] = 'W';
        taula[3][4] = 'B';
        taula[3][3] = 'B';
        taula[2][3] = 'B';
        taula[4][3] = 'B';
        assertArrayEquals(taula, game.getBoard().getBoard());
    }

    @Test
    public void moveTest2() { // Test dues jugades
        game.calculamove(game.getBlackPlayer());
        game.move(game.getBlackPlayer(), 2, 3);
        game.calculamove(game.getWhitePlayer());
        game.move(game.getWhitePlayer(), 4, 2);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[4][4] = 'W';
        taula[3][4] = 'B';
        taula[3][3] = 'B';
        taula[2][3] = 'B';
        taula[4][3] = 'W';
        taula[4][2] = 'W';
        assertArrayEquals(taula, game.getBoard().getBoard());
    }

    @Test
    public void moveTest3() {
        game.calculamove(game.getBlackPlayer());
        game.move(game.getBlackPlayer(), 2, 3);
        game.calculamove(game.getWhitePlayer());
        game.move(game.getWhitePlayer(), 2, 4);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[2][4] = 'W';
        taula[4][4] = 'W';
        taula[3][4] = 'W';
        taula[3][3] = 'B';
        taula[2][3] = 'B';
        taula[4][3] = 'B';
        assertArrayEquals(taula, game.getBoard().getBoard());
    }

    @Test
    public void moveTestIncorrecte() {
        game.calculamove(game.getBlackPlayer());
        game.move(game.getBlackPlayer(), 0, 0);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[3][3] = 'W';
        taula[4][4] = 'W';
        taula[3][4] = 'B';
        taula[4][3] = 'B';
        assertArrayEquals(taula, game.getBoard().getBoard());
    }

}
