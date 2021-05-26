package test.DataBase.ModelAdapters;

import Domain.DataControllers.CtrlDataFactory;
import Domain.DataControllers.ICtrlBoard;
import Domain.Models.Game.Board;
import Exceptions.BoardDoesNotExists;
import org.junit.*;

/**
 * @author Daniel Rodriguez
 */
public class TestCtrlBoardTest {

    public static int boardID = 1;
    public static char [][] boardState = new char [8][8];
    @BeforeClass
    public static void initBoard(){
        for(int i = 0 ; i < 8 ; i++){
            for(int j = 0; j < 8 ; j++){
                boardState[i][j] = 'o';
            }
        }
    }
    @After
    public void deleteBoard(){
        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlBoard ctrlBoard = factory.getCtrlBoard();
        ctrlBoard.deleteBoard(boardID);
    }
    @Before
    public void initBoardTest(){
        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlBoard ctrlBoard = factory.getCtrlBoard();
        ctrlBoard.saveBoard(boardID, boardState, true);
    }

    @Test
    public void saveBoard()  {
        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlBoard ctrlBoard = factory.getCtrlBoard();
        ctrlBoard.deleteBoard(boardID);

        Board expected = new Board(boardID, boardState, true);
        ctrlBoard.saveBoard(boardID, boardState, true);


        try {
            Board board = ctrlBoard.getBoard(boardID);
            Assert.assertEquals("ID's are different", board.getID(), expected.getID());
            Assert.assertEquals("Data are different", board.getBoardString(), expected.getBoardString());
//                Assert.assertFalse(true);
        } catch (BoardDoesNotExists boardDoesNotExists) {
            boardDoesNotExists.printStackTrace();
        }


    }
    @Test
    public void getBoard(){

        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlBoard ctrlBoard = factory.getCtrlBoard();

        ctrlBoard.saveBoard(boardID, boardState, true);


        Board expected = new Board(boardID, boardState, true);

        try {

            Board board = ctrlBoard.getBoard(boardID);
            Assert.assertEquals("ID's are different", board.getID(), expected.getID());
            Assert.assertEquals("Data are different", board.getBoardString(), expected.getBoardString());

        } catch (BoardDoesNotExists boardDoesNotExists) {
            boardDoesNotExists.printStackTrace();
        }


    }

    @Test
    public void deleteBoardTest(){
        CtrlDataFactory factory = CtrlDataFactory.Instance();
        ICtrlBoard ctrlBoard = factory.getCtrlBoard();

        ctrlBoard.deleteBoard(boardID);

        try {
            ctrlBoard.getBoard(boardID);
            Assert.assertTrue("Delete does not work property", false);
        } catch (BoardDoesNotExists boardDoesNotExists) {
            System.out.println("Board deleted correctly");
            Assert.assertTrue("Delete does not work property", true);
        }

    }
}
