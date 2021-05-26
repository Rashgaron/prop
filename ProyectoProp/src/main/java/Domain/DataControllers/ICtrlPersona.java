package Domain.DataControllers;

import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Exceptions.PersonaDoesNotExists;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Persistencia de persona
 * @author Daniel rodriguez
 */

public interface ICtrlPersona {

    /**
     * Guarda los datos de la persona pasados por parámetro
     * @param id
     * @param email
     * @param password
     */
    public void savePersona(int id, String email, String password);
    public Player getPersona(String email) throws PersonaDoesNotExists;
    public Persona getPersona(int id) throws PersonaDoesNotExists;
    public Player getPersonaOnly(int id) throws PersonaDoesNotExists;
    public ArrayList<Persona> getAllPersones() throws PersonaDoesNotExists;
    public boolean existsPersona(String email) throws SQLException;
    public boolean deletePersona(String email);
}
