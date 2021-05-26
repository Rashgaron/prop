package Domain.Controllers;

import Domain.DataControllers.CtrlDataFactory;
import Domain.DataControllers.ICtrlDataFactory;
import Domain.DataControllers.ICtrlStatistics;
import Domain.DataControllers.ICtrlPersona;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Domain.UserLogged;
import Exceptions.PersonaDoesNotExists;

import java.sql.SQLException;
import java.util.Date;

/**
 * @author Daniel Rodriguez
 */

public class AuthController implements IAuthController {

/*
* Variables para hacer inyección de dependencias en los tests
* */
    private UserLogged userLogged = UserLogged.Instance();
    private Player user = UserLogged.Instance().getUserLogged();
    private ICtrlDataFactory ctrlDataFactory = CtrlDataFactory.Instance();
    public void setCtrlDataFactory(ICtrlDataFactory ctrlDataFactory){
        this.ctrlDataFactory = ctrlDataFactory;
    }

    /**
     *
     * Se chequea si el usuario existe en bd, y lo inserta si no existía
     *
     * @param email
     * @param password
     * @return si ha sido posible registrar al usuario
     */

    public boolean registerNewUser(String email, String password) {

        ICtrlPersona ctrlPersona = ctrlDataFactory.getCtrlPersona();

        try {

            if(!ctrlPersona.existsPersona(email)){

                int id = (int) (new Date().getTime());
                ctrlPersona.savePersona(id, email, password);

                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;

    }

    /**
     *
     * Si el usuario existe en bd, se hace login y se guardar los datos en el
     * Singleton llamado userLogged
     *
     * @param email
     * @param password
     * @return true si ha sido posible hacer login
     */
    public boolean loginUser(String email, String password) {

        ICtrlPersona ctrlPersona = ctrlDataFactory.getCtrlPersona();

        try {

            Persona persona = (Persona) ctrlPersona.getPersona(email);
            if(persona.getPassword().equals(password)){

                userLogged.setUserLogged(persona);

                return true;
            }

        }catch (PersonaDoesNotExists exception){
            System.out.println(exception.toString());
        }

        return false;
    }

    public String getEmailLoggedPersona(){
        Persona user = userLogged.getUserLogged();
        return user.getEmail();
    }

    /**
     *
     *
      * @param email
     * @return true si la persona existe y si no tiene el mismo email que el jugador actual
     * @throws SQLException
     */

    public boolean existsPersona(String email) throws SQLException {
        ICtrlPersona ctrlPersona = ctrlDataFactory.getCtrlPersona();
        boolean exists = ctrlPersona.existsPersona(email);

        String actualPlayerEmail = user.getEmail();
        boolean samePlayer = actualPlayerEmail.equals(email);

        return (exists && !samePlayer);
    }

    /**
     * Para hacer inyección de dependencias en los tests
     * @param user
     */
    public void setUser(Player user){
        this.user = user;
    }

    public void logout(){
        userLogged.delete();
    }
}
