package UI.Views;

import Exceptions.PersonaDoesNotExists;
import UI.Controllers.UIAuthController;

import java.awt.*;
import java.util.Scanner;

/**
 * @author Daniel Rodriguez
 */


public class Main {

    public static void main(String [] args) throws PersonaDoesNotExists {

        while(true){

            showMenu();
            makeChoice();

        }
    }

    public static void showMenu(){

        System.out.println("Select an option to continue:");
        System.out.println("1 - Register new user");
        System.out.println("2 - Login");

    }

    public static void makeChoice() throws PersonaDoesNotExists {
        Scanner sc = new Scanner(System.in);

        String op = sc.nextLine();

        switch(op){
            case "1":

                if(UIAuthController.registerNewUser()){
                    System.out.println("Register successfully");
                }else{
                    System.out.println("Register fails");
                }

                break;
            case "2":

                if(UIAuthController.loginUser()){
                    System.out.println("Login successfully");
                    MenuUsuario mu = new MenuUsuario();
                    mu.userMenu();
                }else{
                    System.out.println("Login fails");
                }

                break;

            default:
                System.out.println("Wrong operation");
        }

    }



}
