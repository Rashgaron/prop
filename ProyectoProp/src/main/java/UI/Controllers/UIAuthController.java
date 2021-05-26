package UI.Controllers;

import Domain.Controllers.AuthController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Daniel Rodriguez
 */
public class UIAuthController {


    private static AuthController ac = new AuthController();

    public static boolean loginUser() {

        ArrayList<String> userData = readUserData();

        if(!validUserData(userData)){
            return false;
        }

        return ac.loginUser(userData.get(0), userData.get(1));

    }

    public static boolean registerNewUser() {

        ArrayList<String> userData = readUserData();

        if(!validUserData(userData)){
            return false;
        }

        return ac.registerNewUser(userData.get(0), userData.get(1));

    }

    private static boolean validUserData(ArrayList<String> userData) {

        String email = userData.get(0);
        String password = userData.get(1);

        if(email.trim().equals("") || password.trim().equals("")){
            return false;
        }
        return true;

    }


    public static ArrayList<String> readUserData(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your pass: ");
        String password = sc.nextLine();

        return new ArrayList<String>(Arrays.asList(email, password));

    }

    public static void printLoggedPersonEmail(){
        System.out.println(ac.getEmailLoggedPersona());
    }

    public static void loggout(){
        ac.logout();
    }



}
