package UI.Views;

import Exceptions.PersonaDoesNotExists;
import UI.Controllers.UIAuthController;
import UI.Controllers.UIGameController;
import UI.Controllers.UiCtrlStatistics;
import UI.Controllers.UiCtrlRanking;

import java.util.Scanner;


/**
 * @author Daniel Rodriguez
 */
public class MenuUsuario {



    public void userMenu() throws PersonaDoesNotExists {
        boolean exit = false;

        System.out.print("Welcome back ");
        UIAuthController.printLoggedPersonEmail();

        while(!exit){

            Scanner scan = new Scanner(System.in);

            showUserMenu();

            String opt = scan.nextLine();

            switch(opt){
                case "1":
                    showStatistics();
                    break;
                case "2":
                    System.out.println("Logging out ...");
                    UIAuthController.loggout();
                    exit = true;
                    break;
                case "3":
                    System.out.println("Hello player! ");
                    System.out.println("Please, select a play mode");
                    System.out.println("1.- PvP");
                    System.out.println("2.- PvB");
                    System.out.println("3.- BvB");
                    System.out.println("4.- Exit");

                    UIGameController.createGame();
                    System.out.println("See you later, player");


                    break;
                case "4":
                    showGames();
                    break;
                case "5":
                    getRanking();
                    break;
            }
        }
    }

    public void showUserMenu(){
        System.out.println("1- Statistics");
        System.out.println("2- Logout");
        System.out.println("3- Create Game");
        System.out.println("4- Continue Game");
        System.out.println("5- Ranking");
    }

    public void showStatistics(){
        String statistics = UiCtrlStatistics.getStatistics();
        System.out.println(statistics);

    }
    public void showGames(){

        String games = UIGameController.getGames();

        if(games == ""){
            System.out.println("No game played yet");
        }else {
            System.out.println("Enter the game number or -1 to quit:");
            System.out.println(games);
            Scanner scan = new Scanner(System.in);
            int gameSelected = scan.nextInt();
            if(gameSelected == -1){
                System.out.println("Exiting ... ");
            }else{
                System.out.println("Loading game ...");
                boolean error = UIGameController.chargeSelectedGame(gameSelected);
                if (error) System.out.println("Sorry, something has gone wrong");
                else System.out.println("Have a nice day");
            }

        }

    }
    public void getRanking() throws PersonaDoesNotExists {
      //  String ranking = UiCtrlRanking.getRanking();
      //  System.out.println(ranking);

    }


}
