package test.Domain.Controllers;

import Domain.Models.Usuario.Record;
import Domain.Controllers.RecordController;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Daniel Rodríguez
 */
public class RecordControllerTest {

    public Record record = mock(Record.class);


    @Test
    public void getBestPunctuationIntegration_ValidData_CorrectFormat(){

        String expectedEmail = "Email";
        Persona p = new Persona(1,expectedEmail,"Password");
        int expectedPunctuation = 1000;

        Pair<Player, Integer> bestPunctuationTuple = new Pair<>(p, expectedPunctuation);

        when(record.getBestPunctuation()).thenReturn(bestPunctuationTuple);


        RecordController ctrlRecord = new RecordController();
        ctrlRecord.setRecord(record);


        Pair<String, Integer> actualBestPunctuation = ctrlRecord.getBestPunctuation();

        String actualEmail = actualBestPunctuation.getValue0();
        int actualPunctuation = actualBestPunctuation.getValue1();

        Assert.assertEquals(expectedEmail, actualEmail);
        Assert.assertEquals(expectedPunctuation, actualPunctuation);
    }

    @Test
    public void getGameWithGreaterDifferenceOfPunctuationIntegration_ValidData_CorrectFormat(){

        int expectedGameID = 1;
        Game expectedGame = new Game(expectedGameID,1,1,null,null);
        int expectedPunctuation = 200;
        Pair<Game, Integer> expectedRes = new Pair<Game, Integer>(expectedGame, expectedPunctuation);


        when(record.getGameWithGreaterDifferenceOfPunctuation()).thenReturn(expectedRes);

        RecordController ctrlRecord = new RecordController();
        ctrlRecord.setRecord(record);

        Pair<Integer, Integer> actual = ctrlRecord.getGameWithGreaterDifferenceOfPunctuation();

        int actualGameID = actual.getValue0();
        int actualPunctuation = actual.getValue1();

        Assert.assertEquals(expectedGameID , actualGameID);
        Assert.assertEquals(expectedPunctuation, actualPunctuation);

    }
}
