package DataBase.Controllers;

import Domain.DataControllers.ICtrlStatistics;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Player;
import Exceptions.StatisticDoesNotExists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * @author Daniel Rodriguez
 */
public class CtrlStatistics implements ICtrlStatistics {

    @Override
    public void createPlayerStatistics(int id) {
        JSONObject newStatistic = new JSONObject();

        newStatistic.put("PlayerID",id);
        newStatistic.put("won",0);
        newStatistic.put("lost",0);

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Statistic.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Statistics = (JSONArray) obj;
            Statistics.add(newStatistic);

            writeFile(Statistics, "Statistic.json");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            initStatisticsFile();
            createPlayerStatistics(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Empty file");
            initStatisticsFile();
            createPlayerStatistics(id);
        }
    }


    public Estadistiques getStatistic(int PlayerID) throws StatisticDoesNotExists {

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Statistic.json")){

            Object obj = jsonParser.parse(reader);
            JSONArray Statistics = (JSONArray) obj;

            int index = findStatistic(PlayerID, Statistics);
            if(index == -1)
                throw new StatisticDoesNotExists("The Statistic does not exists");
            else{

                return createStatistic(Statistics, index);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


    public boolean existsStatistic(int PlayerID){

        boolean exists = false;

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Statistic.json")){

            Object obj = jsonParser.parse(reader);
            JSONArray Statistics = (JSONArray) obj;

            exists = findStatistic(PlayerID, Statistics) != -1 ? true : false;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            initStatisticsFile();
            existsStatistic(PlayerID);
        }

        return exists;
    }



    public boolean deleteStatistic(int PlayerID){

        JSONParser jsonParser = new JSONParser();
        boolean deleted = false;

        try(FileReader reader = new FileReader("Statistic.json")){

            Object fileData = jsonParser.parse(reader);
            JSONArray Statistics = (JSONArray) fileData;

            int index = findStatistic(PlayerID, Statistics);

            if(index != -1){
                Statistics.remove(index);
                deleted = true;
            }

            writeFile(Statistics, "Statistic.json");

            return deleted;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean saveStatistic(int PlayerID, int won, int lost) {

        JSONObject statistic = new JSONObject();


        statistic.put("PlayerID",PlayerID);
        statistic.put("won",won);
        statistic.put("lost",lost);

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Statistic.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Statistics = (JSONArray) obj;

            int index = findStatistic(PlayerID, Statistics);

            if(index != -1){
                Statistics.remove(index);
                Statistics.add(statistic);

                writeFile(Statistics, "Statistic.json");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    private int findStatistic(int PlayerID, JSONArray Statistics){

        int index = -1;

        for(int i = 0; i < Statistics.size();i++){

            JSONObject statistic = (JSONObject) Statistics.get(i);
            int statisticID = Integer.parseInt(String.valueOf(statistic.get("PlayerID")));

            if(statisticID == PlayerID){
                index = i;
                break;
            }
        }

        return index;

    }

    private Estadistiques createStatistic(JSONArray Statistics, int index){
        JSONObject est = (JSONObject) Statistics.get(index);

        int PlayerID = Integer.parseInt(String.valueOf(est.get("PlayerID")));
        int won = Integer.parseInt(String.valueOf(est.get("won")));
        int lost = Integer.parseInt(String.valueOf(est.get("lost")));

        return new Estadistiques(PlayerID, won, lost);
    }

    private void writeFile(JSONArray data, String fileName) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.write(data.toJSONString());
        file.flush();
    }

    private void initStatisticsFile(){
        try {
            FileWriter file = new FileWriter("Statistic.json");
            file.write("[]");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
