package test.Domain.Models.Game;
import Domain.Models.Game.Board;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void constructor(){
        Board board = new Board(1);
        assertEquals(1, board.getID());
    }

    Board board;
    @Test
    public void initBoard(){
        board = new Board(1);
        board.initialize();
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
        assertArrayEquals(taula, board.getBoard());
    }
    @Test
    public void nWhites () {
        Board t = new Board(1);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = 'B';
            }
        }
        t.setBoard(taula);
        assertEquals(0, t.getWhites());
    }

    @Test
    public void nBlacks () {
        Board t = new Board(1);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = 'B';
            }
        }
        t.setBoard(taula);
        assertEquals(64, t.getBlacks());
    }

    @Test
    public void setBoard() {
        Board t = new Board(1);
        char [][] taula  = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = 'B';
            }
        }
        t.setBoard(taula);
        assertArrayEquals(taula, t.getBoard());
    }
    @Test
    public void setgetCell() {
        Board t = new Board(1);
        char [][] taula = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taula[i][j] = 'B';
            }
        }
        t.setBoard(taula);
        t.setCell(0, 0, 'W');
        assertEquals('W', t.getCell(0, 0));
    }
}
