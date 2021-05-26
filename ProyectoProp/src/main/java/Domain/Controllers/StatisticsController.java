package Domain.Controllers;

import Domain.Models.Usuario.Estadistiques;
import Domain.UserLogged;

/**
 * @author Daniel Rodriguez
 */

public class StatisticsController implements IStatisticsController{

    UserLogged user = UserLogged.Instance();

    public String getStatisticsFromLoggedUser(){
        Estadistiques estadistiques = user.getStatistic();

        return estadistiques.toString();
    }

    public float[] getStatisticsArrayFromLoggedUser(){
        Estadistiques estadistiques = user.getStatistic();
        return estadistiques.getStatistics();
    }

    public void setUserLogged(UserLogged user){
        this.user = user;
    }



}
