package test.Domain.Models.Usuario;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Bot;
import Domain.Models.Usuario.Persona;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * Classe BotTest
 * @Author Diego Núñez Gimeno
 */


public class BotTest {
    Persona player1;
    Persona player2;
    Game game;
    Bot bot;

    @Before
    public void initialize() {
        player1 =  new Persona(1, "email1", "1");
        player2 =  new Persona(2, "email2", "2");
        game = new Game(1, new Date().getTime(), 1, player1, player2);
        game.getBoard().initialize();
        bot = new Bot(1, 1, game);
    }

    /**
     * Test per comprovar que la constructora de Bot funciona correctament.
     * Bot(1,1,game) -> ID = 1
     *                  maxDepth = 1
     *                  gameID = 1
     *
     * La constructora crea correctament un Bot amb els paràmetres indicats.
     */
    @Test
    public void constructor(){
        Bot b = new Bot(1, 1, game);
        assertEquals(1,b.getID());
        assertEquals(1, b.getBotLvl());
        assertEquals(1, b.getGame().getID());
    }


    /**
     * Tests per comprovar que la funcio bestMove que utilitza l'algorisme minimax funciona correctament
     */
    /* minimaxTest1 (profunditat 2)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0
        1
        2       o B o
        3         B B
        4       o B W
        5
        6
        7

        Bot farà el bestMove a la posició (4,2) ja que és el millor moviment possible amb profunditat 2.
     */
    @Test
    public void minimaxTest1 () {
        game.calculamove(player1);
        game.move(player1, 2, 3);
        game.getBoard().setTurn(false);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=4, y=2
        assertEquals(4, (int)p.getValue0());
        assertEquals(2, (int)p.getValue1());
    }

