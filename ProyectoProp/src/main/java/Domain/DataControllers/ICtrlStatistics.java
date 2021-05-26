package Domain.DataControllers;

import Domain.Models.Usuario.Estadistiques;
import Exceptions.StatisticDoesNotExists;

/**
 * Persistencio de estadísticas
 * @author Daniel Rodriguez
 */
public interface ICtrlStatistics {

    void createPlayerStatistics(int PlayerID);
    Estadistiques getStatistic(int PlayerID) throws StatisticDoesNotExists;
    boolean existsStatistic(int PlayerID);
    boolean deleteStatistic(int PlayerID);
    boolean saveStatistic(int PlayerID, int won, int lost);
}
