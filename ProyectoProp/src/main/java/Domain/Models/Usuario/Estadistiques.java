package Domain.Models.Usuario;

import Domain.DataControllers.CtrlDataFactory;
import Domain.DataControllers.ICtrlPersona;
import Exceptions.PersonaDoesNotExists;

import java.util.*;
import java.lang.*;

/**
 * @author Daniel Rodriguez
 */

public class Estadistiques {
    private int PlayerID;
    private float Pguanyats;
    private float Pperduts;

    public Estadistiques(int PlayerID, int Pguanyats, int Pperduts) {
        this.PlayerID = PlayerID;
        this.Pguanyats = Pguanyats;
        this.Pperduts = Pperduts;
    }

    public void addPguanyat() {
        Pguanyats = Pguanyats + 1;
    }

    public void addPperdut() {
        Pperduts = Pperduts+ 1;
    }

    public int getPlayerID() {
        return PlayerID;
    }
    public float getPjugats() {
        return Pguanyats + Pperduts;
    }

    public float getPguanyats() {
        return Pguanyats;
    }

    public float getPperduts() {
        return Pperduts;
    }

    public float getWinratio() {
        return (float) Pguanyats == 0 ? 0 : (Pguanyats)/(Pguanyats+Pperduts);
    }



    @Override
    public String toString() {
        float winRatio = (Pguanyats == 0 ? 0 : (float) Pguanyats/(Pguanyats+Pperduts));
        return "Won: " + Pguanyats + "\n"
                + "Lost: " + Pperduts + "\n"
                + "WinRatio: " + winRatio + "\n";
    }

    public float [] getStatistics(){
        float winRatio = (Pguanyats == 0 ? 0 : (float) Pguanyats/(Pguanyats+Pperduts));
        float [] statistics = {Pguanyats, Pperduts, winRatio};

        return statistics;
    }

    /**
     * @author Pep Gascon
     */
    public String toStringRanking() throws PersonaDoesNotExists {
        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlPersona ctrlPersona = factory.getCtrlPersona();
        Persona persona = ctrlPersona.getPersona(PlayerID);
        float winRatio =  (Pguanyats == 0 ? 0 : (float)Pguanyats/(Pguanyats + Pperduts));
            return "Name: " + persona.getEmail() + " Won: " + Pguanyats
                    + " Lost: " + Pperduts + " WinRatio: " + winRatio + "\n";

    }
}
