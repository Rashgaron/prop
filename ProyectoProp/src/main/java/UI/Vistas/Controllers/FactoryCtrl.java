package UI.Vistas.Controllers;

import Domain.Controllers.RecordController;
import Domain.DataControllers.CtrlDataFactory;

/**
 * @author Daniel Rodriguez
 */
public class FactoryCtrl {
    private static FactoryCtrl _instance = null;

    public static FactoryCtrl Instance(){
        if(_instance == null)
            _instance = new FactoryCtrl();

        return _instance;
    }

    public SignInCtrl getSignInCtrl() {return new SignInCtrl();}
    public MainCtrl getMainCtrl(){return new MainCtrl();}
    public LoginCtrl getLoginCtrl(){return new LoginCtrl();}
    public RecordController getRecordCtrl(){return new RecordController();}

}
