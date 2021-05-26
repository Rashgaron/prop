package Domain.DataControllers;

import DataBase.Controllers.CtrlBoard;
import DataBase.Controllers.CtrlGame;
import DataBase.Controllers.CtrlPersona;
import DataBase.Controllers.CtrlStatistics;


/**
 * @author Daniel Rodriguez
 */
public class CtrlDataFactory implements ICtrlDataFactory{

    private static CtrlDataFactory _instance = null;

    public static CtrlDataFactory Instance(){
        if(_instance == null){
            _instance = new CtrlDataFactory();
        }
        return _instance;
    }

    public ICtrlPersona getCtrlPersona(){
        return new CtrlPersona();
    }
    public ICtrlStatistics getCtrlStatistics(){
        return new CtrlStatistics();
    }
    public ICtrlGame getCtrlGame(){return new CtrlGame(); }
    public ICtrlBoard getCtrlBoard(){return new CtrlBoard();}

    }

