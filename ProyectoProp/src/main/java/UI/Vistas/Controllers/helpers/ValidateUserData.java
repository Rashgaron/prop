package UI.Vistas.Controllers.helpers;

/**
 * @author Daniel Rodriguez
 */


public class ValidateUserData {
    public boolean validateUserData(String a, String b){
        if(a.trim().equals("") || b.trim().equals("")) return false;
        else return true;
    }
}
