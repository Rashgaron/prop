package test.Domain.Controllers;

import Domain.Controllers.GameController;
import Domain.DataControllers.*;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Domain.utility.IRandomNumber;
import Exceptions.PersonaDoesNotExists;
import Exceptions.emailEqualThanLoggedUser;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
/**
 * @author Daniel Rodríguez
 */

public class GameControllerTest {

    public ICtrlGame fakeCtrlGame;
    public ICtrlBoard fakeCtrlBoard;
    public ICtrlPersona fakeCtrlPersona;
    public GameController gameController;
    int gameID = 1;
    int data = 1;
    int boardID = 1;
    Player firstPlayer = new Persona(1010,"persona 1", "password 1");
    Player secondPlayer = new Persona(2, "persona 2", "password 2");
    Persona userLogged = new Persona(1010, "persona 1", "password 1");

    @Before
    public void initVars(){
        fakeCtrlGame = mock(ICtrlGame.class);
        fakeCtrlBoard = mock(ICtrlBoard.class);
        fakeCtrlPersona = mock(ICtrlPersona.class);
        gameController = new GameController();
    }

    @Test
    public void generateOrderOfGameAndSave_verifyRandomNumberOdd(){
        IRandomNumber fakeRandomNumber = mock(IRandomNumber.class);
        when(fakeRandomNumber.getRandomNumber()).thenReturn(1);

        ICtrlDataFactory fakeDataFactory = new fakeDataFactory();

        gameController.setRandomNumberGenerator(fakeRandomNumber);
        gameController.setCtrlDataFactory(fakeDataFactory);


        gameController.generateOrderOfGameAndSave(gameID, data, boardID,
                firstPlayer, secondPlayer,userLogged);

        verify(fakeCtrlGame,only()).saveGame(gameID, data, boardID, userLogged.getID(), secondPlayer.getID());

    }
    @Test
    public void generateOrderOfGameAndSave_VerifyRandomNumberEven(){
        IRandomNumber fakeRandomNumber = mock(IRandomNumber.class);
        when(fakeRandomNumber.getRandomNumber()).thenReturn(1);

        ICtrlDataFactory fakeDataFactory = new fakeDataFactory();

        gameController.setRandomNumberGenerator(fakeRandomNumber);
        gameController.setCtrlDataFactory(fakeDataFactory);


        gameController.generateOrderOfGameAndSave(gameID, data, boardID,
                firstPlayer, secondPlayer,userLogged);

        verify(fakeCtrlGame,only()).saveGame(gameID, data, boardID, userLogged.getID(), secondPlayer.getID());

    }

    @Test
    public void getSelectedGameID_NoGame_ReturnMinusOne(){
        Persona fakePersona = mock(Persona.class);
        when(fakePersona.getSelectedGame(anyInt())).thenReturn(null);

        gameController.setUserLogged(fakePersona);

        int actual = gameController.getSelectedGameID(1);

        int expected = -1;

        Assert.assertEquals(actual, expected);

    }

    @Test
    public void getSelectedGameID_GameExists_ReturnGameID(){

        int selectedGameIndex = 3;
        int gameID = 102039;
        Persona fakePersona = mock(Persona.class);
        when(fakePersona.getSelectedGame(selectedGameIndex)).thenReturn(new Game( gameID,0,0,null,null));
        gameController.setUserLogged(fakePersona);

        int actual = gameController.getSelectedGameID(selectedGameIndex);

        int expected = gameID;

        Assert.assertEquals(expected, actual);


    }

    @Test
    public void GetCurrentPlayer_TurnOfBlacks_ReturnBlackPlayer(){

        Game game = mock(Game.class);
        Player p = new Persona(1,"email", "password");
        when(game.getBlackPlayer()).thenReturn(p);
        boolean isTurnOfBlacks = true;

        Player actual = gameController.getCurrentPlayer(isTurnOfBlacks, game);

        Assert.assertEquals(p.getID(), actual.getID());
    }

    @Test
    public void GetCurrentPlayer_TurnOfWhites_ReturnWhitePlayer(){

        Game game = mock(Game.class);
        when(game.getWhitePlayer()).thenReturn(firstPlayer);
        boolean isTurnOfBlacks = false;

        Player actual = gameController.getCurrentPlayer(isTurnOfBlacks, game);

        Assert.assertEquals(firstPlayer.getID(), actual.getID());
    }

