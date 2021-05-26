package Drivers.Domain;

import Domain.Models.Usuario.Estadistiques;
import Exceptions.PersonaDoesNotExists;
import java.util.Scanner;

/**
 * @author Pep Gascon
 */
public class EstadistiquesDriver {
    public static final String ANSI_RESET = "\u001B[0m";


    public static Estadistiques estadistiques;
    public static Scanner scan = new Scanner(System.in);

    public static void main(String [] args) throws PersonaDoesNotExists {

        registrarEstadisticas();

        while(true){

            System.out.println(ANSI_RESET);
            muestraMenu();
            selectOpt();
        }

    }

    private static void registrarEstadisticas(){
        System.out.println("ID Persona: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("Partidos ganados:");
        int Pguanyats = scan.nextInt();
        System.out.println("Partidos perdidos:");
        int Pperduts = scan.nextInt();
        estadistiques = new Estadistiques(id, Pguanyats, Pperduts);
    }

    private static void muestraMenu(){
        System.out.println("Welcome to the Estadistiques Driver");
        System.out.println("1- Add Partit Guanyat");
        System.out.println("2- Add Partit Perdut");
        System.out.println("3- Get Player ID");
        System.out.println("4- Get Partits Jugats");
        System.out.println("5- Get Partits Guanyats");
        System.out.println("6- Get Partits Perduts");
        System.out.println("7- Get Win Ratio");
        System.out.println("8- ToString");
    }

    public static void selectOpt() {
        int opt = scan.nextInt();

        switch (opt) {
            case 1:
                addPguanyats();
                break;
            case 2:
                addPperduts();
                break;
            case 3:
                getPlayerID();
                break;
            case 4:
                getPjugats();
                break;
            case 5:
                getPguanyats();
                break;
            case 6:
                getPperduts();
                break;
            case 7:
                getWinratio();
                break;
            case 8:
                getToString();
                break;

            default:
                System.out.println("Wrong option selected");
                break;
        }
    }

    private static void addPguanyats(){
        estadistiques.addPguanyat();
    }

    private static void addPperduts(){
        estadistiques.addPperdut();
    }

    private static void getPlayerID(){
        System.out.println(estadistiques.getPlayerID());
    }

    private static void getPjugats(){
        System.out.println(estadistiques.getPjugats());
    }

    private static void getPguanyats(){
        System.out.println(estadistiques.getPguanyats());
    }

    private static void getPperduts(){
        System.out.println(estadistiques.getPperduts());
    }

    private static void getWinratio(){
        System.out.println(estadistiques.getWinratio());
    }

    private static void getToString(){
        System.out.println(estadistiques.toString());
    }

}
