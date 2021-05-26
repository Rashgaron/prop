package Domain.DataControllers;

/**
 * @author Daniel Rodriguez
 */
public interface ICtrlDataFactory {


    public static CtrlDataFactory Instance() {
        return null;
    }

    public ICtrlPersona getCtrlPersona();
    public ICtrlStatistics getCtrlStatistics();
    public ICtrlGame getCtrlGame();
    public ICtrlBoard getCtrlBoard();

}
