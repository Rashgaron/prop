package Domain.Controllers;


import Domain.DataControllers.CtrlDataFactory;
import Domain.DataControllers.ICtrlPersona;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;

import Domain.Models.Usuario.Ranking;
import Exceptions.PersonaDoesNotExists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.lang.String;

/**
 * @author Pep Gascon
 * En comptes de tenir una classe ranking hem decidit crear-lo cada cop que el volguèssim veure'l, i per crear-lo
 * anem a les estadístiques de cadascuna de les persones que tenim i les ordenem de manera ascendent per el percentatge
 * de victories, i en cas d'empat per partits jugats.
 */
public class RankingController {

    public static Comparator<Estadistiques> StatsNameComparator = new Comparator<Estadistiques>() {
        @Override
        public int compare(Estadistiques e1, Estadistiques e2) {
            int r = 1;
            if(e1.getWinratio()>e2.getWinratio()) r = -1;
            else if(e1.getWinratio()==e2.getWinratio()){
                if(e1.getPguanyats()>e2.getPguanyats()) r = -1;
            }
            return r;
        }
    };

    public ObservableList<Ranking> getRanking() throws PersonaDoesNotExists {

        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlPersona ctrlPersona = factory.getCtrlPersona();
        ArrayList<Persona> persones = ctrlPersona.getAllPersones();
        ArrayList<Estadistiques> arrayStats = new ArrayList<>();

        for (Persona persona : persones) {
            Estadistiques estadistiques = persona.getEstadistiques();
            arrayStats.add(estadistiques);
        }

        Collections.sort(arrayStats, StatsNameComparator);

        ObservableList<Ranking> list = FXCollections.observableArrayList();

        for (int i = 0; i < arrayStats.size(); ++i) {
            Estadistiques estadistiques = arrayStats.get(i);

            Persona persona = ctrlPersona.getPersona(estadistiques.getPlayerID());
            String mail = persona.getEmail();

            float w = Math.round(estadistiques.getPguanyats() * 100F)/100F;
            float l = Math.round(estadistiques.getPperduts() * 100F)/100;
            float wr = Math.round(estadistiques.getWinratio() * 100F)/100F;

            list.add(new Ranking(i+1, mail, w, l, wr));
        }

        return list;

    }
}
