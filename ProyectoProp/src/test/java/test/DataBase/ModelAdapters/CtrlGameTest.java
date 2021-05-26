package test.DataBase.ModelAdapters;

import DataBase.Controllers.CtrlGame;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Rodriguez
 */

public class CtrlGameTest {


    public static int gameID = 0;
    public static int boardID = 1;
    public static long data = 10;
    public static int blackPlayerID = 1;
    public static int whitePlayerID = 2;


    @Before
    public void initGame(){
        CtrlGame ctrlGame = new CtrlGame();
        ctrlGame.saveGame(gameID, data, boardID, blackPlayerID, whitePlayerID);

    }

    @After
    public void deleteGame(){
        CtrlGame ctrlGame = new CtrlGame();
        ctrlGame.deleteGame(gameID);
    }

    @Test

    public void saveGameTest(){
        CtrlGame ctrlGame = new CtrlGame();

        ctrlGame.deleteGame(gameID);


        ctrlGame.saveGame(gameID, data, boardID, blackPlayerID, whitePlayerID);

        boolean existsGame = ctrlGame.existsGame(gameID);
        Assert.assertTrue("Game not saved", existsGame);
    }

    @Test

    public void existsGameTest(){
        CtrlGame ctrlGame = new CtrlGame();

        boolean existstGame = ctrlGame.existsGame(gameID);
        Assert.assertTrue("Game does not exists", existstGame);
    }




    @Test

    public void deleteGameTest(){
        CtrlGame ctrlGame = new CtrlGame();

        boolean deleted = ctrlGame.deleteGame(gameID);


        Assert.assertTrue("Game does not exists", deleted);
    }



}
