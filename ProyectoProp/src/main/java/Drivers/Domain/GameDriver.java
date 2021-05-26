package Drivers.Domain;

import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import org.javatuples.Pair;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Pep Gascon
 */
public class GameDriver {
    public static final String ANSI_RESET = "\u001B[0m";


    public static Game game;
    public static Scanner scan = new Scanner(System.in);

    static Player escullPlayer(){
        System.out.println("Choose the player:");
        System.out.println("1- White");
        System.out.println("2- Black");

        int opt = scan.nextInt();
        Player player = null;
        switch (opt) {
            case 1:
                player = game.getWhitePlayer();
                break;
            case 2:
                player = game.getBlackPlayer();
                break;
            default:
                System.out.println("Wrong option selected");
                break;
        }
        return player;
    }

    public static void main(String [] args)  {

        registrarGame();

        while(true){

            System.out.println(ANSI_RESET);
            muestraMenu();
            selectOpt();
        }

    }

    private static void registrarGame(){
        System.out.println("Game ID: ");
        int id = scan.nextInt();
        Persona persona1 = new Persona(1234, "josep1", "1234");

        Persona persona2 = new Persona(4321, "josep2", "1234");
        game = new Game(id, 1234, 1234, persona1, persona2);
    }

    private static void muestraMenu(){
        System.out.println("Welcome to the Game Driver");
        System.out.println("1- Register Game");
        System.out.println("2- Move Player");
        System.out.println("3- Calculate if there are possible moves");
        System.out.println("4- Get successors");
        System.out.println("5- Is terminal?");
        System.out.println("6- Get heuristic");
        System.out.println("7- Set father move");
        System.out.println("8- Get father move");
        System.out.println("9- Set last move");
        System.out.println("10- Get last move");
        System.out.println("11- Get game ID");
        System.out.println("12- Set Black player");
        System.out.println("13- Set White player");
        System.out.println("14- Get valid position matrix");
        System.out.println("15- Get Winner");
        System.out.println("16- Get Loser");
        System.out.println("17- Print Board");
        System.out.println("18- Print Board with possible moves");
    }

    public static void selectOpt()  {
        int opt = scan.nextInt();

        switch (opt) {
            case 1:
                registrarGame();
                break;
            case 2:
                move();
                break;
            case 3:
                calculaMove();
                break;
            case 4:
                getSuccessors();
                break;
            case 5:
                esTerminal();
                break;
            case 6:
                heuristic();
                break;
            case 7:
                setMovimentPare();
                break;
            case 8:
                getMovimentPare();
                break;
            case 9:
                setUltimMov();
                break;
            case 10:
                getUltimMov();
                break;
            case 11:
                getID();
                break;
            case 12:
                setBlackPlayer();
                break;
            case 13:
                setWhitePlayer();
                break;
            case 14:
                getTaulell();
                break;
            case 15:
                getWinnerPlayerId();
                break;
            case 16:
                getLoserPlayerId();
                break;
            case 17:
                printBoard();
                break;
            case 18:
                printPossibleMovesBoard();
                break;

            default:
                System.out.println("Wrong option selected");
                break;
        }
    }

    private static void move(){
        Player player = escullPlayer();
        System.out.println("Column:");
        int x = scan.nextInt();
        System.out.println("Row:");
        int y = scan.nextInt();
        if(game.move(player, x, y)) System.out.println("Player moved!");
        else System.out.println("Invalid move!");
    }

    private static void calculaMove(){
        Player player = escullPlayer();
        if(game.calculamove(player)) System.out.println("Possible moves!");
        else System.out.println("There aren't possible moves!");
    }

    private static void getSuccessors(){
        Player player = escullPlayer();
        System.out.println("Depth:");
        int depth = scan.nextInt();
        LinkedList<Game> l = game.getSuccessors(player,depth);
        for (int i = 0; i < l.size(); ++i){
            l.pop().printBoard();
            System.out.print("\n");
        }
    }

    private static void esTerminal(){
        if(game.esTerminal()) System.out.println("Is terminal!");
        else System.out.println("Isn't terminal!");
    }

    private static void heuristic(){
        System.out.println("Choose the player:");
        System.out.println("1- White");
        System.out.println("2- Black");

        int opt = scan.nextInt();
        char c = '.';
        switch (opt) {
            case 1:
                c = 'W';
                break;
            case 2:
                c = 'B';
                break;
            default:
                System.out.println("Wrong option selected");
                break;
        }
        System.out.println(game.heuristic(c));
    }

    private static void setMovimentPare(){
        System.out.println("Column:");
        int x = scan.nextInt();
        System.out.println("Row:");
        int y = scan.nextInt();
        Pair<Integer,Integer> pair = new Pair<>(x,y);
        game.setMovimentPare(pair);
    }

    private static void getMovimentPare(){
        Pair<Integer,Integer> pair = game.getMovimentPare();
        System.out.println("Column = "+pair.getValue0()+" Row = "+pair.getValue1());
    }

    private static void setUltimMov(){
        System.out.println("Column:");
        int x = scan.nextInt();
        System.out.println("Row:");
        int y = scan.nextInt();
        Pair<Integer,Integer> pair = new Pair<>(x,y);
        game.setUltimMov(pair);
    }

    private static void getUltimMov(){
        Pair<Integer,Integer> pair = game.getUltimMov();
        System.out.println("Column = "+pair.getValue0()+" Row = "+pair.getValue1());
    }

    private static void getID(){
        System.out.println(game.getID());
    }

    private static void setBlackPlayer(){
        System.out.println("Player ID:");
        int id = scan.nextInt();
        System.out.println("Email:");
        String email = scan.next();
        System.out.println("Password:");
        String password = scan.next();
        Persona persona3 = new Persona(id, email, password);
        game.setBlackPlayer(persona3);
    }

    private static void setWhitePlayer(){
        System.out.println("Player ID:");
        int id = scan.nextInt();
        System.out.println("Email:");
        String email = scan.next();
        System.out.println("Password:");
        String password = scan.next();
        Persona persona3 = new Persona(id, email, password);
        game.setWhitePlayer(persona3);
    }

    private static void getTaulell(){
        Player player = escullPlayer();
        if(game.calculamove(player)) {
            boolean[][] matrix = game.getTaulell();
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if(matrix[i][j]) System.out.print("o ");
                    else System.out.print(". ");
                }
                System.out.print("\n");
            }
        }
    }

    private static void getWinnerPlayerId(){
        if(game.esTerminal()) System.out.println(game.getWinnerPlayerId());
        else System.out.println("Game not finished!");
    }


    private static void getLoserPlayerId(){
        if(game.esTerminal()) System.out.println(game.getLoserPlayerId());
        else System.out.println("Game not finished!");
    }

    private static void printBoard(){
        game.printBoard();
    }

    private static void printPossibleMovesBoard(){
        Player player = escullPlayer();
        game.printGameBoard(game.calculamove(player));
    }
}