package Domain.Models.Game;


import java.util.Arrays;

/**
 * Classe Board
 * @Author Diego Núñez Gimeno
 */


public class Board {

    private int ID;
    boolean blackTurn;
    private char [][] board;


    public Board(int ID){
        this.ID = ID;
        board = new char [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = '.';
            }
        }
        blackTurn = true;
    }

    public Board(int ID, char [][] boardState, boolean turn){
        this.ID = ID;
        board = boardState;
        this.blackTurn = turn;
    }

    public Board(Board board) {
        this.ID = board.ID;
        this.blackTurn = board.blackTurn;
        this.board = board.board;
    }

    public Board(char[][] aux) {
        board = aux;
    }

    public void initialize(){
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = '.';
            }
        }
        board[3][3] = 'W';
        board[4][4] = 'W';
        board[3][4] = 'B';
        board[4][3] = 'B';
    };
    public int getBlacks(){
        int count = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 'B') ++count;
            }
        }
        return count;
    };
    public int getWhites(){
        int count = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 'W') ++count;
            }
        }
        return count;
    };

    public void setCell(int i, int j, char color) {
        board[i][j] = color;
    }

    public char getCell(int i, int j) {
        return board[i][j];
    }

    public int getID() {
        return ID;
    }
    public void setID(int id){this.ID = id;}
    public char [][] getBoard() {
        return board;
    }

    public void setBoard(char [][] board) {
        this.board = board;
    }

    public void printBoard() {
        System.out.println("Taulell:");
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 8; ++i) {
            boolean primer = true;
            System.out.print(i + " ");
            for (int j = 0; j < 8; ++j) {
                if (!primer) {
                    System.out.print(' ');
                } else {
                    primer = false;
                }
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public String getBoardString(){
        String result ="";

        for(int i = 0; i < 8;i++){
            result += Arrays.toString(board[i]);
        }
        return result;
    }
    public boolean getTurn(){return blackTurn;}

    public void setTurn(boolean blackTurn){this.blackTurn = blackTurn;}
}
