package Domain.Controllers;

import Domain.UserLogged;

public interface IStatisticsController {

    public String getStatisticsFromLoggedUser();

    public float[] getStatisticsArrayFromLoggedUser();
    public void setUserLogged(UserLogged user);
}
