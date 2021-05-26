package UI.Controllers;

import Domain.Controllers.RankingController;
import Domain.Models.Usuario.Ranking;
import Exceptions.PersonaDoesNotExists;
import javafx.collections.ObservableList;

/**
 * @author Pep Gascon
 * @return
 * @throws PersonaDoesNotExists
 */
public class UiCtrlRanking {

    public static ObservableList<Ranking> getRanking() throws PersonaDoesNotExists {
        RankingController rankingController = new RankingController();
        ObservableList<Ranking> ranking = rankingController.getRanking();
        return ranking;
    }
}