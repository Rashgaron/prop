package Domain.Models.Game;

import Domain.Models.Usuario.Bot;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import java.util.*;
import java.util.LinkedList;
import org.javatuples.Pair;
import java.time.LocalDate;


/**
 * Classe Game
 * @Author Arnau Llobet Massallé & Diego Núñez Gimeno
 */



public class Game {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private int ID;
    private Player blackPlayer;
    private Player whitePlayer;
    private Board board;
    private long date;
    private boolean[][] taulell;
    private Pair <Integer, Integer> ultimMov;
    private Pair<Integer, Integer> movimentPare;


    /**
     * Constructores Game
     * @Author Arnau Llobet Massallé
     */
    public Game(Game game) {
        this.ID = game.ID;
        this.blackPlayer = game.blackPlayer;
        this.whitePlayer = game.whitePlayer;
        this.board = new Board(-1);
        char [][] aux = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                aux[i][j] = game.getBoard().getBoard()[i][j];
            }
        }
        this.board.setBoard(aux);
        this.date = game.date;
        this.taulell = game.taulell;
        this.ultimMov = game.ultimMov;
        this.movimentPare = game.movimentPare;
    }


    public Game(int ID, long date, int boardID, Player blackPlayer, Player whitePlayer){
        this.ID = ID;
        this.date = date;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        board = new Board(boardID);
        board.initialize();
        taulell =  new boolean [8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taulell[i][j] = false;
            }
        }
        ultimMov = new Pair<> (-1, -1);
        movimentPare = new Pair<> (-1, -1);
    }


    /**
     *Funció move
     * @Author Arnau Llobet Massallé
     * Retorna true si el moviment es correcte, en aquest cas
     * efectua el moviment i canvia el taulell, altrament retorna false.
     */
    public boolean move(Player player, int x, int y) {
        if (!taulell[x][y]) {
            return false;
        }
        else {
            char col = 'B';
            if (getBlackPlayer() != player) {
                col = 'W';
            }
            board.setCell(x, y, col);
            canviaBoard(col, x, y);
            ultimMov = new Pair<>(x, y);
            return true;
        }
    }

    /**
     *Funció calculamove
     * @Author Diego Núñez Gimeno
     * Calcula i actualitza el taulell de booleans amb totes les jugades
     * possibles. Si no hi ha cap moviment possible retorna false, altrament retorna true.
     */
    public boolean calculamove(Player player){

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                taulell[i][j] = false;
            }
        }
        if (getBlackPlayer() == player) {
            calculamovPossible('B');
        } else {
            calculamovPossible('W');
        }
        boolean possible = false;
        int i = 0;
        while (!possible && i < 8) {
            int j = 0;
            while (!possible && j < 8) {
                if (taulell[i][j] == true) possible = true;
                ++j;
            }
            ++i;
        }
        return possible;
    }

    /**
     *Funció calculamovPossible
     * @Author Diego Núñez Gimeno
     */
    public void calculamovPossible(char color){
        char [][] b = board.getBoard();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (b[i][j] == color) buscarPossibleMov(color, i, j);
            }
        }
    }


    /**
     *Funció buscarPossibleMov
     * @Author Diego Núñez Gimeno
     */
    private void buscarPossibleMov(char color, int x, int y) {
        if(x == 0 && y == 0) {  //Cantonada adalt esquerra
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAbaix(color, x, y);           //Abaix dreta
            buscarabaix(color, x, y);                 //Abaix
        }
        else if(x == 0 && y == 7) { //Cantonada adalt dreta
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAbaix(color, x, y);            //Abaix esquerra
            buscarabaix(color, x, y);                 //Abaix
        }
        else if(x == 7 && y == 0) { //Cantonada abaix esquerra
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAmunt(color, x, y);           //Adalt dreta
            buscaramunt(color, x, y);                 //Adalt
        }
        else if(x == 7 && y == 7) { //Cantonada abaix dreta
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAmunt(color, x, y);            //Adalt esquerra
            buscaramunt(color, x, y);                 //Adalt
        }
        else if(x == 0){ //Limit superior
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAbaix(color, x, y);           //Abaix dreta
            buscarabaix(color, x, y);                 //Abaix
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAbaix(color, x, y);            //Abaix esquerra
        }
        else if(x == 7){ //Limit inferior
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAmunt(color, x, y);           //Adalt dreta
            buscaramunt(color, x, y);                 //Adalt
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAmunt(color, x, y);            //Adalt esquerra
        }
        else if(y == 0){ //Limit esquerra
            diagonalDretAmunt(color, x, y);           //Adalt dreta
            buscaramunt(color, x, y);                 //Adalt
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAbaix(color, x, y);           //Abaix dreta
            buscarabaix(color, x, y);                 //Abaix
        }
        else if(y == 7){ //Limit dreta
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAmunt(color, x, y);            //Adalt esquerra
            buscaramunt(color, x, y);                 //Adalt
            diagonalEsqAbaix(color, x, y);            //Abaix esquerra
            buscarabaix(color, x, y);                 //Abaix
        }
        else { //Pel mig
            buscaramunt(color, x, y);                 //Adalt
            diagonalEsqAmunt(color, x, y);            //Adalt esquerra
            buscaresquerra(color, x, y);              //Esquerra
            diagonalEsqAbaix(color, x, y);            //Abaix esquerra
            buscarabaix(color, x, y);                 //Abaix
            diagonalDretAbaix(color, x, y);           //Abaix dreta
            buscardreta(color, x, y);                 //Dreta
            diagonalDretAmunt(color, x, y);           //Adalt dreta
        }
    }

    /**
     *Funcions utilitzades a buscarPossibleMov
     * @Author Diego Núñez Gimeno
     */
    private void buscardreta (char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x][y + 1] == color || b[x][y + 1] == '.') return;
        ++y;
        while (y < 8) {
            if (b[x][y] != color && b[x][y] != '.') ++y;
            else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }
    private void buscaresquerra (char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x][y - 1] == color || b[x][y - 1] == '.') return;
        --y;
        while (y >= 0) {
            if (b[x][y] != color && b[x][y] != '.') --y;
            else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }

    private void buscarabaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y] == color || b[x + 1][y] == '.') return;
        ++x;
        while (x < 8) {
            if (b[x][y] != color && b[x][y] != '.') ++x;
            else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }

    private void buscaramunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y] == color || b[x - 1][y] == '.') return;
        --x;
        while (x >= 0) {
            if (b[x][y] != color && b[x][y] != '.') --x;
            else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }

    private void diagonalEsqAmunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y - 1] == color || b[x - 1][y - 1] == '.') return;
        --x;
        --y;
        while (x >= 0 && y >= 0) {
            if (b[x][y] != color && b[x][y] != '.') {
                --x;
                --y;
            } else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }


    private void diagonalDretAbaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y + 1] == color || b[x + 1][y + 1] == '.') return;
        ++x;
        ++y;
        while (x < 8 && y < 8) {
            if (b[x][y] != color && b[x][y] != '.') {
                ++x;
                ++y;
            } else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }

    private void diagonalDretAmunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y + 1] == color || b[x - 1][y + 1] == '.') return;
        --x;
        ++y;
        while (y < 8 && x >= 0) {
            if (b[x][y] != color && b[x][y] != '.') {
                --x;
                ++y;
            } else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }

    private void diagonalEsqAbaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y - 1] == color || b[x + 1][y - 1] == '.') return;
        ++x;
        --y;
        while (y >= 0 && x < 8) {
            if (b[x][y] != color && b[x][y] != '.') {
                ++x;
                --y;
            } else if (b[x][y] == '.') {
                taulell[x][y] = true;
                return;
            }
            else return;
        }
    }


    /**
     *Funció canviaBoard
     * @Author Arnau Llobet Massallé
     * Per a cada jugada x i y, canvia l'estat actual de la board. S'encarrega de capturar fitxes del rival
     * i canviar-les de color.
     */
    private void canviaBoard(char color, int x, int y) {
        if(x == 0 && y == 0) { //Cantonada adalt esquerra
            dreta(color, x, y);              //Dreta
            DretAbaix(color, x, y);          //Abaix dreta
            abaix(color, x, y);              //Abaix
        }
        else if(x == 0 && y == 7) { //Cantonada adalt dreta
            esquerra(color, x, y);           //Esquerra
            EsqAbaix(color, x, y);           //Abaix esquerra
            abaix(color, x, y);              //Abaix
        }
        else if(x == 7 && y == 0) { //Cantonada abaix esquerra
            dreta(color, x, y);             //Dreta
            DretAmunt(color, x, y);         //Adalt dreta
            amunt(color, x, y);             //Adalt
        }
        else if(x == 7 && y == 7) { //Cantonada abaix dreta
            esquerra(color, x, y);          //Esquerra
            EsqAmunt(color, x, y);          //Adalt esquerra
            amunt(color, x, y);             //Adalt
        }
        else if(x == 0){ //Limit superior
            dreta(color, x, y);             //Dreta
            DretAbaix(color, x, y);         //Abaix dreta
            abaix(color, x, y);             //Abaix
            esquerra(color, x, y);          //Esquerra
            EsqAbaix(color, x, y);          //Abaix esquerra
        }
        else if(x == 7){ //Limit inferior
            dreta(color, x, y);             //Dreta
            DretAmunt(color, x, y);         //Adalt dreta
            amunt(color, x, y);             //Adalt
            esquerra(color, x, y);          //Esquerra
            EsqAmunt(color, x, y);          //Adalt esquerra
        }
        else if(y == 0){ //Limit esquerra
            DretAmunt(color, x, y);         //Adalt dreta
            amunt(color, x, y);             //Adalt
            dreta(color, x, y);             //Dreta
            DretAbaix(color, x, y);         //Abaix dreta
            abaix(color, x, y);             //Abaix
        }
        else if(y == 7){ //Limit dreta
            esquerra(color, x, y);          //Esquerra
            EsqAmunt(color, x, y);          //Adalt esquerra
            amunt(color, x, y);             //Adalt
            EsqAbaix(color, x, y);          //Abaix esquerra
            abaix(color, x, y);             //Abaix
        }
        else { //Pel mig
            amunt(color, x, y);             //Adalt
            EsqAmunt(color, x, y);          //Adalt esquerra
            esquerra(color, x, y);          //Esquerra
            EsqAbaix(color, x, y);          //Abaix esquerra
            abaix(color, x, y);             //Abaix
            DretAbaix(color, x, y);         //Abaix dreta
            dreta(color, x, y);             //Dreta
            DretAmunt(color, x, y);         //Adalt dreta
        }
    }


    /**
     *Funcions utilitzades a canviaBoard
     * @Author Arnau Llobet Massallé
     */
    private void dreta (char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x][y + 1] == color || b[x][y + 1] == '.') return;
        int aux = y;
        ++y;
        boolean trobat = false;
        while (!trobat && y < 8) {
            if (b[x][y] != color && b[x][y] != '.') ++y;
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (y > aux) {
                board.setCell(x, y, color);
                --y;
            }
        }
    }

    private void esquerra (char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x][y - 1] == color || b[x][y - 1] == '.') return;
        int aux = y;
        --y;
        boolean trobat = false;
        while (!trobat && y >= 0) {
            if (b[x][y] != color && b[x][y] != '.') --y;
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (y < aux) {
                board.setCell(x, y, color);
                ++y;
            }
        }
    }

    private void abaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y] == color || b[x + 1][y] == '.') return;
        int aux = x;
        ++x;
        boolean trobat = false;
        while (!trobat && x < 8) {
            if (b[x][y] != color && b[x][y] != '.') ++x;
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x > aux) {
                board.setCell(x, y, color);
                --x;
            }
        }
    }

    private void amunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y] == color || b[x - 1][y] == '.') return;
        int aux = x;
        --x;
        boolean trobat = false;
        while (!trobat && x >= 0) {
            if (b[x][y] != color && b[x][y] != '.') --x;
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x < aux) {
                board.setCell(x, y, color);
                ++x;
            }
        }
    }

    private void EsqAmunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y - 1] == color || b[x - 1][y - 1] == '.') return;
        int aux1 = x;
        int aux2 = y;
        --x;
        --y;
        boolean trobat = false;
        while (!trobat && x >= 0 && y >= 0) {
            if (b[x][y] != color && b[x][y] != '.') {
                --x;
                --y;
            }
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x < aux1 && y < aux2) {
                board.setCell(x, y, color);
                ++x;
                ++y;
            }
        }
    }

    private void DretAbaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y + 1] == color || b[x + 1][y + 1] == '.') return;
        int aux1 = x;
        int aux2 = y;
        ++x;
        ++y;
        boolean trobat = false;
        while (!trobat && x < 8 && y < 8) {
            if (b[x][y] != color && b[x][y] != '.') {
                ++x;
                ++y;
            }
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x > aux1 && y > aux2) {
                board.setCell(x, y, color);
                --x;
                --y;
            }
        }
    }


    private void DretAmunt(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x - 1][y + 1] == color || b[x - 1][y + 1] == '.') return;
        int aux1 = x;
        int aux2 = y;
        --x;
        ++y;
        boolean trobat = false;
        while (!trobat && x >= 0 && y < 8) {
            if (b[x][y] != color && b[x][y] != '.') {
                --x;
                ++y;
            }
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x < aux1 && y > aux2) {
                board.setCell(x, y, color);
                ++x;
                --y;
            }
        }
    }


    private void EsqAbaix(char color, int x, int y) {
        char [][] b = board.getBoard();
        if (b[x + 1][y - 1] == color || b[x + 1][y - 1] == '.') return;
        int aux1 = x;
        int aux2 = y;
        ++x;
        --y;
        boolean trobat = false;
        while (!trobat && y >= 0 && x < 8) {
            if (b[x][y] != color && b[x][y] != '.') {
                ++x;
                --y;
            }
            else if (b[x][y] == color) trobat = true;
            else return;
        }
        if (trobat) {
            while (x > aux1 && y < aux2) {
                board.setCell(x, y, color);
                --x;
                ++y;
            }
        }
    }


    /**
     *Funció getSuccessors
     * @Author Arnau Llobet Massallé
     * Retorna una llista de tots els jocs obtinguts a partir del joc actual.
     * Hi ha un element de la llista per a cada els moviment possible. Si no hi ha moviments possibles,
     * retorna una llista buida.
     */
    public LinkedList<Game> getSuccessors(Player player, int depth){
        LinkedList<Game> llistafills = new LinkedList<Game>();
        calculamove(player);
        for(int x = 0; x < 8; ++x){
            for(int y = 0; y < 8; ++y){
                if(taulell[x][y]) {
                    Game nouJoc = new Game(this);
                    nouJoc.move(player, x, y);
                    if(depth == 0){
                        Pair p1 = new Pair(x,y);
                        nouJoc.setMovimentPare(p1);
                    }
                    llistafills.add(nouJoc);
                }
            }
        }
        return llistafills;
    }


    /**
     *Funció esTerminal
     * @Author Arnau Llobet Massallé
     * Retorna true si l'estat actual del joc és terminal, és a dir, o el tauler esta ple o un dels dos
     * jugadors s'ha quedat sense fitxes. Altrament retorna false.
     */
    public boolean esTerminal() {
        int count = 0;
        int white = 0;
        int black = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getBoard().getCell(i, j) == 'W') ++white;
                if(getBoard().getCell(i, j) == 'B') ++black;
            }
        }
        return ((black + white) == 64) || (white == 0) || (black == 0);
    }


    /**
     *Funció heuristic
     * @Author Arnau Llobet Massallé
     * Retorna l'heurístic de l'estat actual del joc. L'heurístic és la diferència de fitxes respecte
     * el rival.
     */
    public int heuristic(char col)  {
        if (col == 'W') return getBoard().getWhites() - getBoard().getBlacks();
        return getBoard().getBlacks() - getBoard().getWhites();
    }



    /**
     *Funcions seters i geters
     * @Author Diego Núñez Gimeno
     */
    public void setMovimentPare(Pair<Integer,Integer> pare){
        this.movimentPare = pare;
    }

    public Pair<Integer, Integer> getMovimentPare() {
        return movimentPare;
    }

    public void setUltimMov(Pair<Integer, Integer> ultimMov) {
        this.ultimMov = ultimMov;
    }

    public int getID() {
        return ID;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Board getBoard() {
        return board;
    }

    public boolean[][] getTaulell() {
        return taulell;
    }

    public Pair<Integer, Integer> getUltimMov() {
        return ultimMov;
    }

    public void setTaulell(boolean [][] taulell) {
        this.taulell = taulell;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getWinnerPlayerId(){
        int black = board.getBlacks();
        int white = board.getWhites();

        if(black > white) return blackPlayer.getID();
        else if(white > black) return whitePlayer.getID();
        else return -1;

    }
    public String getWinnerEmail(){
        int black = board.getBlacks();
        int white = board.getWhites();

        if(black > white) return blackPlayer.getEmail();
        else if(white > black) return whitePlayer.getEmail();
        else return "";
    }
    public int getLoserPlayerId(){
        int black = board.getBlacks();
        int white = board.getWhites();

        if(black < white) return blackPlayer.getID();
        else if(white < black) return whitePlayer.getID();
        else return -1;
    }

    @Override
    public String toString() {
        return "BlackPlayer: " + blackPlayer.getEmail() + "\n"
                + "WhitePlayer: "+ whitePlayer.getEmail() + "\n";
    }


    /**
     *Funcions print
     * @Author Diego Núñez Gimeno
     */
    public void printBoard(){
        board.printBoard();
    }

    public void printGameBoard(boolean validMoves){
        if(validMoves){
            System.out.println("Board with possible moves:");
            printTaulell(taulell);
        }else{
            System.out.println("No possible moves. Skipping turn ...");
            System.out.println();
        }
    }

    private void printTaulell(boolean [][] aux) {
        char [][] b = board.getBoard();
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 8; ++i) {
            System.out.print(i + " ");
            boolean primer = true;
            for (int j = 0; j < 8; ++j) {

                if(ultimMov.getValue0() == i && ultimMov.getValue1() == j )
                    System.out.print(ANSI_GREEN);

                if (!primer) {
                    System.out.print(' ');
                } else {
                    primer = false;
                }
                if (aux[i][j]) System.out.print('o');
                else {
                    if(b[i][j] == 'B') {
                        System.out.print('B');
                    }
                    else if(b[i][j] == 'W') {
                        System.out.print('W');
                    }
                    else System.out.print('.');

                }
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }
    }
}