    @Test
    public void checkIfGameIsFinished_NoMovesAvailable_ReturnFalse(){
        Game game = mock(Game.class);
        when(game.calculamove(firstPlayer)).thenReturn(false);
        boolean actual = gameController.checkIfGameIsFinished(game, firstPlayer);

        Assert.assertFalse(actual);

    }

    @Test
    public void checkIfGameIsFinished_MovesAvailable_ReturnTrue(){
        Game game = mock(Game.class);
        when(game.calculamove(firstPlayer)).thenReturn(false);

        boolean actual = gameController.checkIfGameIsFinished(game, firstPlayer);

        Assert.assertFalse(actual);
    }

    @Test
    public void makeMoveBot_IsABot_FunctionCalled(){
        Game game = mock(Game.class);
        Player bot = mock(Player.class);
        GameController gC = mock(GameController.class);
        doCallRealMethod().when(gC).makeBotMove(any(),any());

        when(bot.getID()).thenReturn(1);

        gC.makeBotMove(bot, game);

        verify(game,only()).calculamove(any());
    }


    @Test
    public void Play_PvE_MakeBotMoveCalled(){

        Pair<Integer,Integer> jugada = new Pair<Integer,Integer>(1,1);


        GameController gameCtrl = mock(GameController.class);
        when(gameCtrl.getCurrentPlayer(eq(true),any())).thenReturn(firstPlayer);
        when(gameCtrl.getOpponentPlayer(eq(true),any())).thenReturn(secondPlayer);
        doCallRealMethod().when(gameCtrl).play(jugada);
        doCallRealMethod().when(gameCtrl).setCurrentGame(any());

        Board board = mock(Board.class);
        when(board.getTurn()).thenReturn(true);//turn of blacks

        Game game = mock(Game.class);
        when(game.calculamove(firstPlayer)).thenReturn(true);
        when(game.getBoard()).thenReturn(board);

        doCallRealMethod().when(gameCtrl).moveIfIsABot(any(),any(), any(), anyBoolean());

        gameCtrl.setCurrentGame(game);

        gameCtrl.play(jugada);

        verify(gameCtrl, atLeast(1)).makeTheMove(any(), any(),any());
    }

    @Test (expected = emailEqualThanLoggedUser.class)
    public void findPlayer_OpponentEmailEqualsMyEmail_ReturnException() throws PersonaDoesNotExists, emailEqualThanLoggedUser {

        String myEmail = "myEmail";
        String opponentEmail = myEmail;

        gameController.findPlayer(opponentEmail, null, myEmail);

    }

    @Test(expected = PersonaDoesNotExists.class)
    public void findPlayer_opponentDoesNotExists_ReturnException() throws PersonaDoesNotExists, emailEqualThanLoggedUser {

        String myEmail = "myEmail";
        String opponentEmail = "opponentEmail";

        when(fakeCtrlPersona.getPersona(opponentEmail)).thenThrow(PersonaDoesNotExists.class);

        gameController.findPlayer(opponentEmail, fakeCtrlPersona, myEmail);
    }

    @Test
    public void findPlayer_ValidOpponent_ReturnPlayer() throws PersonaDoesNotExists, emailEqualThanLoggedUser {
        String myEmail = "myEmail";
        String opponentEmail = "opponentEmail";

        when(fakeCtrlPersona.getPersona(opponentEmail)).thenReturn(secondPlayer);

        Player expected = secondPlayer;

        Player actual = gameController.findPlayer(opponentEmail, fakeCtrlPersona, myEmail);

        Assert.assertEquals(expected, actual);

    }





    public class fakeDataFactory implements ICtrlDataFactory{

        @Override
        public ICtrlPersona getCtrlPersona() {
            return null;
        }

        @Override
        public ICtrlStatistics getCtrlStatistics() {
            return null;
        }

        @Override
        public ICtrlGame getCtrlGame() {
            return fakeCtrlGame;
        }

        @Override
        public ICtrlBoard getCtrlBoard() {
            return fakeCtrlBoard;
        }
    }


}
