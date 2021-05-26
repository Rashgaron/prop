package Drivers.Domain;

import Domain.Models.Game.Game;
import Domain.Models.Usuario.Bot;
import Domain.Models.Usuario.Persona;

import java.util.Scanner;

/**
 * @author Pep Gascon
 */
public class BotDriver {
    public static final String ANSI_RESET = "\u001B[0m";

    public static Bot bot;
    public static Scanner scan = new Scanner(System.in);

    static Game creaGame(){
        Persona persona1 = new Persona(1234, "1234", "1234");
        Persona persona2 = new Persona(4321, "4321", "4321");
        Game game = new Game(1234, 1234, 1234, persona1, persona2);
        return game;
    }

    public static void main(String [] args) {

        registrarBot();

        while(true){

            System.out.println(ANSI_RESET);
            muestraMenu();
            selectOpt();
        }

    }

    private static void registrarBot(){
        System.out.println("ID Bot: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("Introduce Bot level: (from 1 to 8)");
        int botLvl = scan.nextInt();
        Game game = creaGame();
        bot = new Bot(id, botLvl, game);
    }

    private static void muestraMenu(){
        System.out.println("Welcome to the Bot Driver");
        System.out.println("1- Register Bot");
        System.out.println("2- Show best move");
        System.out.println("3- Get Bot Level");
        System.out.println("4- Set Bot Level");
    }

    public static void selectOpt() {
        int opt = scan.nextInt();

        switch (opt) {
            case 1:
                registrarBot();
                break;
            case 2:
                showBestMove();
                break;
            case 3:
                getBotLvl();
                break;
            case 4:
                setBotLvl();
                break;


            default:
                System.out.println("Wrong option selected");
                break;
        }
    }

    private static void showBestMove(){
        Game game = bot.getGame();
        System.out.println(bot.bestMove(game));
    }

    private static void getBotLvl(){
        System.out.println(bot.getBotLvl());
    }

    private static void setBotLvl(){
        System.out.println("Introduce Bot level: (from 1 to 8)");
        int botlvl = scan.nextInt();
        bot.setBotLvl(botlvl);
    }

}