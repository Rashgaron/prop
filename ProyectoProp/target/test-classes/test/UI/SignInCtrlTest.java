package test.UI;

import Domain.Controllers.IAuthController;
import UI.Vistas.Controllers.SignInCtrl;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * @author Daniel Rodriguez
 */


public class SignInCtrlTest {

    final static String validEmail = "valid email";
    final static String validPassword = "valid password";
    final static String invalidData = "";


    @Test

    public void RegisterUser_ValidDataAndNotRegistered_ReturnTrue(){
        IAuthController authCtrl = mock(IAuthController.class);
        when(authCtrl.registerNewUser(validEmail,validPassword)).thenReturn(true);
        SignInCtrl signIn = new SignInCtrl();

        boolean actual = signIn.registerUser(validEmail, validPassword, authCtrl);
        verify(authCtrl,only()).registerNewUser(validEmail, validPassword);
        Assert.assertTrue("Con datos válidos y sin un usuario ya registrado, el resultado debería ser true", actual);

    }

    @Test
    public void RegisterUser_ValidDataAndRegistered_ReturnFalse(){
        IAuthController authCtrl = mock(IAuthController.class);
        when(authCtrl.registerNewUser(validEmail, validPassword)).thenReturn(false);
        SignInCtrl signIn = new SignInCtrl();

        boolean actual = signIn.registerUser( validEmail, validPassword, authCtrl);

        verify(authCtrl,only()).registerNewUser(validEmail, validPassword);
        Assert.assertFalse("Con datos válidos pero con el usuario ya registrado, el resultado debe ser false", actual);
    }

    @Test
    public void RegisterUser_InvalidDataAndRegistered_ReturnFalse(){

        IAuthController authCtrl = mock(IAuthController.class);
        when(authCtrl.registerNewUser(invalidData, invalidData)).thenReturn(false);

        SignInCtrl signIn = new SignInCtrl();
        boolean actual = signIn.registerUser(invalidData, invalidData, authCtrl);


        verify(authCtrl, never()).registerNewUser(anyString(),anyString());
        Assert.assertFalse(actual);
    }

    @Test
    public void RegisterUser_InvalidDataAndNotRegistered_ReturnFalse(){
        IAuthController authCtrl = mock(IAuthController.class);

        when(authCtrl.registerNewUser(invalidData, invalidData)).thenReturn(true);
        SignInCtrl signIn = new SignInCtrl();

        boolean actual = signIn.registerUser(invalidData, invalidData, authCtrl);

        verify(authCtrl, never()).registerNewUser(anyString(), anyString());
        Assert.assertFalse(actual);
    }

}
