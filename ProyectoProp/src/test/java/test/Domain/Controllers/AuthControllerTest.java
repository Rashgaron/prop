package test.Domain.Controllers;

import DataBase.Controllers.CtrlPersona;
import DataBase.Controllers.CtrlStatistics;
import Domain.Controllers.AuthController;
import Domain.DataControllers.*;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Exceptions.PersonaDoesNotExists;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * @author Daniel Rodriguez
 */

public class AuthControllerTest {


    public static String email = "John Doe";
    public static String password = "12345";


    @Test

    public void registerNewUserTest(){

        AuthController authControl = new AuthController();
        CtrlPersona ctrlPersona = new CtrlPersona();
        CtrlStatistics ctrlStatisticsAdap = new CtrlStatistics();

        authControl.registerNewUser(email,password);

        try {
            boolean existsPersona = ctrlPersona.existsPersona(email);
            Persona persona = (Persona) ctrlPersona.getPersona(email);
            int id = persona.getID();
            boolean existsStatistic = ctrlStatisticsAdap.existsStatistic(id);

            Assert.assertTrue("El usuario no se ha registrado correctamente", existsPersona);
            Assert.assertTrue("La estadistica no existe", existsStatistic);

            ctrlPersona.deletePersona(email);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (PersonaDoesNotExists personaDoesNotExists) {
            personaDoesNotExists.printStackTrace();
        }



    }

    @Test

    public void loginUserTest(){

       CtrlPersona personaAdap = new CtrlPersona();
       AuthController authController = new AuthController();
       authController.registerNewUser(email, password);
       boolean loginSuccessfull = authController.loginUser(email, password);

       Assert.assertTrue("Ha habido un fallo con el login", loginSuccessfull);

        try {
            Persona persona = (Persona) personaAdap.getPersona(email);
            int id = persona.getID();
            personaAdap.deletePersona(email);
        } catch (PersonaDoesNotExists personaDoesNotExists) {
            personaDoesNotExists.printStackTrace();
        }

    }

    @Test
    public void ExistsPersona_EmailExistsButSameUser_ReturnFalse() throws SQLException {
        String userEmail = "userEmail";

        AuthController authController = new AuthController();
        authController.setCtrlDataFactory(new fakeDataFactory());
        Player user = mock(Player.class);
        when(user.getEmail()).thenReturn(userEmail);
        when(ctrlPersona.existsPersona(userEmail)).thenReturn(true);

        authController.setUser(user);


        boolean actual = authController.existsPersona(userEmail);

        Assert.assertFalse(actual);
    }

    @Test
    public void ExistsPersona_EmailNotExistsAndSameUser_ReturnFalse() throws SQLException {
        String userEmail = "userEmail";

        AuthController authController = new AuthController();
        authController.setCtrlDataFactory(new fakeDataFactory());
        Player user = mock(Player.class);
        when(user.getEmail()).thenReturn(userEmail);
        when(ctrlPersona.existsPersona(userEmail)).thenReturn(false);

        authController.setUser(user);

        boolean actual = authController.existsPersona(userEmail);

        Assert.assertFalse(actual);
    }

    @Test
    public void ExistsPersona_EmailNotExistsAndDifferentUser_ReturnFalse()throws SQLException{

        String userEmail1 = "userEmail1";
        String userEmail2 = "userEmail2";

        AuthController authController = new AuthController();
        authController.setCtrlDataFactory(new fakeDataFactory());
        Player user = mock(Player.class);
        when(user.getEmail()).thenReturn(userEmail2);
        when(ctrlPersona.existsPersona(userEmail1)).thenReturn(false);

        authController.setUser(user);

        boolean actual = authController.existsPersona(userEmail1);

        Assert.assertFalse(actual);

    }

    @Test
    public void ExistsPersona_EmailExistsAndDifferentUser_ReturnTrue() throws SQLException {
        String userEmail1 = "userEmail1";
        String userEmail2 = "userEmail2";

        AuthController authController = new AuthController();
        authController.setCtrlDataFactory(new fakeDataFactory());
        Player user = mock(Player.class);
        when(user.getEmail()).thenReturn(userEmail2);
        when(ctrlPersona.existsPersona(userEmail1)).thenReturn(true);

        authController.setUser(user);

        boolean actual = authController.existsPersona(userEmail1);

        Assert.assertTrue(actual);
    }


    public ICtrlPersona ctrlPersona = mock(ICtrlPersona.class);


    public class fakeDataFactory implements ICtrlDataFactory{

        @Override
        public ICtrlPersona getCtrlPersona() {
            return ctrlPersona;
        }

        @Override
        public ICtrlStatistics getCtrlStatistics() {
            return null;
        }

        @Override
        public ICtrlGame getCtrlGame() {
            return null;
        }

        @Override
        public ICtrlBoard getCtrlBoard() {
            return null;
        }
    }
}
