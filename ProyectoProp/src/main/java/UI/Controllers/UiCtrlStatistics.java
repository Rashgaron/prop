package UI.Controllers;

import Domain.Controllers.StatisticsController;

/**
 * @author Daniel Rodriguez
 */

public class UiCtrlStatistics {

    private static StatisticsController statisticCtrl = new StatisticsController();

    public static String getStatistics(){

        String estadistiques = statisticCtrl.getStatisticsFromLoggedUser();

        return estadistiques;
    }

}
