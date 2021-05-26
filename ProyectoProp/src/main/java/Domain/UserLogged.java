package Domain;

import Domain.DataControllers.CtrlDataFactory;
import Domain.DataControllers.ICtrlPersona;
import Domain.DataControllers.ICtrlStatistics;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;
import Exceptions.StatisticDoesNotExists;
/**
 * Esta clase guarda los datos del usuario que está registrado actualmente en la aplicación
 * @author Daniel Rodriguez
 */
public class UserLogged {



    private static UserLogged _instance = null;
    private Persona user;

    public static UserLogged Instance(){
        if(_instance == null){
            _instance = new UserLogged();
        }
        return _instance;
    }


    public void setUserLogged(Persona user){

        this.user = user;
        CtrlDataFactory dataFactory = CtrlDataFactory.Instance();
        ICtrlStatistics ctrlStatistics = dataFactory.getCtrlStatistics();

        try {

            Estadistiques estadistica = ctrlStatistics.getStatistic(user.getID());
            user.setEstadistiques(estadistica);

        } catch (StatisticDoesNotExists statisticDoesNotExists) {
            statisticDoesNotExists.printStackTrace();
        }


    }
    public Persona getUserLogged(){
        return user;
    }

    public Estadistiques getStatistic(){

        Estadistiques estadistica = user.getEstadistiques();
        return estadistica;

    }


    public void delete(){
        user = null;
    }

    public void setStatistics(Estadistiques est){
        user.setEstadistiques(est);
    }

}
