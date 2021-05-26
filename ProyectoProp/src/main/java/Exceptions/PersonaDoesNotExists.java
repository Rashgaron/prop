package Exceptions;
/**
 * @author Daniel Rodriguez
 */
public class PersonaDoesNotExists extends Exception{

    public PersonaDoesNotExists(String errorMessage){
        super(errorMessage);
    }
}
