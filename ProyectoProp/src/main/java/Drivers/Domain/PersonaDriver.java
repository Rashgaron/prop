package Drivers.Domain;

import Domain.Models.Game.Game;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;

import java.util.List;
import java.util.Scanner;
/**
 * @author Daniel Rodriguez
 */
public class PersonaDriver {



    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";



    public static Persona persona;
    public static Scanner scan = new Scanner(System.in);
    
    public static void main(String [] args){

        registrarPersona();

        while(true){

            System.out.println(ANSI_RESET);
            muestraMenu();
            selectOpt();
        }

    }

    private static void registrarPersona(){
        System.out.println("ID Persona: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("Email persona:");
        String email = scan.nextLine();
        System.out.println("Password persona:");
        String password = scan.nextLine();
        persona = new Persona(id, email, password);
    }

    private static void muestraMenu(){
        System.out.println("Welcome to the Persona Driver");
        System.out.println("1- Create new Persona");
        System.out.println("2- Get Game by index");
        System.out.println("3- Get Game by ID");
        System.out.println("4- Make your move (Not implemented yet)");
        System.out.println("5- Get games");
        System.out.println("6- Add Game");
        System.out.println("7- Get Email");
        System.out.println("8- Set Email");
        System.out.println("9- Get Password");
        System.out.println("10- Set Password");
        System.out.println("11- Get Statistics");
        System.out.println("12- Set Statistics");
        System.out.println("13- Win Game");
        System.out.println("14- Lost Game");
    }
    
    public static void selectOpt(){
            int opt = scan.nextInt();

            switch(opt){
                case 1:
                    
                    registrarPersona();
                    break;
                    
                case 2:
                    
                    getGameByIndex(); 
                    break;
                    
                case 3:
                   
                    getGameByID();
                    break;
                    
                case 4:
                    
                    System.out.println("Not implemented yet ...");
                    break;
                    
                case 5:
                    
                    getAllGames();
                    break;
                    
                case 6:
                    
                    addNewGame();
                    
                    break;
                    
                case 7:

                    System.out.println(ANSI_GREEN + "The Persona email is: " + persona.getEmail());
                    break;
                    
                case 8:

                    insertEmail();
                    break;
                    
                case 9:
                    
                    printPassword();
                    break;
                    
                case 10:
                    
                    setNewPassword(); 
                    break;
                    
                case 11:
                        
                    getStatistics();
                    
                    break;
                    
                case 12:
                    
                    addStatistics();
                    
                    break;

                case 13:
                    
                    addWonGame();
                    
                    break;
                    
                case 14:
                    
                    addLostGame();
                    break;
                    
                default:
                    
                    System.out.println("Wrong option selected");
                    break;
            }


        }

        private static void insertEmail(){
            System.out.println("Insert a new email:");
            scan.nextLine();
            String email = scan.nextLine();
            persona.setEmail(email);
        }

        private static void getAllGames(){
            System.out.println("List of GamesID:");
            List<Game> games = persona.getGames();

            if(games.size() == 0) System.out.println(ANSI_RED + "Not enough games yet ...");
            else{
                games.forEach(g ->{
                    System.out.println(ANSI_GREEN + "GameID: " + g.getID());
                });
            }

        }

        private static void getStatistics(){
            System.out.println("Statistics of Persona:");
            Estadistiques statistic = persona.getEstadistiques();
            if(statistic != null){
                System.out.println(ANSI_GREEN);
                System.out.println("Won matches: " + statistic.getPguanyats());
                System.out.println("Lost matches: " + statistic.getPperduts());
                System.out.println("Win ratio: " + statistic.getWinratio());
            }else{
                System.out.println(ANSI_RED + "You need to Set Statistics First");
            }
        }
        
        private static void addStatistics(){
            System.out.println("Create New Statistic ...");
            Estadistiques est = new Estadistiques(persona.getID(), 0,0);
            persona.setEstadistiques(est);
        }

        private static void addNewGame(){
            System.out.println("Add Game");
            System.out.println("Insert gameID:");
            int gameID = scan.nextInt();
            System.out.println("Insert boardID:");
            int boardID = scan.nextInt();

            Game game = new Game(gameID, 1010, boardID, null, null);
            persona.addGame(game);
        }
        
        
        private static void addWonGame(){
            if(persona.getEstadistiques() != null){
                System.out.println("Add won game");
                persona.winGame();
            }else{
                System.out.println(ANSI_RED + "You need to Set Statistics first");
            }
        }
        
        private static void getGameByIndex(){
            if(persona.getGames().size() != 0){
                System.out.println("Insert index");
                int index = scan.nextInt();
                Game game = persona.getSelectedGame(index);
                System.out.println(ANSI_GREEN + "Game ID:" + game.getID());
            }else{
                System.out.println(ANSI_RED + "No game added yet ...");
            }

        }
        
        private static void getGameByID(){
            System.out.println("Insert ID:");
            int id = scan.nextInt();
            Game game = persona.getGame(id);

            if(game != null){
                System.out.println(ANSI_GREEN + "Game ID:" + game.getID());
            }else
                System.out.println(ANSI_RED + "Game does not exists");

        }    
        private static void setNewPassword(){
            System.out.println("Insert new Password");
            scan.nextLine();
            String password = scan.nextLine();
            persona.setPassword(password);
        } 
        private static void printPassword(){
            System.out.println(ANSI_GREEN + "The Persona password is: " + persona.getPassword());
        }

        private static void addLostGame(){
            if(persona.getEstadistiques() != null){
                System.out.println(ANSI_GREEN + "Add lost game");
                persona.lostGame();
            }else{
                System.out.println(ANSI_RED + "You need to Set Statistics first");
            }
        }

}
