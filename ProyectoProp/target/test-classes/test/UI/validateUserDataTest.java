package test.UI;

import UI.Vistas.Controllers.helpers.ValidateUserData;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author Daniel Rodriguez
 */

public class validateUserDataTest {
    @Test

    public void ValidUserData_CorrectData_ReturnTrue(){
        String email = "dani";
        String password = "password";
        ValidateUserData val = new ValidateUserData();

        boolean result = val.validateUserData(email,password);

        Assert.assertTrue("Correct data should return true",result);
    }

    @Test

    public void ValidUserData_InvalidData_ReturnFalse(){
        String email = "";
        String password = "";
        ValidateUserData val = new ValidateUserData();

        boolean result = val.validateUserData(email, password);

        Assert.assertFalse(result);
    }

}
