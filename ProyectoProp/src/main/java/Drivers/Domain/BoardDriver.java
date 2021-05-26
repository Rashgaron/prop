package Drivers.Domain;

import Domain.Models.Game.Game;
import Domain.Models.Game.Board;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;

import java.util.List;
import java.util.Scanner;

/**
 * @author Pep Gascon
 */
public class BoardDriver {
    public static final String ANSI_RESET = "\u001B[0m";


    public static Board board;
    public static Scanner scan = new Scanner(System.in);

    public static void main(String [] args) {

        registerBoard();

        while(true){

            System.out.println(ANSI_RESET);
            muestraMenu();
            selectOpt();
        }

    }

    private static void registerBoard(){
        System.out.println("ID Board: ");
        int id = scan.nextInt();
        board = new Board(id);
        board.initialize();
    }

    private static void muestraMenu(){
        System.out.println("Welcome to the Board Driver");
        System.out.println("1- Register Board");
        System.out.println("2- Get black points");
        System.out.println("3- Get white points");
        System.out.println("4- Set Cell");
        System.out.println("5- Get Cell");
        System.out.println("6- Get Board ID");
        System.out.println("7- Print Board");
        System.out.println("8- Get Board String");
        System.out.println("9- Get Turn");
        System.out.println("10- Set Turn");
        System.out.println("11- Initialize Board");
    }

    public static void selectOpt()   {
        int opt = scan.nextInt();

        switch (opt) {
            case 1:
                registerBoard();
                break;
            case 2:
                getBlacks();
                break;
            case 3:
                getWhites();
                break;
            case 4:
                setCell();
                break;
            case 5:
                getCell();
                break;
            case 6:
                getID();
                break;
            case 7:
                printBoard();
                break;
            case 8:
                getBoardString();
                break;
            case 9:
                getTurn();
                break;
            case 10:
                setTurn();
                break;
            case 11:
                initializeBoard();
                break;

            default:
                System.out.println("Wrong option selected");
                break;
        }
    }

    private static void getBlacks(){
        System.out.println(board.getBlacks());
    }

    private static void getWhites(){
        System.out.println(board.getWhites());
    }

    private static void setCell(){
        System.out.println("Column:");
        int x = scan.nextInt();
        System.out.println("Row:");
        int y = scan.nextInt();
        System.out.println("The cell is gonna be:");
        System.out.println("1- White");
        System.out.println("2- Black");
        System.out.println("3- Empty");

        int opt = scan.nextInt();
        char color = '.';
        switch (opt) {
            case 1:
                color = 'W';
                break;
            case 2:
                color = 'B';
                break;
            case 3:
                color = '.';
                break;

            default:
                System.out.println("Wrong option selected");
                break;
        }
        board.setCell(x, y, color);
    }

    private static void getCell(){
        System.out.println("Column:");
        int x = scan.nextInt();
        System.out.println("Row:");
        int y = scan.nextInt();
        board.getCell(x, y);
    }

    private static void getID(){
        System.out.println(board.getID());
    }

    private static void printBoard(){
        board.printBoard();
    }

    private static void getBoardString(){
        System.out.println(board.getBoardString());
    }

    private static void getTurn(){

        if (board.getTurn()) System.out.println("White's turn!");
        else System.out.println("Black's turn!");

    }

    private static void setTurn(){
        System.out.println("White's turn? (true or false)");
        boolean turn = scan.nextBoolean();
        board.setTurn(turn);
    }

    private static void initializeBoard(){
        board.initialize();
    }

}