    /* minimaxTest2 (profunditat 2)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0   W B B B B B o
        1     W
        2
        3
        4
        5
        6
        7

        Bot farà el bestMove a la posició (0,6) ja que és el millor moviment possible amb profunditat 2.
     */
    @Test
    public void minimaxTest2 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[0][0] = 'W';
        taula[0][1] = 'B';
        taula[0][2] = 'W';
        taula[0][3] = 'W';
        taula[0][4] = 'W';
        taula[1][1] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 0, 5);
        game.getBoard().setTurn(false);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=0, y=6
        assertEquals(0, (int)p.getValue0());
        assertEquals(6, (int)p.getValue1());
    }


    /* minimaxTest3 (profunditat 3)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0     o   o
        1   W B B B o
        2     W
        3
        4
        5
        6
        7

        Bot farà el bestMove a la posició (1,4) ja que és el millor moviment possible  amb profunditat 3.
     */
    @Test
    public void minimaxTest3 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][0] = 'W';
        taula[1][1] = 'B';
        taula[1][2] = 'W';
        taula[2][1] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 1, 3);
        game.getBoard().setTurn(false);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 3: x=1, y=4
        assertEquals(1, (int)p.getValue0());
        assertEquals(4, (int)p.getValue1());
    }

    /* minimaxTest4 (profunditat 4)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0                 B
        1             W B o
        2             B
        3             B
        4     W B B B o
        5
        6
        7

        Bot farà el bestMove a la posició (4,5) ja que és el millor moviment possible  amb profunditat 4.
     */
    @Test
    public void minimaxTest4 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][5] = 'W';
        taula[1][6] = 'W';
        taula[2][5] = 'B';
        taula[3][5] = 'B';
        taula[4][4] = 'B';
        taula[4][3] = 'B';
        taula[4][1] = 'W';
        taula[4][2] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 0, 7);
        game.getBoard().setTurn(false);
        bot.setBotLvl(4);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 4: x=4, y=5
        assertEquals(4, (int)p.getValue0());
        assertEquals(5, (int)p.getValue1());
    }

    /* minimaxTest5 (profunditat 2)
    Taulell inicial després del move de player2:
        0 1 2 3 4 5 6 7
    0   B W W W W W o
    1     B
    2
    3
    4
    5
    6
    7

    Bot farà el bestMove a la posició (0,6) ja que és el millor moviment possible amb profunditat 2.
 */
    @Test
    public void minimaxTest5 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[0][0] = 'B';
        taula[0][1] = 'W';
        taula[0][2] = 'B';
        taula[0][3] = 'B';
        taula[0][4] = 'B';
        taula[1][1] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 0, 5);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=0, y=6
        assertEquals(0, (int)p.getValue0());
        assertEquals(6, (int)p.getValue1());
    }

    /* minimaxTest6 (profunditat 3)
       Taulell inicial després del move de player2:
           0 1 2 3 4 5 6 7
       0     o   o
       1   B W W W o
       2     B
       3
       4
       5
       6
       7

       Bot farà el bestMove a la posició (1,4) ja que és el millor moviment possible  amb profunditat 3.
    */
    @Test
    public void minimaxTest6 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][0] = 'B';
        taula[1][1] = 'W';
        taula[1][2] = 'B';
        taula[2][1] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 1, 3);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 3: x=1, y=4
        assertEquals(1, (int)p.getValue0());
        assertEquals(4, (int)p.getValue1());
    }
    /* minimaxTest7 (profunditat 4)
            Taulell inicial després del move de player2:
                0 1 2 3 4 5 6 7
            0                 W
            1             B W o
            2             W
            3             W
            4     B W W W o
            5
            6
            7

            Bot farà el bestMove a la posició (4,5) ja que és el millor moviment possible  amb profunditat 4.
         */
    @Test
    public void minimaxTest7 () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][5] = 'B';
        taula[1][6] = 'B';
        taula[2][5] = 'W';
        taula[3][5] = 'W';
        taula[4][4] = 'W';
        taula[4][3] = 'W';
        taula[4][1] = 'B';
        taula[4][2] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 0, 7);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMove(game);
        //Esperem el millor moviment possible trobat amb profunditat 4: x=4, y=5
        assertEquals(4, (int)p.getValue0());
        assertEquals(5, (int)p.getValue1());
    }

    @Test
    public void minimaxTest1Poda () {
        game.calculamove(player1);
        game.move(player1, 2, 3);
        game.getBoard().setTurn(false);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=4, y=2
        assertEquals(4, (int)p.getValue0());
        assertEquals(2, (int)p.getValue1());
    }

    /* minimaxTest2 (profunditat 2)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0   W B B B B B o
        1     W
        2
        3
        4
        5
        6
        7

        Bot farà el bestMove a la posició (0,6) ja que és el millor moviment possible amb profunditat 2.
     */
    @Test
    public void minimaxTest2Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[0][0] = 'W';
        taula[0][1] = 'B';
        taula[0][2] = 'W';
        taula[0][3] = 'W';
        taula[0][4] = 'W';
        taula[1][1] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 0, 5);
        game.getBoard().setTurn(false);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=0, y=6
        assertEquals(0, (int)p.getValue0());
        assertEquals(6, (int)p.getValue1());
    }


    /* minimaxTest3 (profunditat 3)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0     o   o
        1   W B B B o
        2     W
        3
        4
        5
        6
        7

        Bot farà el bestMove a la posició (1,4) ja que és el millor moviment possible  amb profunditat 3.
     */
    @Test
    public void minimaxTest3Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][0] = 'W';
        taula[1][1] = 'B';
        taula[1][2] = 'W';
        taula[2][1] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 1, 3);
        game.getBoard().setTurn(false);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 3: x=1, y=4
        assertEquals(1, (int)p.getValue0());
        assertEquals(4, (int)p.getValue1());
    }

    /* minimaxTest4 (profunditat 4)
        Taulell inicial després del move de player1:
            0 1 2 3 4 5 6 7
        0                 B
        1             W B o
        2             B
        3             B
        4     W B B B o
        5
        6
        7

        Bot farà el bestMove a la posició (4,5) ja que és el millor moviment possible  amb profunditat 4.
     */
    @Test
    public void minimaxTest4Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][5] = 'W';
        taula[1][6] = 'W';
        taula[2][5] = 'B';
        taula[3][5] = 'B';
        taula[4][4] = 'B';
        taula[4][3] = 'B';
        taula[4][1] = 'W';
        taula[4][2] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player1);
        game.move(player1, 0, 7);
        game.getBoard().setTurn(false);
        bot.setBotLvl(4);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 4: x=4, y=5
        assertEquals(4, (int)p.getValue0());
        assertEquals(5, (int)p.getValue1());
    }

    /* minimaxTest5 (profunditat 2)
    Taulell inicial després del move de player2:
        0 1 2 3 4 5 6 7
    0   B W W W W W o
    1     B
    2
    3
    4
    5
    6
    7

    Bot farà el bestMove a la posició (0,6) ja que és el millor moviment possible amb profunditat 2.
 */
    @Test
    public void minimaxTest5Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[0][0] = 'B';
        taula[0][1] = 'W';
        taula[0][2] = 'B';
        taula[0][3] = 'B';
        taula[0][4] = 'B';
        taula[1][1] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 0, 5);
        bot.setBotLvl(2);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 2: x=0, y=6
        assertEquals(0, (int)p.getValue0());
        assertEquals(6, (int)p.getValue1());
    }

    /* minimaxTest6 (profunditat 3)
       Taulell inicial després del move de player2:
           0 1 2 3 4 5 6 7
       0     o   o
       1   B W W W o
       2     B
       3
       4
       5
       6
       7

       Bot farà el bestMove a la posició (1,4) ja que és el millor moviment possible  amb profunditat 3.
    */
    @Test
    public void minimaxTest6Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][0] = 'B';
        taula[1][1] = 'W';
        taula[1][2] = 'B';
        taula[2][1] = 'B';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 1, 3);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 3: x=1, y=4
        assertEquals(1, (int)p.getValue0());
        assertEquals(4, (int)p.getValue1());
    }
    /* minimaxTest7 (profunditat 4)
            Taulell inicial després del move de player2:
                0 1 2 3 4 5 6 7
            0                 W
            1             B W o
            2             W
            3             W
            4     B W W W o
            5
            6
            7

            Bot farà el bestMove a la posició (4,5) ja que és el millor moviment possible  amb profunditat 4.
         */
    @Test
    public void minimaxTest7Poda () {
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = '.';
            }
        }
        taula[1][5] = 'B';
        taula[1][6] = 'B';
        taula[2][5] = 'W';
        taula[3][5] = 'W';
        taula[4][4] = 'W';
        taula[4][3] = 'W';
        taula[4][1] = 'B';
        taula[4][2] = 'W';
        Board b = new Board(-1);
        b.setBoard(taula);
        game.setBoard(b);
        game.calculamove(player2);
        game.move(player2, 0, 7);
        bot.setBotLvl(3);
        Pair<Integer, Integer> p = bot.bestMovePoda(game);
        //Esperem el millor moviment possible trobat amb profunditat 4: x=4, y=5
        assertEquals(4, (int)p.getValue0());
        assertEquals(5, (int)p.getValue1());
    }
    /**
     * Test per comprovar que la geter de email.
     */
    @Test
    public void geterEmail(){
        Bot b = new Bot(1, 1, game);
        //Com és un Bot el seu email és: Bot
        assertEquals("Bot", b.getEmail());
    }

    /**
     * Test per comprovar que la geter de BotLvl.
     */
    @Test
    public void geterLevel(){
        Bot b = new Bot(1, 4, game);
        //El nivell de Bot hauria de ser 4.
        assertEquals(4, b.getBotLvl());
    }

    /**
     * Test per comprovar que la geter de Game.
     */
    @Test
    public void geterGame(){
        Bot b = new Bot(1, 4, game);
        //El ID del Game hauria de ser 1 ja que game  (creat al initialize()) té ID 1.
        assertEquals(1, b.getGame().getID());
    }

    /**
     * Test per comprovar que la seter de BotLvl.
     */
    @Test
    public void seterLevel(){
        Bot b = new Bot(1, 4, game);
        b.setBotLvl(8);
        //El nivell de Bot hauria de ser 8.
        assertEquals(8, b.getBotLvl());
    }
}
