package test.DataBase.ModelAdapters;

import DataBase.Controllers.CtrlStatistics;
import Domain.Models.Usuario.Estadistiques;
import Exceptions.StatisticDoesNotExists;
import org.junit.*;

/**
 * @author Daniel Rodriguez
 */
public class CtrlStatisticsTest {


    private static int id = 10;


    @Before
    public void insertDummyData(){
        CtrlStatistics sAD = new CtrlStatistics();
        sAD.createPlayerStatistics(id);
    }

    @After
    public void deleteDummyData(){
        CtrlStatistics sAD = new CtrlStatistics();
        sAD.deleteStatistic(id);
    }

    @Test
    public void createStatisticsTest(){

        int playerID = 10;

        CtrlStatistics sAD = new CtrlStatistics();
        sAD.createPlayerStatistics(playerID);
        Estadistiques estadistica = null;

        try {

            estadistica = sAD.getStatistic(playerID);

        } catch (StatisticDoesNotExists statisticDoesNotExists) {
            statisticDoesNotExists.printStackTrace();
            Assert.assertTrue("La estadistica no existe", false);
        }

        Assert.assertEquals(playerID, estadistica.getPlayerID());
        Assert.assertEquals("Error con la asignación de partidos ganados",0, (int)estadistica.getPguanyats());
        Assert.assertEquals("Error con la asignación de partidos perdidos",0, (int)estadistica.getPperduts());

        sAD.deleteStatistic(playerID);
    }

    @Test
    public void getStatisticTest(){

        CtrlStatistics sAD = new CtrlStatistics();

        Estadistiques estadistica = null;
        try {

            estadistica = sAD.getStatistic(id);
        } catch (StatisticDoesNotExists statisticDoesNotExists) {

            statisticDoesNotExists.printStackTrace();
            Assert.assertTrue("La estadistica no existe",false);

        }

        Assert.assertEquals("Fail with id",
                estadistica.getPlayerID(),id);
        Assert.assertEquals("Error con la asignación de partidos ganados",0, (int)estadistica.getPguanyats());
        Assert.assertEquals("Error con la asignación de partidos perdidos",0,(int)estadistica.getPperduts());


    }

    @Test
    public void existsStatisticTest(){

        CtrlStatistics sAD = new CtrlStatistics();
        boolean exists = sAD.existsStatistic(id);
        Assert.assertTrue("La estadistica no existe",exists);

    }

    @Test
    public void deleteStatisticTest(){

        CtrlStatistics sAD = new CtrlStatistics();
        boolean deleted = sAD.deleteStatistic(id);
        Assert.assertTrue("The statistic exists",deleted);

    }

}
