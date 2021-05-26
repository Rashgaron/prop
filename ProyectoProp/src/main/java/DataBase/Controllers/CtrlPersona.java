package DataBase.Controllers;

import Domain.DataControllers.ICtrlPersona;
import Domain.DataControllers.ICtrlStatistics;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Bot;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Exceptions.PersonaDoesNotExists;
import Exceptions.StatisticDoesNotExists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Daniel Rodriguez
 */
public class CtrlPersona implements ICtrlPersona {


    @Override
    public void savePersona(int id, String email, String password) {

        JSONObject persona = new JSONObject();

        persona.put("id",id);
        persona.put("email",email);
        persona.put("password",password);

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Persona.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Personas = (JSONArray) obj;
            Personas.add(persona);

            writeFile(Personas, "Persona.json");
            ICtrlStatistics statistics = new CtrlStatistics();
            statistics.createPlayerStatistics(id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Fichero vacío");
            initPersonaFile();
            savePersona(id, email, password);
        }


    }

    @Override
    public Player getPersona(String email) throws PersonaDoesNotExists {
        int emailInt = -1;
        try{
            emailInt = Integer.parseInt(email);
            if(isABot(emailInt)){
                return new Bot(emailInt, calculateBotLvl(emailInt), null);
            }
        }catch (Exception e){
        }


        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Persona.json")) {

            Object obj = jsonParser.parse(reader);
            JSONArray Personas = (JSONArray) obj;

            int index = findPersona(email, Personas);

            if(index == -1){
                throw new PersonaDoesNotExists("Persona no existeix");
            }else{
                return createPersona(Personas, index);
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

    @Override
    public Persona getPersona(int id) throws PersonaDoesNotExists {



        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Persona.json")) {

            Object obj = jsonParser.parse(reader);
            JSONArray Personas = (JSONArray) obj;

            int index = findPersona(id, Personas);

            if(index == -1){
                throw new PersonaDoesNotExists("Persona no existeix");
            }else{
                return createPersona(Personas, index);
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
    private boolean isABot(int id){
        return id == 1 || id == 2 || id == 3;
    }
    @Override
    public Player getPersonaOnly(int id) throws PersonaDoesNotExists {

        if(isABot(id)){
            return new Bot(id, calculateBotLvl(id), null);
        }else{

            JSONParser jsonParser = new JSONParser();

            try(FileReader reader = new FileReader("Persona.json")) {

                Object obj = jsonParser.parse(reader);
                JSONArray Personas = (JSONArray) obj;

                int index = findPersona(id, Personas);

                if(index == -1){
                    throw new PersonaDoesNotExists("Persona no existeix");
                }else{
                    JSONObject persona = (JSONObject) Personas.get(index);
                    Player user = null;
                    String email = (String) persona.get("email");
                    String password = (String) persona.get("password");
                    user = new Persona(id, email, password);
                    return user;
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return null;

    }


    /**
     *Funció getAllPersones
     * @Author Pep Gascon
     */
    @Override
    public ArrayList<Persona> getAllPersones() throws PersonaDoesNotExists {

        ArrayList<Persona> llistaPersones = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Persona.json")) {

            Object obj = jsonParser.parse(reader);
            JSONArray Personas = (JSONArray) obj;

            int index = Personas.size();

            for(int i = 0; i < Personas.size(); ++i) llistaPersones.add(i, (createPersona(Personas, i)));



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return llistaPersones;
    }



    private Persona createPersona(JSONArray Personas, int index){
        JSONObject persona = (JSONObject) Personas.get(index);
        Persona user = null;

        String email = (String) persona.get("email");
        String password = (String) persona.get("password");
        int id = Integer.parseInt(String.valueOf(persona.get("id")));

        CtrlStatistics ctrlStatistics = new CtrlStatistics();
        CtrlGame ctrlGame = new CtrlGame();
        try {
            Estadistiques est = ctrlStatistics.getStatistic(id);
            user = new Persona(id, email, password);
            user.setEstadistiques(est);

            ArrayList<Game> games = ctrlGame.getPersonaGames(user);
            user.setGames(games);

        } catch (StatisticDoesNotExists statisticDoesNotExists) {
            statisticDoesNotExists.printStackTrace();
        }

        return user;
    }


    @Override
    public boolean existsPersona(String email) throws SQLException {

        boolean exists = false;

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Persona.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Personas = (JSONArray) obj;

            exists = findPersona(email, Personas) != -1 ? true : false;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exists;

    }

    @Override
    public boolean deletePersona(String email) {
        CtrlStatistics ctrlStatistics = new CtrlStatistics();
        JSONParser jsonParser = new JSONParser();
       boolean deleted = false;

       try(FileReader reader = new FileReader("Persona.json")){


           Object obj = jsonParser.parse(reader);
           JSONArray Personas = (JSONArray) obj;

           int index = findPersona(email, Personas);
           if(index != -1){

               int id = getPersona(email).getID();
               ctrlStatistics.deleteStatistic(id);
               Personas.remove(index);
               deleted = true;

           }

           writeFile(Personas, "Persona.json");

           return deleted;

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ParseException e) {
           e.printStackTrace();
       } catch (PersonaDoesNotExists personaDoesNotExists) {
           personaDoesNotExists.printStackTrace();
       }
        return false;

    }

    private int findPersona(int id, JSONArray data){
        int index = -1;

        for(int i = 0; i < data.size(); i++){

            JSONObject persona = (JSONObject) data.get(i);
            int idPersona = Integer.parseInt(String.valueOf(persona.get("id")));

            if(idPersona == id){
                index = i;
                break;
            }

        }

        return index;
    }
    private int findPersona(String email, JSONArray data){

        int index = -1;

        for(int i = 0; i < data.size(); i++){

            JSONObject persona = (JSONObject) data.get(i);
            String emailPersona = (String) persona.get("email");

            if(emailPersona.equals(email)){
                index = i;
                break;
            }

        }

        return index;
    }

    private void writeFile(JSONArray data, String fileName) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.write(data.toJSONString());
        file.flush();
    }

    private void initPersonaFile(){
        try {
            FileWriter file = new FileWriter("Persona.json");
            file.write("[]");
            file.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private int calculateBotLvl(int id){
        int res = 1;
        if (id == 2) res = 3;
        else if (id == 3) res = 6;
        return res;
    }
}
