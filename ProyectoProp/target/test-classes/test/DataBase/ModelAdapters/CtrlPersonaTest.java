package test.DataBase.ModelAdapters;
import DataBase.Controllers.CtrlPersona;
import Domain.DataControllers.ICtrlPersona;
import Domain.Models.Usuario.Persona;
import Exceptions.PersonaDoesNotExists;
import org.junit.*;

import java.sql.SQLException;

/**
 *
 * @author Daniel Rodriguez
 */

public class CtrlPersonaTest {


    private static String email = "test";
    private static String password = "Password";
    private static int id = 10;

    @Before
    public void insertDummyData(){
        ICtrlPersona personaCtrl = new CtrlPersona();
        personaCtrl.savePersona(id, email, password);
    }
    @After
    public void deletePersona(){
        ICtrlPersona personaCtrl = new CtrlPersona();
        personaCtrl.deletePersona(email);
    }


    @Test
    public void savePersonaTest(){
       ICtrlPersona persona = new CtrlPersona();
       persona.deletePersona(email);
       persona.savePersona(id, email, password);

        try {

            Persona personaResult = (Persona) persona.getPersona(email);
            Assert.assertEquals("Contraseña incorrecta", personaResult.getPassword(),password);
            Assert.assertEquals("Email incorrecto", personaResult.getEmail(),email);
            Assert.assertEquals("ID incorrecto",personaResult.getID(),id);

        } catch (PersonaDoesNotExists personaDoesNotExists) {
            personaDoesNotExists.printStackTrace();
            Assert.assertTrue("Usuario no creado correctamente",false);

        }
    }

    @Test
    public void getPersonaTest(){
        ICtrlPersona ctrlPersona = new CtrlPersona();
        try {

            Persona personaResult = (Persona) ctrlPersona.getPersona(email);

            Assert.assertEquals("Contraseña incorrecta", personaResult.getPassword(),password);
            Assert.assertEquals("Email incorrecto", personaResult.getEmail(),email);
            Assert.assertEquals("ID incorrecto",personaResult.getID(),id);
        } catch (PersonaDoesNotExists personaDoesNotExists) {
            personaDoesNotExists.printStackTrace();
            Assert.assertTrue("La persona no existe",false);
        }

    }

    @Test
    public void existsPersonaTest(){
        ICtrlPersona ctrlPersona = new CtrlPersona();

        try {
            boolean exists = ctrlPersona.existsPersona(email);
            Assert.assertTrue("Persona no existe", exists);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void deletePersonaTest(){
        ICtrlPersona ctrlPersona = new CtrlPersona();

        boolean deleted = ctrlPersona.deletePersona(email);


        try {
            deleted = !ctrlPersona.existsPersona(email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Assert.assertTrue("La persona no se ha eliminado",deleted);

    }

}
