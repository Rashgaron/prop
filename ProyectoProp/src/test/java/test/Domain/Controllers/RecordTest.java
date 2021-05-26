package test.Domain.Controllers;

import DataBase.Controllers.CtrlGame;
import DataBase.Controllers.CtrlPersona;
import Domain.Models.Usuario.Record;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * @author Daniel Rodríguez
 */
public class RecordTest {

    public CtrlGame fakeCtrlGame = mock(CtrlGame.class);
    public CtrlPersona fakeCtrlPersona = mock(CtrlPersona.class);


    @Test
    public void getGameWithMoreTurns(){
        CtrlGame ctrlGames = new CtrlGame();
        ctrlGames.getAllGames();
    }

    @Test
    public void GetBestPunctuation_ValidDataInDB_ReturnValidPunctuation(){
        Board board1 = mock(Board.class);

        int expectedBestPunctuation = 100;
        String expectedEmailPlayer = "Best Punctuation Player Email";

        //Best punctuation
        when(board1.getBlacks()).thenReturn(expectedBestPunctuation);

        when(board1.getWhites()).thenReturn(20);

        Board board2 = mock(Board.class);
        when(board2.getBlacks()).thenReturn(50);
        when(board2.getWhites()).thenReturn(10);

        Game game1 = mock(Game.class);
        when(game1.getBoard()).thenReturn(board1);
        when(game1.getBlackPlayer()).thenReturn(new Persona(13,expectedEmailPlayer, "password"));
        when(game1.getWhitePlayer()).thenReturn(new Persona(23, "segundo", "password"));

        Game game2 = mock(Game.class);
        when(game2.getBoard()).thenReturn(board2);


        ArrayList<Game> games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);

        when(fakeCtrlGame.getAllGames()).thenReturn(games);

        Record record = new Record(fakeCtrlGame, new CtrlPersona());
        record.initRecord();

        Pair<Player, Integer> Actual = record.getBestPunctuation();
        int actualBestPunctuation = Actual.getValue1();
        Player actualPlayer = Actual.getValue0();
        String actualPlayerEmail = actualPlayer.getEmail();

        Assert.assertEquals(expectedBestPunctuation, actualBestPunctuation, 0);
        Assert.assertEquals(expectedEmailPlayer, actualPlayerEmail);


    }

    @Test
    public void getBestPunctuation_NoValidDataInDB_ReturnZeroAndNull(){

        ArrayList<Game> games = new ArrayList<Game>();

        when(fakeCtrlGame.getAllGames()).thenReturn(games);

        Record record = new Record(fakeCtrlGame, new CtrlPersona());
        record.initRecord();

        Pair<Player, Integer> Actual = record.getBestPunctuation();

        Assert.assertEquals(null, Actual.getValue0());
        Assert.assertEquals(0, Actual.getValue1(), 0);

    }

    @Test
    public void getGreaterDifferenceOfPunctuation_ValidDataOnDB_ReturnValidRes(){

        Board board1 = mock(Board.class);
        when(board1.getBlacks()).thenReturn(100);
        when(board1.getWhites()).thenReturn(20);

        Board board2 = mock(Board.class);
        when(board2.getBlacks()).thenReturn(50);
        when(board2.getWhites()).thenReturn(10);

        Game expectedGame = mock(Game.class);
        when(expectedGame.getBoard()).thenReturn(board1);
        when(expectedGame.getBlackPlayer()).thenReturn(new Persona(13,"Email 1", "password"));
        when(expectedGame.getWhitePlayer()).thenReturn(new Persona(13, "Email 2", "password"));

        Game game2 = mock(Game.class);
        when(game2.getBoard()).thenReturn(board2);


        ArrayList<Game> games = new ArrayList<Game>();
        games.add(expectedGame);
        games.add(game2);

        when(fakeCtrlGame.getAllGames()).thenReturn(games);

        Record record = new Record(fakeCtrlGame, new CtrlPersona());
        record.initRecord();

        Pair<Game, Integer> actual = record.getGameWithGreaterDifferenceOfPunctuation();

        Game actualGame = actual.getValue0();
        int actualPunctuation = actual.getValue1();

        Assert.assertEquals("The game with greater difference of punctuation should be this game",expectedGame, actualGame);
        Assert.assertEquals(80, actualPunctuation,0);
    }

    @Test
    public void getGameWithGreaterDifferenceOfPunctuation_EmptyDB_ReturnNull(){
        ArrayList<Game> games = new ArrayList<Game>();

        when(fakeCtrlGame.getAllGames()).thenReturn(games);

        Record record = new Record(fakeCtrlGame, new CtrlPersona());
        record.initRecord();

        Pair<Game, Integer> actual = record.getGameWithGreaterDifferenceOfPunctuation();

        Game actualGame = actual.getValue0();
        int actualPunctuation = actual.getValue1();

        Assert.assertEquals(null, actualGame);
        Assert.assertEquals(0, actualPunctuation);
    }

    @Test
    public void isNotABot_EnterBotID_ReturnFalse(){
        Record record = new Record(new CtrlGame(), new CtrlPersona());
        boolean actual = record.isNotABot(1);
        boolean expected = false;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNotABot_EnterPlayerID_ReturnTrue(){
        Record record = new Record(new CtrlGame(), new CtrlPersona());
        boolean actual = record.isNotABot(12341234);
        boolean expected = true;

        Assert.assertEquals(expected, actual);
    }


}
